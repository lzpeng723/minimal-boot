<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      title="导入文件"
      :visible.sync="dialog.show"
      width="600px"
      @open="openDialog"
      @close="closeDialog"
    >
      <div class="upload-container">
        <el-upload
          :action="dialog.action"
          :headers="headers"
          :multiple="false"
          :limit="1"
          :before-upload="beforeUpload"
          :on-success="onSuccess"
          :on-error="onError"
          :on-remove="onRemove"
          :file-list="fileList"
          :on-exceed="onExceed"
          drag
          show-file-list
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"
          class="image-upload"
        >
          <i class="el-icon-upload" />
          <div v-if="fileList.length === 0" class="el-upload__text">
            请将文件拖入或 <em>点击上传</em>
          </div>
          <div v-else class="el-upload__text">已上传</div>
        </el-upload>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth' // get token from ls
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
export default {
  name: 'ImportDialog',
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: {
      type: Object,
      required: true,
      default() {
        return { show: false }
      }
    }
  },
  data() {
    return {
      fileList: []
    }
  },
  computed: {
    headers() {
      const token = getToken()
      return {
        Authorization: `Bearer ${JSON.parse(token).access_token}`
      }
    }
  },
  methods: {
    // 打开Dialog
    openDialog() {
    },
    /**
     * 关闭dialog
     */
    closeDialog() {
      this.dialog.show = false
    },
    beforeUpload(file) {
      console.log(file)
      // 调用父组件的 beforeUpload 方法
      this.$emit('beforeUpload', file)
    },
    // 上传成功调用
    onSuccess(response, file) {
      const { code, msg } = response
      if (code === 10200) {
        this.$message({
          message: msg || '导入成功',
          type: 'success'
        })
        this.$emit('onSuccess', response.data)
        this.fileList = []
      } else {
        this.$message({
          message: (msg && `导入失败，失败原因：${msg}`) || '导入失败',
          type: 'error'
        })
        this.$emit('onError', file)
      }
    },
    // 上传失败调用
    onError(err) {
      const errMsg = err.message && JSON.parse(err.message)
      this.$message({
        message: (errMsg && errMsg.msg && `导入失败，导入原因：${errMsg.msg}`) || '导入失败',
        type: 'error'
      })
      this.$emit('onError', err)
    },
    // 删除服务器资源调用
    onRemove() {
      this.$message({
        message: '删除成功',
        type: 'success'
      })
      this.$emit('onRemove')
    },
    // 多次上传调用
    onExceed() {
      this.$message({
        message: '每次只能上传一个文件',
        type: 'warning'
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
