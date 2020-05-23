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
        fit
        stripe
        border
        highlight-current-row
        v-loading="loading"
        :data="tableData"
        @selection-change="handleSelectionChange"
        @current-change="handleCurrentChange"
      >
        <el-table-column v-if="multiple" type="selection" width="55" align="center" />
        <el-table-column
          v-for="column in getShowColumns()"
          :key="column"
          :label="columnInfo[column] && columnInfo[column].apiModelProperty || column"
          :prop="column"
          align="center"
        />
      </el-table>
      <!--确定 取消按钮-->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialog">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import parseSql from '@/utils/parseSql'
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
    filters: Array,
    // 显示哪些列
    showColumns: {
      type: Array,
      required: false,
      default() {
        return []
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
      // 当前选择的行
      selectRows: [],
      // 表格是否正在加载中
      loading: false,
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
    /**
     * 过滤id字段,id字段不展示
     * @return {*}
     */
    getShowColumns() {
      return this.showColumns.filter(key => key !== 'id')
    },
    // 打开Dialog
    openDialog() {
      // 组装过滤条件
      const filterItems = []
      for (let i = 0; i < this.filters.length; i++) {
        const filter = this.filters[i]
        const filterItem = parseSql.getFilter(filter.key, filter.op, filter.value)
        filterItems.push(filterItem)
      }
      const mergeFilter = parseSql.mergeFilter(filterItems, 'and')
      this.loading = true
      getTableData({
        entity: this.entity,
        column: this.column,
        filter: mergeFilter,
        showColumns: this.getShowColumns()
      }).then(res => {
        this.tableData = res.tableData // 表格数据
        const result = this.parseTableMeta(res.tableInfo) // 解析单据的详细定义信息
        this.columnInfo = result.columnInfo // 每列的详细信息
        this.dictValues = result.dictValues // 每列的数据字典
        this.loading = false
      })
    },
    /**
     * 点击确定按钮
     */
    submitDialog() {
      const selectRows = this.selectRows
      this.closeDialog()
      this.$emit('submit', selectRows)
    },
    // 关闭Dialog
    closeDialog() {
      this.tableData = [] // 表格数据
      this.columnInfo = [] // 每列的详细信息
      this.dictValues = [] // 每列的数据字典
      this.selectRows = [] // 清空选择行
      this.$emit('close', this.selectRows)
    },
    // 多选选择行事件
    handleSelectionChange(selection) {
      this.selectRows = selection
    },
    // 单选选择行事件
    handleCurrentChange(currentRow, oldCurrentRow) {
      this.selectRows = currentRow
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
