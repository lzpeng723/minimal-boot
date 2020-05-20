<template>
  <div>
    <!-- 添加或修改用户对话框 -->
    <el-dialog v-el-drag-dialog :title="dialog.title" :visible.sync="dialog.show" width="500px" @close="closeDialog">
      <el-form ref="form" :model="dialog.form" :rules="rules" label-width="80px">
        <el-form-item label="部门">
          <treeselect
            v-model="dialog.form.treeId"
            :options="leftTreeData"
            :normalizer="normalizerLeftTreeData"
            :show-count="true"
            placeholder="请选择部门"
            @input="changeLeftTreeNode"
          />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="dialog.form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item v-if="!dialog.form.id" label="密码" prop="password">
          <el-input v-model="dialog.form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="name">
          <el-input v-model="dialog.form.name" placeholder="请输入用户真实姓名" />
        </el-form-item>
        <el-form-item label="是否删除" prop="enabled">
          <el-radio-group v-model="dialog.form.enabled">
            <el-radio
              v-for="dict in dialog.dictValues['enabled']"
              :key="dict.key"
              :label="dict.key"
            >{{ dict.value }}</el-radio>
          </el-radio-group>
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
import Treeselect from '@riophae/vue-treeselect' // 树形结构选择器 Select
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import { insertUser, updateUser, leftTreeData } from '@/api/sys/user' // 用户api
export default {
  name: 'UserDialog', // 组件名称
  components: {
    // 注册树形结构选择器
    Treeselect
  },
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: Object // 从父组件接收的参数
  },
  data() {
    return {
      // 左树数据
      leftTreeData: {},
      // 表单校验
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '用户真实姓名不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  // 初始化组件时执行
  created() {
    this.getLeftTreeData() // 获取左树数据
  },
  methods: {
    /**
     * 获得左树数据
     */
    getLeftTreeData() {
      leftTreeData().then(res => {
        this.leftTreeData = res
      })
    },
    /**
     * 转换左树数据
     * @param node
     */
    normalizerLeftTreeData(node) {
      return {
        id: node.id,
        label: node.name,
        children: node.children
      }
    },
    /**
     * 当左树节点更新时操作
     * @param value
     */
    changeLeftTreeNode(value) {

    },
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
            updateUser(model.id, this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '修改用户成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
            })
          } else {
            insertUser(this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '新增用户成功',
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
