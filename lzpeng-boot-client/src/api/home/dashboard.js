import request from '@/utils/request'

/**
 * 首页的根 url
 * @type {string}
 */
const baseUrl = '/home/dashboard'

/**
 * 首页通知待办等的数量
 */
export function count() {
  return request({
    url: `${baseUrl}/count`,
    method: 'get'
  })
}

/**
 * 首页通知列表
 */
export function noticeList() {
  return request({
    url: `${baseUrl}/notice`,
    method: 'get'
  })
}
