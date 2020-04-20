<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="model" :inline="true" label-width="68px">
      <el-form-item label="日志文件" prop="fileName">
        <el-input
          v-model="model.fileName"
          placeholder="请输入日志文件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="model = {}">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="serverLogList"
    >
      <el-table-column label="日志文件" align="center" prop="fileName">
        <template slot-scope="{row}">
          <a :href="`${baseURL}/monitor/serverLog/download/${row.fileName}`">
            <el-button type="text" >{{ row.fileName }}</el-button>
          </a>
        </template>
      </el-table-column>
      <el-table-column label="真实路径" align="center" prop="canonicalPath" />
      <el-table-column label="大小" align="center" prop="size">
        <template slot-scope="{row}">
          <span>{{ formatBytes(row.size) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastModifiedTime" width="180">
        <template slot-scope="{row}">
          <span>{{ parseTime(row.lastModifiedTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="page"
      :limit.sync="size"
      @pagination="handleQuery"
    />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getServerLogPage, exportServerLog } from '@/api/monitor/serverLog'// 后台日志api
export default {
  name: 'ServerLogList',
  data() {
    return {
      // 当前选中是否是单行
      single: false,
      // 当前选中是否是多行
      multiple: false,
      // 表格是否在加载中
      loading: false,
      // 当前第几页
      page: 1,
      // 每页多少条数据
      size: 20,
      // 一共多少条数据
      total: 0,
      // 查询条件
      model: {},
      // 表格数据
      serverLogList: []
    }
  },
  computed: {
    ...mapGetters([
      'device'
    ])
  },
  created() {
    this.handleQuery() // 获得表格数据
  },
  methods: {
    /**
     * 获得表格数据
     */
    handleQuery() {
      this.loading = true
      getServerLogPage(this.page, this.size, this.model).then(res => {
        this.serverLogList = res.list
        this.page = res.page
        this.total = res.total
        this.loading = false
      })
    },
    // 导出
    handleExport() {

    },
    // 下载日志文件
    download(fileName) {
      // desktop mobile
      switch (this.device) {
        case 'desktop':
          exportServerLog(fileName).then(res => {
            this.downloadFile(res, fileName)
          })
          break
        case 'mobile':
          exportServerLog(fileName).then(res => {
            // const blob = this.type(res) === 'Blob' ? res : new Blob([res])
            // const fileReader = new FileReader()
            // fileReader.onload = function() {
            //   const blobAsDataUrl = fileReader.result
            //   // window.location = blobAsDataUrl
            //   window.open(blobAsDataUrl)
            // }
            // fileReader.readAsDataURL(blob)
            // const BlobBuilder = window.BlobBuilder ||
            //   window.WebKitBlobBuilder ||
            //   window.MozBlobBuilder ||
            //   window.MSBlobBuilder
            // const blobBuilder = new BlobBuilder()
            // blobBuilder.append(res)
            // const data = blobBuilder.getBlob()
            // this.downloadFile(data, fileName)
          })
          // TODO 其实是暂时不会在手机端下载
          this.$message('手机端不允许下载文件')
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
