
// 存储 ComponentView 的父组件
const state = {
  component: null
}

/**
 * 设置 ComponentView 的父组件
 * @type {{SET_ROUTES: mutations.SET_ROUTES}}
 */
const mutations = {
  SET_COMPONENT: (state, component) => {
    state.component = component
  }
}

/**
 * 设置 ComponentView 的父组件
 * @type {{generateRoutes({commit: *}, *=): *}}
 */
const actions = {
  setComponent({ commit }, component) {
    return new Promise((resolve) => {
      commit('SET_COMPONENT', component)
      resolve(component)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
