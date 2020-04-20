import request from '@/utils/request'

/**
 * 岗位实体的根 url
 * @type {string}
 */
const baseUrl = '/sys/position'
/**
 * 获得岗位的数据字典
 */
export function getPositionDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增岗位
 * @param model 需要保存的数据
 */
export function insertPosition(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除岗位
 * @param id 要删除的岗位id
 */
export function deletePosition(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 更新岗位
 * @param id 要更新的岗位id
 * @param model 需要更新的数据
 */
export function updatePosition(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询岗位,返回数组
 * @param model 查询条件
 */
export function getPositionList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询岗位,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getPositionPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作岗位
 * @param model
 */
export function batchOperation(model) {
  return request({
    url: `${baseUrl}/batch`,
    method: 'post',
    data: model
  })
}

