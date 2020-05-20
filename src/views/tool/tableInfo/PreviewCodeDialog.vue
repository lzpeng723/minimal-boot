<template>
  <el-dialog
    :visible.sync="dialog.show"
    width="80%"
    @open="openDialog"
    @close="closeDialog"
  >
    <div slot="title" class="header-title">
      <span>{{dialog.fileName}}</span>
      <el-button type="primary" @click="handleClipboard($event)">复制代码</el-button>
    </div>
    <codemirror
      :code="dialog.code"
      :options="cmOptions"
    />
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeDialog">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import clipboard from '@/utils/clipboard'
import { codemirror } from 'vue-codemirror'
import { cmOptions } from '@/utils/codemirror'
export default {
  name: 'PreviewCodeDialog',

  components: { codemirror },
  props: {
    dialog: {
      type: Object,
      default() {
        return {
          fileName: '',
          show: false,
          fileType: '',
          code: ''
        }
      }
    }
  },
  data() {
    return {
      cmOptions
    }
  },
  created() {
    this.openDialog()
  },
  methods: {
    /**
     * 复制代码
     */
    handleClipboard(event) {
      clipboard(this.dialog.code, event)
    },
    /**
     * 打开dialog
     */
    openDialog() {
      if (this.dialog.fileType === 'text/x-vue') {
        // vue 语法渲染失败
        this.cmOptions.mode = 'text/javascript'
      } else {
        this.cmOptions.mode = this.dialog.fileType
      }
    },

    /**
     * 关闭dialog
     */
    closeDialog() {
      this.$emit('close')
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
