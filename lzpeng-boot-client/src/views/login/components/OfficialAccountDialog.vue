<template>
  <div>
    <!-- 添加或修改岗位对话框 -->
    <el-dialog v-el-drag-dialog :title="dialog.title" :visible.sync="dialog.show" width="500px" @close="closeDialog">
      <div class="wechat-official-account"></div>
      <div style="font-size: 20px;margin: 0 auto; width: 100%;text-align: center;">
        <span style="color: red;">关注公众号</span>
        回复
        <span style="color: red;">体验</span><br>
        获取体验账户
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
export default {
  name: 'OfficialAccountDialog', // 组件名称
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    // 从父组件接收的参数
    dialog: {
      type: Object,
      default() {
        return {
          title: '提示',
          show: true
        }
      }
    }
  },
  data() {
    return {

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
      this.dialog.show = false
    }
  }
}
</script>

<style lang="scss" scoped>
  .wechat-official-account {
    min-height: 215px;
    width: 215px;
    background-image: url('../../../assets/login/wechat-official-account.jpg');
    background-size: 100% 100%;
    overflow: hidden;
    margin: 0 auto;
  }
</style>
