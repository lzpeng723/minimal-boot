<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="dialog.title"
      :visible.sync="dialog.show"
      width="600px"
      height="400px"
      @open="openDialog"
      @close="closeDialog"
      style="text-align: center"
    >
      <el-transfer
        v-model="dialog.roles"
        style="text-align: left; display: inline-block"
        filterable
        :titles="['未拥有角色', '已拥有角色']"
        :props="{key: 'id',label: 'name'}"
        :data="allRoles"
        @change="handleChange"
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
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import { getRoleList } from '@/api/sys/role' // 角色api
import { setRoles } from '@/api/sys/user' // 用户api

export default {
  name: 'SelectRoleDialog',
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: Object
  },
  data() {
    return {
      allRoles: [],
      roles: []
    }
  },
  methods: {
    // 打开Dialog
    openDialog() {
      getRoleList().then(res => {
        this.allRoles = res
      })
    },
    // 关闭Dialog
    closeDialog() {
      this.dialog.show = false
    },
    // 列表发生变化
    handleChange(value, direction, movedKeys) {
      console.log(value, direction, movedKeys)
    },
    // 分配角色
    submitForm() {
      setRoles(this.dialog.userId, this.dialog.roles).then(res => {
        this.$notify({
          title: '成功',
          message: '分配角色成功',
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
