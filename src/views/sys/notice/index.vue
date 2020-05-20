<template>
  <div class="app-container">
    <!--通知查询条件-->
    <el-form ref="queryForm" :model="model" :inline="true" label-width="68px">
      <el-form-item label="通知编码" prop="number">
        <el-input
          v-model="model.number"
          placeholder="请输入通知编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通知名称" prop="name">
        <el-input
          v-model="model.name"
          placeholder="请输入通知名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="noticeType">
        <el-select v-model="model.noticeType" placeholder="通知类型" clearable size="small">
          <el-option
            v-for="dict in dictValues['noticeType']"
            :key="dict.key"
            :label="dict.value"
            :value="dict.key"
          />
        </el-select>
      </el-form-item>
      <!--搜索和重置按钮-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="model = {}">重置</el-button>
      </el-form-item>
    </el-form>
    <!--工具栏按钮-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd(null)"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleBatchDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
    </el-row>
    <!--通知列表数据-->
    <el-table
      v-loading="loading"
      :data="noticeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="通知编码" align="center" prop="number" />
      <el-table-column label="通知名称" align="center" prop="name" />
      <el-table-column label="通知类型" align="center" prop="noticeType" :formatter="columnFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="{row}">
          <span>{{ parseTime(row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="openSendNotice = true"
          >发送
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="page"
      :limit.sync="size"
      @pagination="handleQuery"
    />
    <!--通知新增编辑弹出框-->
    <notice-dialog :dialog="dialog" @refresh="handleQuery" />
    <!--通知导入弹出框-->
    <import-dialog :dialog="importDialog" @onSuccess="importSuccess" />
    <select-dialog
      :show="openSendNotice"
      :showColumns="['username', 'name']"
      entity="com.lzpeng.project.sys.domain.User"
      :filter="[{key:'name',op:'<',value:'user'}]"
    />
  </div>
</template>

<script>
import { getNoticePage, getNoticeDict, deleteNotice, batchOperation, sendNotice } from '@/api/sys/notice' // 通知api
import NoticeDialog from './components/NoticeDialog'
import ImportDialog from '@/components/ImportDialog' // 导入文件弹出框
import SelectDialog from '@/components/SelectDialog'

export default {
  name: 'NoticeList',
  components: { SelectDialog, ImportDialog, NoticeDialog },
  data() {
    return {
      // 是否打开发送通知对话框
      openSendNotice: false,
      // 表格是否在加载中
      loading: false,
      // 当前第几页
      page: 1,
      // 每页多少条数据
      size: 20,
      // 一共多少条数据
      total: 0,
      // 查询条件
      model: {},
      // 当前选中行id
      ids: [],
      // 当前选中是否是单行
      single: false,
      // 当前选中是否是多行
      multiple: false,
      // 表格数据
      noticeList: [],
      // 通知数据字典
      noticeDict: {},
      // 缓存每列的详细信息 {fieldName:columnInfo}
      columnInfo: {},
      // 缓存每列的数据字典 {fieldName:dictValues}
      dictValues: {},
      // 通知新增编辑页面 参数
      dialog: {
        // 默认不显示 dialog 新增或编辑时显示
        show: false,
        // dialog 标题
        title: '',
        // 数据字典
        dictValues: {},
        // dialog 数据
        form: {
          enabled: 1,
          noticeType: 0
        },
        // dialog 默认数据
        defaultForm: {
          enabled: 1,
          noticeType: 0
        }
      },
      // 导入文件弹出框
      importDialog: {
        show: false,
        action: null // 导入文件Url
      }
    }
  },
  created() {
    this.handleQuery() // 获得表格数据
    this.handleDict() // 获得数据字典
  },
  methods: {
    /**
     * 获得表格数据
     */
    handleQuery() {
      this.loading = true
      getNoticePage(this.page, this.size, this.model).then(res => {
        this.noticeList = res.list
        this.page = res.page
        this.total = res.total
        this.loading = false
      })
    },
    /**
     * 获取数据字典
     */
    handleDict() {
      // 获得本单据的详细定义信息
      getNoticeDict().then(res => {
        this.noticeDict = res // 本单据的详细定义信息
        const result = this.parseTableMeta(res) // 解析本单据的详细定义信息
        this.columnInfo = result.columnInfo // 每列的详细信息
        this.dictValues = result.dictValues // 每列的数据字典
        this.dialog.dictValues = result.dictValues // 每列的数据字典
      })
    },
    /**
     * 默认的列字段显示格式化，从数据字典中查找该值在前台显示什么信息
     * @param row 当前行数据
     * @param column 当前列信息
     */
    columnFormat(row, column) {
      const key = column.property // 当前列信息 绑定的属性名称
      const dictValues = this.dictValues[key] // 当前列的数据字典
      if (dictValues != null && dictValues.length > 0) {
        // 将 key 转为 int 才查找, Boolean型后台返回实体上为 true false, 而数据字典中是 1，0
        const dict = dictValues.find(dict => dict.key === +row[key])
        return (dict && dict.value) || '数据错误'
      }
      return row[key]
    },
    /**
     * 新增按钮操作
     * @param row 当前行数据
     */
    handleAdd(row) {
      this.dialog.show = true
      this.dialog.title = '添加通知'
    },
    /**
     * TODO 表格内发送按钮
     * @param row 当前行数据
     */
    sendNotice(row) {
      sendNotice(row.id, ['27H_j0hl_1vcn2eGR4rK', '27H_j0hl_1vcn2f1ibOo']).then(res => {
        this.$notify.success({
          title: '成功',
          message: '发送通知成功'
        })
      })
    },
    /**
     * 表格内编辑按钮
     * @param row 当前行数据
     */
    handleUpdate(row) {
      if (row != null) {
        this.dialog.form = this.deepClone(row)
        this.dialog.form.enabled = +row.enabled // Boolean 转 int
      }
      this.dialog.show = true
      this.dialog.title = '编辑通知'
    },
    /**
     * 表格内删除按钮
     * @param row 当前行的数据
     */
    handleDelete(row) {
      this.$confirm('此操作将永久删除该通知, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteNotice(row.id).then(res => {
          this.$notify.success({
            title: '成功',
            message: '删除通知成功'
          })
          this.page = 1
          this.handleQuery()
        }).catch(err => {
          console.log(err)
          this.$notify.error({
            title: '失败',
            message: '删除通知失败'
          })
        })
      })
    },
    /**
     * 批量删除
     */
    handleBatchDelete() {
      this.$confirm('此操作将永久所选择的通知, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        batchOperation({ delete: this.ids }).then(res => {
          this.$notify.success({
            title: '成功',
            message: '批量删除通知成功'
          })
          this.page = 1
          this.handleQuery()
        }).catch(err => {
          console.log(err)
          this.$notify.error({
            title: '失败',
            message: '批量删除通知失败'
          })
        })
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1 // 是否只选择了一个
      this.multiple = !selection.length // 是否有选择数据
    },
    // 导入
    handleImport() {
      this.importDialog.show = true
      this.importDialog.action = `${this.baseURL}/${this.$route.path}/import`
    },
    // 导入成功
    importSuccess(data) {
      this.handleQuery() // 获得表格数据
      this.importDialog.show = false
    },
    // 导出
    handleExport() {
      window.open(`${this.baseURL}/${this.$route.path}/export`)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
