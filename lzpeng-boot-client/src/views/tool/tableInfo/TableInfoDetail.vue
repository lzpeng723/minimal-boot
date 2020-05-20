<template>

  <div class="app-container">
    <sticky :class-name="'sub-navbar'">
      <el-button @click="openGenDialog">代码生成情况</el-button>
    </sticky>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" style="margin-top: 20px;" :disabled="isBillId(form.id)">
      <el-row>
        <el-col :span="12">
          <el-form-item label="实体名" prop="className">
            <el-input v-model="form.className" placeholder="实体名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="表名" prop="tableName">
            <el-input v-model="form.tableName" placeholder="表名" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-collapse v-model="activeNames">
      <el-collapse-item name="1">
        <template slot="title">
          <el-button style="margin-left: 15px" type="text">字段信息>></el-button>
        </template>
        <el-table
          v-loading="loading"
          :data="form.columns"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="别名" align="center" prop="apiModelProperty">
            <template slot-scope="{row}">
              <el-button type="text" @click="handleDetail(row)">{{ row.apiModelProperty }}</el-button>
            </template>
          </el-table-column>
          <el-table-column label="属性名" align="center" prop="fieldName" />
          <el-table-column label="属性类型" align="center" prop="className" />
          <el-table-column label="字段名" align="center" prop="columnName" />
          <el-table-column label="字段类型" align="center" prop="typeName" />
          <el-table-column label="字段长度" align="center" prop="size" />
        </el-table>

        <el-dialog title="字段详细信息" :visible.sync="dialog.show" width="%50">
          <el-table :data="dialog.dictValue">
            <el-table-column property="key" label="键" />
            <el-table-column property="value" label="值" />
          </el-table>
        </el-dialog>
      </el-collapse-item>
    </el-collapse>
    <generate-code-dialog v-show="genDialog.show" :dialog="genDialog" @showCode="showCode" />
    <!--不知为何 代码预览弹出框不能正常关闭 所以使用 v-if 实现-->
    <preview-code-dialog v-if="previewDialog.show" :dialog="previewDialog" @close="hideCode" />

  </div>

</template>

<script>
import Sticky from '@/components/Sticky/index'
import { getTableInfoDict, getTableInfoById } from '@/api/tool/tableInfo' // 数据字典api
import GenerateCodeDialog from './GenerateCodeDialog'
import PreviewCodeDialog from './PreviewCodeDialog'

export default {
  name: 'TableInfoDetail',
  components: { PreviewCodeDialog, GenerateCodeDialog, Sticky },
  data() {
    return {
      loading: true,
      form: {
        enabled: 1,
        columns: []
      }, // 当前单据信息
      // 表信息数据字典
      tableInfoDict: {},
      // 缓存每列的详细信息 {fieldName:columnInfo}
      columnInfo: {},
      // 缓存每列的数据字典 {fieldName:dictValues}
      dictValues: {},
      // 目标单据表信息数据字典
      targetTableInfoDict: {},
      // 缓存目标单据每列的详细信息 {fieldName:columnInfo}
      targetColumnInfo: {},
      // 缓存目标单据每列的数据字典 {fieldName:dictValues}
      targetDictValues: {},
      // 表单校验
      rules: {
        className: [
          { required: true, message: '实体名不能为空', trigger: 'blur' }
        ],
        tableName: [
          { required: true, message: '表名不能为空', trigger: 'blur' }
        ]
      },
      // 枚举字段详细信息
      dialog: {
        show: false,
        dictValue: []
      },
      // 生成代码弹出框
      genDialog: {
        show: false,
        className: null
      },
      // 预览代码弹出框
      previewDialog: {
        // 是否显示预览代码
        show: false,
        // 文件名称
        fileName: '',
        // 文件内容
        fileType: '',
        // 代码
        code: ''
      },
      activeNames: ['1']
    }
  },
  mounted() {
    console.log(this.$route.params)
  },
  async created() {
    // 获取数据字典
    await this.handleDict()
    // 获取或初始化单据信息
    await this.handleModel()
  },
  methods: {

    /**
     * 获取或初始化单据信息
     */
    async handleModel() {
      this.loading = true
      this.form.id = this.$route.params.id
      if (this.isBillId(this.form.id)) {
        this.form = await getTableInfoById(this.form.id)
        this.form.enabled = +this.form.enabled
        this.targetTableInfoDict = this.form // 目标单据的详细定义信息
        const result = this.parseTableMeta(this.form) // 解析目标单据的详细定义信息
        this.targetColumnInfo = result.columnInfo // 目标单据每列的详细信息
        this.targetDictValues = result.dictValues // 目标单据每列的数据字典
      }
      this.loading = false
    },
    /**
     * 获取数据字典
     */
    async handleDict() {
      // 获得本单据的详细定义信息
      const dict = await getTableInfoDict()
      this.tableInfoDict = dict // 本单据的详细定义信息
      const result = this.parseTableMeta(dict) // 解析本单据的详细定义信息
      this.columnInfo = result.columnInfo // 每列的详细信息
      this.dictValues = result.dictValues // 每列的数据字典
    },
    // 表单提交
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          const model = this.form
          console.log(model)
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    /**
     * 查看该字段详细信息
     * @param row
     */
    handleDetail(row) {
      if (row.dictValues.length > 0) {
        this.dialog.show = true
        this.dialog.dictValue = this.targetDictValues[row.fieldName]
      } else {
        this.$alert(row.comment, '提示', {
          confirmButtonText: '确定',
          callback: action => {
          }
        })
      }
    },
    /**
     * 打开代码生成Dialog
     */
    openGenDialog() {
      this.genDialog.show = true
      this.genDialog.className = this.form.className
    },
    /**
     * 显示代码预览窗口
     * @param res
     */
    showCode(res) {
      this.genDialog.show = false
      this.$nextTick(() => {
        this.previewDialog = { ...res }
        this.previewDialog.show = true
      })
    },
    /**
     * 隐藏代码预览窗口
     * @param res
     */
    hideCode() {
      this.previewDialog.show = false
      this.$nextTick(() => {
        this.genDialog.show = true
      })
    }
  }
}
</script>

