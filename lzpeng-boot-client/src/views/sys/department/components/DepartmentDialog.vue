<template>
  <div>
    <!-- 添加或修改部门对话框 -->
    <el-dialog v-el-drag-dialog :title="dialog.title" :visible.sync="dialog.show" width="600px" @close="closeDialog">
      <el-form ref="form" :model="dialog.form" :rules="rules" label-width="80px">
        <el-row>
          <!--父级部门-->
          <el-col :span="24">
            <el-form-item label="上级部门">
              <treeselect
                v-model="dialog.form.parentId"
                :options="departmentOptions"
                :normalizer="normalizerDepartmentOptions"
                :show-count="true"
                placeholder="选择上级部门"
              />
            </el-form-item>
          </el-col>
          <!--部门编码-->
          <el-col :span="12">
            <el-form-item label="部门编码" prop="number">
              <el-input type="text" v-model="dialog.form.number" placeholder="请输入部门编码" />
            </el-form-item>
          </el-col>
          <!--部门名称-->
          <el-col :span="12">
            <el-form-item label="部门名称" prop="name">
              <el-input type="text" v-model="dialog.form.name" placeholder="请输入部门名称" />
            </el-form-item>
          </el-col>
          <!--顺序号-->
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="dialog.form.orderNum" controls-position="right" :min="0" disabled/>
            </el-form-item>
          </el-col>
          <!--部门状态-->
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-radio-group v-model="dialog.form.enabled">
                <el-radio
                  v-for="dict in dialog.dictValues['enabled']"
                  :key="dict.key"
                  :label="dict.key"
                >{{ dict.value }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <!--备注-->
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="dialog.form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--确定 取消按钮-->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import { getDepartmentTree, insertDepartment, updateDepartment } from '@/api/sys/department' // 获取树形结构部门
import Treeselect from '@riophae/vue-treeselect' // 树形结构选择器 Select
import '@riophae/vue-treeselect/dist/vue-treeselect.css' // 树形结构选择器样式
export default {
  name: 'DepartmentDialog', // 组件名称
  components: {
    Treeselect // 注册树形结构选择器和
  },
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: Object // 从父组件接收的参数
  },
  data() {
    return {
      // 父部门是叶子还是分支
      parentDepartment: {},
      // 部门树
      departmentOptions: [],
      // 组件树
      componentOptions: [],
      // 表单校验
      rules: {
        name: [
          { required: true, message: '部门名称不能为空', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '部门顺序不能为空', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '部门编码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  // 初始化组件时执行
  created() {
    // 获取部门树
    this.getDepartmentTreeselect()
  },
  methods: {
    // 关闭Dialog
    closeDialog() {
      this.dialog.form = this.deepClone(this.dialog.defaultForm)
      this.resetForm('form')
      this.dialog.show = false
    },
    // 表单提交
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          const model = this.dialog.form
          if (+model.parentId === 0) {
            // 0 代表没有父部门
            model.parentId = null
          }
          if (model.id) {
            updateDepartment(model.id, this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '修改部门成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
              this.getDepartmentTreeselect()
            })
          } else {
            insertDepartment(this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '新增部门成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
              this.getDepartmentTreeselect()
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    /** 查询部门下拉树结构 */
    getDepartmentTreeselect() {
      getDepartmentTree().then(res => {
        this.departmentOptions = []
        const department = { id: 0, name: '主部门', children: res }
        this.departmentOptions.push(department)
      })
    },
    /** 转换部门数据结构 */
    normalizerDepartmentOptions(node) {
      // {key:show} 可以选择的key 是否显示
      // 将该id是叶子节点还是分支节点存入变量
      if (node.children && !node.children.length) {
        // 叶子节点
        this.parentDepartment[node.id] = { 0: false, 1: false, 2: true } // 'Leaf'
        delete node.children
      } else {
        // 分支节点
        this.parentDepartment[node.id] = { 0: true, 1: true, 2: false }// 'Branch'
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
