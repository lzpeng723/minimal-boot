import { constantRoutes } from '@/router'
import { sortTree } from '@/utils/lzpeng-base'
import Layout from '@/layout'
import { getRouters } from '@/api/sys/menu'
// 获取所有用户自定义组件
import { allComponents } from '@/utils/component-scan'
allComponents['@/layout/index'] = Layout
const pageNotFound = () => import('@/views/error-page/Dev')
/**
 * 有后端返回的菜单数据生成vue路由
 * @param data
 */
function buildRoutes(data) {
  for (let i = 0; i < data.length; i++) {
    const router = data[i]
    const component = router.component
    if (component) {
      // 如果定义了该组件,则用该自定义组件,否则用404
      router.component = allComponents[component] || pageNotFound
    }
    if (router.children && router.children.length > 0) {
      router.children = buildRoutes(router.children)
    }
  }
  return data
}

const state = {
  routes: [],
  addRoutes: []
}

/**
 * 设置路由 为 constantRoutes 拼接上 后台获取的路由 再把404放最后
 * @type {{SET_ROUTES: mutations.SET_ROUTES}}
 */
const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

/**
 * 生成路由方法
 * @type {{generateRoutes({commit: *}, *=): *}}
 */
const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise((resolve, reject) => {
      getRouters().then(data => {
        // 动态构建 vue 组件
        const accessedRoutes = buildRoutes(data)
        accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      }).catch(err => {
        reject(err)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
