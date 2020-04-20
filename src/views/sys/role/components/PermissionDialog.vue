<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="dialog.title"
      :visible.sync="dialog.show"
      width="800px"
      height="400px"
      @open="openDialog"
      @close="closeDialog"
    >
      <tree-transfer
        :title="['未拥有权限','已拥有权限']"
        :from_data="fromData"
        :to_data="dialog.permissions"
        :default-props="{label:'title'}"
        :pid="'parentId'"
        :mode="mode"
        height="400px"
        filter
        @addBtn="add"
        @removeBtn="remove"
      />
      <!--确定 取消按钮-->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getMenuTree } from '@/api/sys/menu' // 菜单API
import { setPermissions, noPermissions } from '@/api/sys/role' // 角色API
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import treeTransfer from 'el-tree-transfer' // 引入树形穿梭框

export default {
  name: 'PermissionDialog',
  components: { treeTransfer }, // 注册树形穿梭框
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: Object
  },
  data() {
    return {
      mode: 'transfer', // transfer addressList
      fromData: []
    }
  },
  methods: {
    // 切换模式 现有树形穿梭框模式transfer 和通讯录模式addressList
    changeMode() {
      if (this.mode === 'transfer') {
        this.mode = 'addressList'
      } else {
        this.mode = 'transfer'
      }
    },
    // 监听穿梭框组件添加
    add(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log('fromData:', fromData)
      console.log('toData:', toData)
      console.log('obj:', obj)
      this.dialog.permissions = toData
    },
    // 监听穿梭框组件移除
    remove(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log('fromData:', fromData)
      console.log('toData:', toData)
      console.log('obj:', obj)
      this.dialog.permissions = toData
    },
    // 打开Dialog
    openDialog() {
      noPermissions(this.dialog.roleId).then(res => {
        this.fromData = this.listToTree(res)
      })
    },
    // 关闭Dialog
    closeDialog() {
      this.dialog.show = false
    },
    // 分配权限
    submitForm() {
      const list = this.treeToList(this.dialog.permissions)
      const permissionIds = list.map(permission => permission.id)
      setPermissions(this.dialog.roleId, permissionIds).then(res => {
        this.$notify({
          title: '成功',
          message: '分配权限成功',
          type: 'success'
        })
        this.closeDialog()
        this.$emit('refresh')
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
