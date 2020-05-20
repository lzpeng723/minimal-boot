<!-- @author lzpeng -->
<template>
  <div class="component-body">
    <!--组件搜索框-->
    <el-input
      v-model="name"
      style="position: relative;"
      clearable
      placeholder="请输入组件名称"
      @clear="filterComponents"
      @input.native="filterComponents"
    >
      <i slot="suffix" class="el-icon-search el-input__icon" />
    </el-input>
    <!--显示所有组件列表-->
    <!--TODO 考虑组件列表使用 el-tree 显示-->
    <div class="component-list">
      <div
        v-for="(component, componentName, index) in components"
        :key="index"
        style="position: relative;"
        @click="selectedComponent(componentName, component)">
        <svg-icon icon-class="component" />
        <span>{{ componentName }}</span>
        <span class="view" @click="showComponent(componentName)">预览</span>
      </div>
    </div>
    <!--遮罩层-->
    <div v-if="currentComponent != null" class="mask" />
    <!--预览组件-->
    <el-card v-if="currentComponent != null" class="box-card component-view" shadow="always">
      <div slot="header" class="clearfix">
        <span>{{ currentComponent }}</span>
        <el-button class="close" type="text" @click="currentComponent = null">关闭</el-button>
      </div>
      <!--真正要预览的组件-->
      <keep-alive>
        <component :is="currentComponent" />
      </keep-alive>
    </el-card>
  </div>
</template>

<script>
import { scanAllComponents, scanUserComponents, scanCommonComponents } from './component-scan'

/**
 * 组件查看器
 * 不可被其他组件使用
 */
export default {
  name: 'ComponentView',
  components: scanAllComponents(),
  props: {
    type: {
      type: String,
      default: 'user'
    }
  },
  data() {
    return {
      name: '',
      components: null,
      allComponents: null,
      currentComponent: null
    }
  },
  created() {
    const excludePath = []
    let p = this
    while (p) {
      p.$options.__source && excludePath.push(p.$options.__source)
      p = p.$parent
    }
    switch (this.type) {
      case 'common':
        this.allComponents = scanCommonComponents(excludePath)
        break
      case 'all':
        this.allComponents = scanAllComponents(excludePath)
        break
      default :
        this.allComponents = scanUserComponents(excludePath)
    }
    this.components = this.allComponents
  },
  methods: {
    /**
     * 搜索 过滤组件
     */
    filterComponents() {
      if (this.name) {
        // 遍历所有组件的key
        this.components = Object.keys(this.allComponents).reduce((result, key) => {
          // 如果输入的字符在key中出现 则返回此组件
          if (key.toLowerCase().includes(this.name.toLowerCase())) {
            result[key] = this.allComponents[key]
          }
          return result
        }, {})
      } else {
        this.components = this.allComponents
      }
    },
    /**
     * 选择组件 交给父组件处理
     * @param componentName
     * @param component
     */
    selectedComponent(componentName, component) {
      // 将组件名称中的 - 替换为 / 表示组件路径
      const componentPath = componentName.replace(/-/g, '/')
      this.$emit('selected', componentPath, component)
      document.body.click()
    },
    // 重置组件
    reset() {
      this.name = ''
      this.components = this.allComponents
    },
    // 预览组件
    showComponent(componentName) {
      this.currentComponent = componentName
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .component-body {
    width: 100%;
    padding: 10px;
    .component-list {
      height: 200px;
      overflow-y: scroll;
      div {
        height: 30px;
        line-height: 30px;
        margin-bottom: -5px;
        cursor: pointer;
        width: 50%;
        float: left;
      }
      span {
        display: inline-block;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
      }
      .view {
        position: absolute;
        right: 15px;
        color: #1890ff;
      }
    }
    // 遮罩层 模仿 el-dialog 遮罩样式写的,z-index必须大于 el-dialog 的 z-index
    .mask {
      line-height: 1.15;
      -webkit-font-smoothing: antialiased;
      text-rendering: optimizeLegibility;
      background-color: #303133;
      box-sizing: inherit;
      position: fixed;
      opacity: 0.6;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      overflow: auto;
      margin: 0;
      z-index: 3000;
    }
    // 需要考虑是否固定 header 的情况
    .component-view {
       position: absolute;
       top: 5%;
       left: 5%;
       width: 90%;
       height: 90%;
       overflow: auto;
       z-index: 3001;
       box-shadow: 0px 0px 15px #888888;
       .close {
         float: right;
         padding: 3px 0;
       }
     }
  }
</style>
