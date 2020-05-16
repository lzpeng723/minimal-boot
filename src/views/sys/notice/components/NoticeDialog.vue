<template>
  <div>
    <!-- 添加或修改通知对话框 -->
    <el-dialog v-el-drag-dialog :title="dialog.title" :visible.sync="dialog.show" width="500px" @close="closeDialog">
      <el-form ref="form" :model="dialog.form" :rules="rules" label-width="80px">
        <el-form-item label="通知编码" prop="number">
          <el-input v-model="dialog.form.number" placeholder="请输入通知编码" />
        </el-form-item>
        <el-form-item label="通知名称" prop="name">
          <el-input v-model="dialog.form.name" placeholder="请输入通知名称" />
        </el-form-item>
        <el-form-item label="通知类型" prop="noticeType">
          <el-select v-model="dialog.form.noticeType" placeholder="通知类型" clearable size="small">
            <el-option
              v-for="dict in dialog.dictValues['noticeType']"
              :key="dict.key"
              :label="dict.value"
              :value="dict.key"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="dialog.form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import { insertNotice, updateNotice } from '@/api/sys/notice' // 通知api
export default {
  name: 'NoticeDialog', // 组件名称
  components: {
  },
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: Object // 从父组件接收的参数
  },
  data() {
    return {
      // 表单校验
      rules: {
        name: [
          { required: true, message: '通知名称不能为空', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '通知编码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  // 初始化组件时执行
  created() {
  },
  methods: {
    /**
     * 关闭弹出框
     */
    closeDialog() {
      this.dialog.form = this.deepClone(this.dialog.defaultForm)
      this.resetForm('form')
      this.dialog.show = false
    },
    /**
     * 表单提交
     */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          const model = this.dialog.form
          if (model.id) {
            updateNotice(model.id, this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '修改通知成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
            })
          } else {
            insertNotice(this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '新增通知成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
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

<style lang="scss" scoped>

</style>
