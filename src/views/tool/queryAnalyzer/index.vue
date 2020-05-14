<template>
  <div class="app-container">
    <el-row>
      <el-button size="mini" round @click="executeSQL">执行SQL</el-button>
      <el-button size="mini" round @click="executeJPQL">执行JPQL</el-button>
      <el-button size="mini" round>执行Rhino脚本</el-button>
      <el-button size="mini" round>执行Nashorn脚本</el-button>
      <el-button size="mini" round>id查表名</el-button>
      <el-button size="mini" round>实体查表名</el-button>
      <el-button size="mini" round>显示表定义</el-button>
      <el-button size="mini" round>显示所有表</el-button>
      <el-button size="mini" round disabled>格式化代码</el-button>
      <el-button size="mini" round disabled>查BOTP关系</el-button>
    </el-row>

    <codemirror
      :code="code"
      :options="cmOptions"
      @ready="onCmReady"
      @focus="onCmFocus"
      @input="onCmCodeChange"
    />

    <el-table
      stripe
      border
      v-loading="false"
      :data="result"
    >
      <!--表格自适应宽度: https://www.jianshu.com/p/b1e7e2a695c0-->
      <el-table-column
        v-for="key in keys"
        :key="key.label || key"
        :label="key.label || key"
        :prop="key.prop || key"
        align="center"
        width="180"
      />
    </el-table>
  </div>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import { cmOptions } from '@/utils/codemirror'
import { executeSQL, executeJPQL } from '@/api/tool/queryAnalyzer' // 查询分析器api

export default {
  name: 'GenDetail',
  components: { codemirror },
  data() {
    return {
      cmOptions,
      code: '',
      result: [],
      keys: []
    }
  },
  created() {
    this.cmOptions.mode = 'text/x-sql'
    this.result = [{ name: 'name', number: 'number' }]
    this.keys = [{ label: '名称', prop: 'name' }, { label: '编码', prop: 'number' }]
  },
  methods: {
    onCmReady(cm) {
      // cm 启动完成
      console.log('the editor is readied!', cm, this.cmOptions)
    },
    onCmFocus(cm) {
      // 获得焦点
      console.log('the editor is focused!', cm, this.cmOptions)
    },
    onCmCodeChange(newCode) {
      // 编辑器文本改变
      console.log('this is new code', newCode, this.cmOptions)
      this.code = newCode
    },
    /**
     * 执行 SQL
     */
    executeSQL() {
      executeSQL(this.code).then(res => {
        if (res) {
          // sql 查询结果
          this.result = res
          this.keys = Object.keys(res[0] || {})
        }
      })
    },
    /**
     * 执行 JPQL
     */
    executeJPQL() {
      executeJPQL(this.code).then(res => {
        if (res) {
          // sql 查询结果
          this.result = res
          this.keys = Object.keys(res[0] || {})
        }
      })
    }

  }
}
</script>

