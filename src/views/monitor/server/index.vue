<template>
  <div class="app-container">
    <el-row>
      <!-- CPU信息-->
      <el-col v-if="server.cpu" :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>CPU</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
              <tr>
                <th class="is-leaf">
                  <div class="cell">属性</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">值</div>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>
                  <div class="cell">核数</div>
                </td>
                <td>
                  <div v-if="server.cpu" class="cell">{{ server.cpu.coreNum }} 核 {{ server.cpu.threadNum }} 线程</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">用户使用率</div>
                </td>
                <td>
                  <div v-if="server.cpu" class="cell">{{ server.cpu.used }}%</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">系统使用率</div>
                </td>
                <td>
                  <div v-if="server.cpu" class="cell">{{ server.cpu.sys }}%</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">当前空闲率</div>
                </td>
                <td>
                  <div v-if="server.cpu" class="cell">{{ server.cpu.free }}%</div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
      <!-- 内存信息 -->
      <el-col v-if="server.mem || server.jvm" :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>内存</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
              <tr>
                <th class="is-leaf">
                  <div class="cell">属性</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">内存</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">JVM</div>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>
                  <div class="cell">总内存</div>
                </td>
                <td>
                  <div v-if="server.mem" class="cell">{{ formatBytes(server.mem.total) }}</div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell">{{ formatBytes(server.jvm.totalMemory) }}</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">已用内存</div>
                </td>
                <td>
                  <div v-if="server.mem" class="cell">{{ formatBytes(server.mem.used) }}</div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell">{{ formatBytes(server.jvm.usedMemory) }}</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">剩余内存</div>
                </td>
                <td>
                  <div v-if="server.mem" class="cell">{{ formatBytes(server.mem.free) }}</div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell">{{ formatBytes(server.jvm.freeMemory) }}</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">使用率</div>
                </td>
                <td>
                  <div v-if="server.mem" class="cell" :class="{'text-danger': server.mem.usage > 80}">{{
                    server.mem.usage }}%
                  </div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell" :class="{'text-danger': server.jvm.usage > 80}">{{
                    server.jvm.usage }}%
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
      <!-- JVM信息 -->
      <el-col v-if="server.jvm" :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Java虚拟机信息</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
              <tr>
                <td>
                  <div class="cell">Java名称</div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell">{{ server.jvm.name }}</div>
                </td>
                <td>
                  <div class="cell">Java版本</div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell">{{ server.jvm.version }}</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">启动时间</div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell">{{ parseTime(server.jvm.startTime) }}</div>
                </td>
                <td>
                  <div class="cell">运行时长</div>
                </td>
                <td>
                  <div v-if="server.jvm" class="cell">{{ distanceTime(server.jvm.startTime, currentTime) }}</div>
                </td>
              </tr>
              <tr>
                <td colspan="1">
                  <div class="cell">安装路径</div>
                </td>
                <td colspan="3">
                  <div v-if="server.jvm" class="cell">{{ server.jvm.home }}</div>
                </td>
              </tr>
              <tr>
                <td colspan="1">
                  <div class="cell">项目路径</div>
                </td>
                <td colspan="3">
                  <div v-if="server.sys" class="cell">{{ server.sys.userDir }}</div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
      <!-- JVM堆内存 -->
      <el-col v-if="server.jvm" :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Java虚拟机堆内存</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
              <tr>
                <th class="is-leaf">
                  <div class="cell">名称</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">初始内存</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">可用内存</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">已用内存</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">最大内存</div>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(value, key) in server.jvm.heapMemory" :key="key">
                <td>
                  <div class="cell">{{ key }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.init) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.committed) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.used) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.max) }}</div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
      <!-- JVM非堆内存 -->
      <el-col v-if="server.jvm" :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Java虚拟机非堆内存</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
              <tr>
                <th class="is-leaf">
                  <div class="cell">名称</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">初始内存</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">可用内存</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">已用内存</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">最大内存</div>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(value, key) in server.jvm.noHeapMemory" :key="key">
                <td>
                  <div class="cell">{{ key }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.init) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.committed) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.used) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(value.max) }}</div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
      <!-- 服务器信息 -->
      <el-col v-if="server.sys" :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>服务器信息</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
              <tr>
                <td>
                  <div class="cell">服务器名称</div>
                </td>
                <td>
                  <div class="cell">{{ server.sys.computerName }}</div>
                </td>
                <td>
                  <div class="cell">操作系统</div>
                </td>
                <td>
                  <div v-if="server.sys" class="cell">{{ server.sys.osName }}</div>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="cell">服务器IP</div>
                </td>
                <td>
                  <div class="cell">{{ server.sys.computerIp }}</div>
                </td>
                <td>
                  <div class="cell">系统架构</div>
                </td>
                <td>
                  <div class="cell">{{ server.sys.osArch }}</div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
      <!-- 磁盘信息 -->
      <el-col v-if="server.sysFiles" :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>磁盘状态</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
              <tr>
                <th class="is-leaf">
                  <div class="cell">盘符路径</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">文件系统</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">盘符标签</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">总大小</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">可用大小</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">已用大小</div>
                </th>
                <th class="is-leaf">
                  <div class="cell">已用百分比</div>
                </th>
              </tr>
              </thead>
              <tbody v-if="server.sysFiles">
              <tr v-for="(sysFile, index) in server.sysFiles" :key="index">
                <td>
                  <div class="cell">{{ sysFile.dirName }}</div>
                </td>
                <td>
                  <div class="cell">{{ sysFile.sysTypeName }}</div>
                </td>
                <td>
                  <div class="cell">{{ sysFile.label }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(sysFile.total) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(sysFile.free) }}</div>
                </td>
                <td>
                  <div class="cell">{{ formatBytes(sysFile.used) }}</div>
                </td>
                <td>
                  <div class="cell" :class="{'text-danger': sysFile.usage > 80}">{{ sysFile.usage }}%</div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getServer } from '@/api/monitor/server'

export default {
  name: 'Server',
  data() {
    return {
      // 加载层信息
      loading: [],
      // 服务器信息
      server: [],
      // 当前时间
      currentTime: Date.now(),
      // 定时刷新时间任务
      timer: null
    }
  },
  created() {
    this.openLoading()
    this.getList()
  },
  mounted() {
    this.timer = setInterval(() => {
      this.currentTime = Date.now()
    }, 1000)
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  methods: {
    /** 查询服务器信息 */
    getList() {
      getServer().then(res => {
        this.server = res
        this.server.jvm.usedMemory = res.jvm.totalMemory - res.jvm.freeMemory
        this.server.jvm.usage = (this.server.jvm.usedMemory / res.jvm.totalMemory * 100).toFixed(2)
        this.server.mem.used = res.mem.total - res.mem.free
        this.server.mem.usage = (this.server.mem.used / res.mem.total * 100).toFixed(2)
        this.server.cpu.used = (res.cpu.used / res.cpu.total * 100).toFixed(2)
        this.server.cpu.sys = (res.cpu.sys / res.cpu.total * 100).toFixed(2)
        this.server.cpu.free = (res.cpu.free / res.cpu.total * 100).toFixed(2)
        for (let i = 0; i < this.server.sysFiles.length; i++) {
          const sysFile = this.server.sysFiles[i]
          sysFile.used = sysFile.total - sysFile.free
          sysFile.usage = (sysFile.used / sysFile.total * 100).toFixed(2)
        }
        this.loading.close()
      })
    },
    // 打开加载层
    openLoading() {
      this.loading = this.$loading({
        lock: true,
        text: '拼命读取中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    }
  }
}
</script>
