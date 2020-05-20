import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // 清空原生样式

import Element from 'element-ui'
import './styles/element-variables.scss'

import '@/styles/index.scss' // 全局样式
import '@/styles/ruoyi.scss' // ruoyi css

import App from './App'
import store from './store'
import router from './router'

import i18n from './lang' // 国际化
import './icons' // 图标
import './permission' // 权限控制
import './utils/error-log' // 错误日志收集
import { deepClone, formatTime, parseTime } from './utils'
import { isBillId } from './utils/validate' // 深拷贝对象
import { baseURL, type, downloadFile, filterModel, resetForm, distanceTime, sortTree, convertStringToTree, jsonToTreeSelectData, formatBytes, parseTableMeta } from '@/utils/lzpeng-base'
import { parseTreeNode, treeToList, listToTree } from '@/utils/lzpeng-tree'
import Pagination from '@/components/Pagination' // 分页组件

import * as filters from './filters' // 全局过滤器

// 全局方法挂载
Vue.prototype.formatTime = formatTime
Vue.prototype.distanceTime = distanceTime
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.type = type
Vue.prototype.baseURL = baseURL
Vue.prototype.isBillId = isBillId
Vue.prototype.downloadFile = downloadFile
Vue.prototype.filterModel = filterModel
Vue.prototype.sortTree = sortTree
Vue.prototype.deepClone = deepClone
Vue.prototype.parseTreeNode = parseTreeNode
Vue.prototype.treeToList = treeToList
Vue.prototype.listToTree = listToTree
Vue.prototype.convertStringToTree = convertStringToTree
Vue.prototype.jsonToTreeSelectData = jsonToTreeSelectData
Vue.prototype.parseTableMeta = parseTableMeta
Vue.prototype.formatBytes = formatBytes

// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // 设置 element ui 大小
  i18n: (key, value) => i18n.t(key, value)
})

// 注册全局过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})
