<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="model" :inline="true" label-width="168px">
      <el-form-item label="类名称或Bean名称" prop="className">
        <el-input
          v-model="model.className"
          placeholder="请输入类名称或Bean名称"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 500px;"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="model = {}">重置</el-button>
      </el-form-item>
    </el-form>
    <el-card v-if="classInfo.classLoaderInfo">
      <div slot="header">
        <span>类名称: {{ classInfo.clazz }}</span>
      </div>
      <el-row v-if="classInfo.resourceUrl">
        类路径:
        <a :href="getClassPathUrl(decodeURI(classInfo.resourceUrl))">
          <!--marquee-->
          <el-button type="text">{{ decodeURI(classInfo.resourceUrl) }}</el-button>
        </a>
      </el-row>
      <el-row v-if="classInfo.classLoaderInfo">
        类加载器: <span v-text="classInfo.classLoaderInfo.name" />
      </el-row>
    </el-card>
    <el-card v-else>
      <div slot="header">
        <span>当前类名称或Bean名称: {{ model.className }}</span>
      </div>
      <el-row>
        类路径: 未找到此类或Bean,请输入类名称或Bean名称
      </el-row>
    </el-card>
    <el-divider>以下为所有类加载器信息</el-divider>
    <el-card v-for="item in classLoaderList" :key="item.name">
      <div slot="header">
        <span>{{ item.name }}</span>
      </div>
      <ul class="todo-list">
        <li v-for="(cp, index) in item.classPath" :key="index">
          <a :href="getClassPathUrl(decodeURI(cp))">
            <el-button type="text">{{ decodeURI(cp) }}</el-button>
          </a>
        </li>
      </ul>
    </el-card>
  </div>
</template>

<script>
import { Base64 } from 'js-base64'
import { getClassInfo } from '@/api/tool/classLoader' // 代码生成模板api
export default {
  name: 'ClassLoaderList',
  data() {
    return {
      classInfo: {},
      classLoaderList: [],
      model: {
        className: 'com.lzpeng.MainApplication'
      }
    }
  },
  created() {
    this.handleQuery()
  },
  methods: {
    handleQuery() {
      let className = this.model.className ? this.model.className : 'null'
      className = className === '' ? 'null' : className
      getClassInfo(className).then(res => {
        this.classInfo = res
        this.classLoaderList = res.classLoaderInfoList
      })
    },
    getClassPathUrl(cp) {
      // return Base64.encode(cp)
      return `${this.baseURL}/tool/classLoader/download/${Base64.encode(cp)}`
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
