<template>
  <div>
    <el-dialog
      v-el-drag-dialog
      :title="title"
      :visible.sync="show"
      width="800px"
      height="400px"
      style="text-align: center"
      @open="openDialog"
      @close="closeDialog"
    >
      <el-table
        v-loading="false"
        stripe
        border
        :data="tableData"
      >
        <!--表格自适应宽度: https://www.jianshu.com/p/b1e7e2a695c0-->
        <el-table-column
          v-for="column in showColumns"
          :key="column"
          :label="dictValues[column] && dictValues[column].value || column"
          :prop="column"
          align="center"
          width="180"
        />
      </el-table>
      <!--确定 取消按钮-->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import elDragDialog from '@/directive/el-drag-dialog' // 可拖拽Dialog
import {
  getTableData
} from '@/api/components/selectDialog' // 选择其他单据api
// F7 选择框
export default {
  name: 'SelectDialog',
  directives: { elDragDialog }, // 注册可拖拽Dialog directive
  props: {
    show: Boolean,
    // 对话框标题
    title: String,
    // 实体名
    entity: {
      type: String,
      required: true
    },
    // 字段名
    column: String,
    // 过滤条件
    filter: Array,
    // 显示哪些列
    showColumns: {
      type: Array,
      required: false,
      default() {
        return ['number', 'name']
      }
    },
    // 是否支持多选
    multiple: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      // 查询出的数据
      tableData: [],
      // 缓存每列的详细信息 {fieldName:columnInfo}
      columnInfo: {},
      // 缓存每列的数据字典 {fieldName:dictValues}
      dictValues: {
        number: {
          key: 'number',
          value: '编码'
        },
        name: {
          key: 'name',
          value: '名称'
        }
      }
    }
  },
  methods: {
    // 打开Dialog
    openDialog() {
      getTableData({
        entity: this.entity,
        column: this.column,
        filter: this.filter,
        showColumns: this.showColumns
      }).then(res => {
        this.tableData = res.tableData
        const result = this.parseTableMeta(res.tableInfo) // 解析单据的详细定义信息
        this.columnInfo = result.columnInfo // 每列的详细信息
        this.dictValues = result.dictValues // 每列的数据字典
      })
    },
    // 关闭Dialog
    closeDialog() {
      this.dialog.show = false
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
