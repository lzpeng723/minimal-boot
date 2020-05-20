<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      title="代码生成情况"
      :visible.sync="dialog.show"
      width="600px"
      @open="openDialog"
      @close="closeDialog"
    >
      <el-table
        v-loading="loading"
        :data="genList"
      >
        <el-table-column label="代码生成模板编码" align="center" prop="number" />
        <el-table-column label="代码生成模板名称" align="center" prop="name" />
        <el-table-column label="状态" align="center" prop="gen" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="{row}">
            <a :href="`${baseURL}/tool/gen/gen/${row.id}?className=${dialog.className}`">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-printer"
              >生成
              </el-button>
            </a>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handlePreview(row)"
            >预览
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <a :href="`${baseURL}/tool/gen/gen?className=${dialog.className}`">
          <el-button type="primary">生成所有</el-button>
        </a>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import { getGenInfo, previewGenCode } from '@/api/tool/gen'// 代码生成模板api
export default {
  name: 'GenerateCodeDialog',
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    dialog: {
      type: Object,
      required: true,
      default() {
        return { show: false, className: null }
      }
    }
  },
  data() {
    return {
      // 表格数据
      genList: [],
      // 表格是否正在加载
      loading: false
    }
  },
  created() {
    // this.openDialog()
  },
  methods: {
    /**
     * 获得表格数据
     */
    handleQuery() {
      this.loading = true
      getGenInfo(this.dialog.className).then(res => {
        this.genList = res
        this.loading = false
      })
    },
    /**
     * 预览代码
     * @param row
     */
    handlePreview(row) {
      previewGenCode(row.id, this.dialog.className).then(res => {
        this.$emit('showCode', res)
      })
    },
    // 打开Dialog
    openDialog() {
      this.handleQuery()
    },
    /**
     * 关闭dialog
     */
    closeDialog() {
      this.dialog.show = false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
