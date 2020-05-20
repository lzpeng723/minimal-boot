import request from '@/utils/request'

/**
 * 选择其他对象跟 url
 * @type {string}
 */
const baseUrl = '/framework/entity'
/**
 * 获取其他对象
 */
export function getTableData(params) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params
  })
}
