import request from '@/utils/request'

/**
 * 请求日志实体的根 url
 * @type {string}
 */
const baseUrl = '/monitor/requestLog'
/**
 * 获得请求日志的数据字典
 */
export function getRequestLogDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增请求日志
 * @param model 需要保存的数据
 */
export function insertRequestLog(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除请求日志
 * @param id 要删除的请求日志id
 */
export function deleteRequestLog(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 更新请求日志
 * @param id 要更新的请求日志id
 * @param model 需要更新的数据
 */
export function updateRequestLog(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询请求日志,返回数组
 * @param model 查询条件
 */
export function getRequestLogList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询请求日志,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getRequestLogPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作请求日志
 * @param model
 */
export function batchOperation(model) {
  return request({
    url: `${baseUrl}/batch`,
    method: 'post',
    data: model
  })
}

