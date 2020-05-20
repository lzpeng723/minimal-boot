import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getBasicClient, getToken } from '@/utils/auth'
import qs from 'qs'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000, // 请求超时时间 10s
  paramsSerializer: params => {
    // qs https://www.cnblogs.com/small-coder/p/9115972.html
    for (const key in params) {
      if (params[key] === '') {
        delete params[key]
      }
    }
    return qs.stringify(params, { allowDots: true, arrayFormat: 'repeat' })
  }
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在请求前做一些事情
    const token = getToken()
    if (token) {
      // 向请求头中加入 access_token
      config.headers['Authorization'] = `Bearer ${JSON.parse(token).access_token}`
    } else {
      // 未登录
      // 设置 clientId clientSecret
      config.headers['Authorization'] = `Basic ${getBasicClient()}`
    }
    return config
  },
  error => {
    // 请求失败时做一些事情
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if (res.success) {
      return res.data
    }
    if (res.access_token) {
      return res
    }
    // if the custom code is not 10200, it is judged as an error.
    // response 中包含 access_token 说明登录成功
    if (res.code && res.code !== 10200 && res.code !== 10401) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    const res = error.response.data
    console.log({ err: res }) // 打印错误信息
    const msg = res && res.data && res.data.msg || error.message
    if (res.data && res.data.needReLogin) {
      // to re-login
      MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
    } if (res.code && res.code === 10403) {
      Message({
        message: res.message || '权限不足',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.message || '权限不足'))
    } else {
      Message({
        message: msg,
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
