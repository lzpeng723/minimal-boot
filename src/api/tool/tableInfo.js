import request from '@/utils/request'

/**
 * 表信息实体的根 url
 * @type {string}
 */
const baseUrl = '/tool/tableInfo'
/**
 * 获得表信息的数据字典
 */
export function getTableInfoDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增表信息
 * @param model 需要保存的数据
 */
export function insertTableInfo(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除表信息
 * @param id 要删除的表信息id
 */
export function deleteTableInfo(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
* 根据id查询表信息
* @param id 要查询的表信息id
*/
export function getTableInfoById(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'get'
  })
}

/**
 * 更新表信息
 * @param id 要更新的表信息id
 * @param model 需要更新的数据
 */
export function updateTableInfo(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询表信息,返回数组
 * @param model 查询条件
 */
export function getTableInfoList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询表信息,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getTableInfoPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作表信息
 * @param model
 */
export function batchOperation(model) {
  return request({
    url: `${baseUrl}/batch`,
    method: 'post',
    data: model
  })
}

