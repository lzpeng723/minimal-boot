<template>
  <div class="app-container">
    <!--查询条件表单-->
    <el-form :inline="true">
      <!--部门名称-->
      <el-form-item label="部门编码">
        <el-input
          v-model="model.number"
          placeholder="请输入部门编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--部门名称-->
      <el-form-item label="部门名称">
        <el-input
          v-model="model.name"
          placeholder="请输入部门名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--状态-->
      <el-form-item label="状态">
        <el-select v-model="model.enabled" placeholder="部门状态" clearable size="small">
          <el-option
            v-for="dict in dictValues['enabled']"
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

    <!--数据表格-->
    <el-table
      v-loading="loading"
      :data="departmentList"
      row-key="id"
    >
      <el-table-column prop="number" label="部门编码" :show-overflow-tooltip="true" />
      <el-table-column prop="name" label="部门名称" :show-overflow-tooltip="true" width="160" />
      <el-table-column prop="orderNum" label="排序" width="60" />
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <!--操作按钮-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <department-dialog :dialog="dialog" @refresh="handleQuery" />
    <import-dialog :dialog="importDialog" @onSuccess="importSuccess" />
  </div>
</template>
<script>
import { getDepartmentTree, getDepartmentDict, deleteDepartment } from '@/api/sys/department' // 部门api
import DepartmentDialog from './components/DepartmentDialog' // 部门新增编辑组件
import ImportDialog from '@/components/ImportDialog' // 导入文件弹出框

export default {
  name: 'DepartmentList', // 部门列表组件名称
  components: { ImportDialog, DepartmentDialog }, // 引用部门新增编辑页面组件
  data() {
    return {
      // 表格加载遮罩
      loading: true,
      // 当前第几页
      page: 1,
      // 每页多少条数据
      size: 20,
      // 一共多少条数据
      total: 0,
      // 查询条件
      model: {},
      // 部门数据
      departmentList: [],
      // 所有节点的父子关系
      departmentNodes: [],
      // 部门数据字典
      departmentDict: {},
      // 缓存每列的详细信息 {fieldName:columnInfo}
      columnInfo: {},
      // 缓存每列的数据字典 {fieldName:dictValues}
      dictValues: {},
      // 部门新增编辑页面 参数
      dialog: {
        // 默认不显示 dialog 新增或编辑时显示
        show: false,
        // dialog 标题
        title: '',
        // 数据字典
        dictValues: {},
        // dialog 数据
        form: {
          parentId: 0,
          enabled: 1
        },
        // dialog 默认数据
        defaultForm: {
          parentId: 0,
          enabled: 1
        }
      },
      // 导入文件弹出框
      importDialog: {
        show: false,
        action: null // 导入文件Url
      }
    }
  },
  // 组件创建完成执行
  created() {
    this.handleQuery()
    this.handleDict()
  },
  methods: {
    /**
     * 根据搜素条件查询表格数据
     */
    handleQuery() {
      // 初始化表格
      this.loading = true
      getDepartmentTree(this.model).then(res => {
        this.departmentList = res
        this.departmentNodes = this.parseTreeNode(res) // 将各个节点的父子关系保存起来
        this.loading = false
      })
    },
    /**
     * 获取部门的数据字典
     */
    handleDict() {
      // 获得本单据的详细定义信息
      getDepartmentDict().then(res => {
        this.departmentDict = res // 本单据的详细定义信息
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
      if (row && row.id) {
        this.dialog.form.parentId = row.id || 0
        this.dialog.form.orderNum = (this.departmentNodes[row.id].childrenNum || 0) + 1
      } else {
        this.dialog.form.orderNum = (this.departmentList.length || 0) + 1
      }
      this.dialog.show = true
      this.dialog.title = '添加部门'
    },
    /**
     * 编辑按钮操作
     * @param row 当前行数据
     */
    handleUpdate(row) {
      if (row != null) {
        this.dialog.form = this.deepClone(row)
        this.dialog.form.parentId = this.departmentNodes[row.id].parentId || 0
        this.dialog.form.enabled = +row.enabled // Boolean 转 int
      }
      this.dialog.show = true
      this.dialog.title = '编辑部门'
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该部门及其子部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDepartment(row.id).then(res => {
          this.$notify.success({
            title: '成功',
            message: '删除部门成功'
          })
          this.handleQuery()
        }).catch(err => {
          console.log(err)
          this.$notify.error({
            title: '失败',
            message: '删除部门失败'
          })
        })
      })
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
