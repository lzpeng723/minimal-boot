<template>
  <div>
    <!-- 添加或修改${chineseClassName}对话框 -->
    <el-dialog v-el-drag-dialog :title="dialog.title" :visible.sync="dialog.show" width="500px" @close="closeDialog">
      <el-form ref="form" :model="dialog.form" :rules="rules" label-width="80px"><#if entityType=="LeftTreeRightTable">
        <el-form-item label="${leftTree.chineseClassName}">
          <treeselect
            v-model="dialog.form.treeId"
            :options="leftTreeData"
            :normalizer="normalizerLeftTreeData"
            :show-count="true"
            placeholder="请选择${leftTree.chineseClassName}"
            @input="changeLeftTreeNode"
          />
        </el-form-item></#if>
        <el-form-item label="${chineseClassName}编码" prop="number">
          <el-input v-model="dialog.form.number" placeholder="请输入${chineseClassName}编码" />
        </el-form-item>
        <el-form-item label="${chineseClassName}名称" prop="name">
          <el-input v-model="dialog.form.name" placeholder="请输入${chineseClassName}名称" />
        </el-form-item>
        <el-form-item label="${chineseClassName}状态" prop="enabled">
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

<script><#if entityType!="Base">
import Treeselect from '@riophae/vue-treeselect' // 树形结构选择器 Select</#if>
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import { insert${simpleClassName}, update${simpleClassName}<#if entityType=="LeftTreeRightTable">, leftTreeData</#if> } from '@/api/${moduleName}/${simpleClassName?uncap_first}' // ${chineseClassName}api
export default {
  name: '${simpleClassName}Dialog', // 组件名称
  components: {<#if entityType!="Base">
      // 注册树形结构选择器
      Treeselect</#if>
  },
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: Object // 从父组件接收的参数
  },
  data() {
    return {<#if entityType=="LeftTreeRightTable">
      // 左树数据
      leftTreeData: {},</#if>
      // 表单校验
      rules: {
        name: [
          { required: true, message: '${chineseClassName}名称不能为空', trigger: 'blur' }
        ],
        number: [
          { required: true, message: '${chineseClassName}编码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  // 初始化组件时执行
  created() {<#if entityType=="LeftTreeRightTable">
    this.getLeftTreeData() // 获取左树数据</#if>
  },
  methods: {<#if entityType=="LeftTreeRightTable">
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

    },</#if>
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
            update${simpleClassName}(model.id, this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '修改${chineseClassName}成功',
                type: 'success'
              })
              this.closeDialog()
              this.$emit('refresh')
            })
          } else {
            insert${simpleClassName}(this.filterModel(model)).then(res => {
              this.$notify({
                title: '成功',
                message: '新增${chineseClassName}成功',
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
