import request from '@/utils/request'

/**
 * 后台日志实体的根 url
 * @type {string}
 */
export const baseUrl = '/monitor/serverLog'

/**
 * 查询后台日志,返回数组
 * @param model 查询条件
 */
export function getServerLogList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询后台日志,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getServerLogPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 导出后台日志
 * @param model
 */
export function exportServerLog(fileName) {
  return request({
    url: `${baseUrl}/export/${fileName}`,
    method: 'get',
    responseType: 'blob'
  })
}

