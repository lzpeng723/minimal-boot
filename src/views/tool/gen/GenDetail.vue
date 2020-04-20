<template>
  <div class="app-container">
    <sticky :class-name="'sub-navbar'">
      <el-button @click="submitForm">保存</el-button></sticky>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" style="margin-top: 20px;">
      <el-row>
        <el-col :span="8">
          <el-form-item label="模板编码" prop="number">
            <el-input v-model="form.number" placeholder="请输入模板编码" :disabled="isBillId(form.id)" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="模板名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入模板名称" :disabled="isBillId(form.id)" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="代码类型" prop="type">
            <el-select v-model="form.type" placeholder="请选择代码类型" :disabled="isBillId(form.id)">
              <el-option
                v-for="item in dictValues['type']"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-form-item label="生成路径" prop="path">
          <el-input v-model="form.path" type="text" placeholder="生成路径" :disabled="isBillId(form.id)" />
        </el-form-item>
      </el-row>
    </el-form>
    <codemirror
      :code="form.template"
      :options="cmOptions"
      @ready="onCmReady"
      @focus="onCmFocus"
      @input="onCmCodeChange"
    />
  </div>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import { cmOptions } from '@/utils/codemirror'
import Sticky from '@/components/Sticky/index'
import { getGenDict, insertGen, updateGen, getGenById } from '@/api/tool/gen' // 模板api

export default {
  name: 'GenDetail',
  components: { Sticky, codemirror },
  data() {
    return {
      cmOptions,
      // 代码生成模板数据字典
      genDict: {},
      // 缓存每列的详细信息 {fieldName:columnInfo}
      columnInfo: {},
      // 缓存每列的数据字典 {fieldName:dictValues}
      dictValues: {},
      // 当前单据信息
      form: {
        enabled: 1
      },
      // 表单校验
      rules: {
        name: [
          { required: true, message: '模板名称不能为空', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '模板编码不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '代码类型不能为空', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '生成路径不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    console.log(this.$route.params)
  },
  async created() {
    // 获取或初始化单据信息
    await this.handleModel()
    // 获取数据字典
    await this.handleDict()
    const codeType = this.dictValues['type'][this.form.type].value
    if (codeType === 'text/x-vue') {
      // vue 语法渲染失败
      this.cmOptions.mode = 'text/javascript'
    } else {
      this.cmOptions.mode = codeType
    }
  },
  methods: {
    onCmReady(cm) {
      // cm 启动完成
      console.log('the editor is readied!', cm)
    },
    onCmFocus(cm) {
      // 获得焦点
      console.log('the editor is focused!', cm)
    },
    onCmCodeChange(newCode) {
      // 编辑器文本改变
      console.log('this is new code', newCode)
      this.form.template = newCode
    },
    /**
     * 获取或初始化单据信息
     */
    async handleModel() {
      this.form.id = this.$route.params.id
      if (this.isBillId(this.form.id)) {
        this.form = await getGenById(this.form.id)
        this.form.enabled = +this.form.enabled
      } else {

      }
    },
    /**
     * 获取数据字典
     */
    async handleDict() {
      // 获得本单据的详细定义信息
      const dict = await getGenDict()
      this.genDict = dict // 本单据的详细定义信息
      const result = this.parseTableMeta(dict) // 解析本单据的详细定义信息
      this.columnInfo = result.columnInfo // 每列的详细信息
      this.dictValues = result.dictValues // 每列的数据字典
    },
    // 表单提交
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          const model = this.form
          if (this.isBillId(model.id)) {
            updateGen(model.id, this.filterModel(model)).then(res => {
              this.form = res
              this.form.enabled = +res.enabled
              this.$notify({
                title: '成功',
                message: '修改模板成功',
                type: 'success'
              })
            })
          } else {
            delete model.id
            insertGen(this.filterModel(model)).then(res => {
              this.form = res
              this.form.enabled = +res.enabled
              this.$notify({
                title: '成功',
                message: '新增模板成功',
                type: 'success'
              })
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

