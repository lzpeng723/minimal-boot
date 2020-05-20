import request from '@/utils/request'

/**
 * 方法日志实体的根 url
 * @type {string}
 */
const baseUrl = '/monitor/methodLog'
/**
 * 获得方法日志的数据字典
 */
export function getMethodLogDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增方法日志
 * @param model 需要保存的数据
 */
export function insertMethodLog(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除方法日志
 * @param id 要删除的方法日志id
 */
export function deleteMethodLog(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 更新方法日志
 * @param id 要更新的方法日志id
 * @param model 需要更新的数据
 */
export function updateMethodLog(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询方法日志,返回数组
 * @param model 查询条件
 */
export function getMethodLogList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询方法日志,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getMethodLogPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作方法日志
 * @param model
 */
export function batchOperation(model) {
  return request({
    url: `${baseUrl}/batch`,
    method: 'post',
    data: model
  })
}

