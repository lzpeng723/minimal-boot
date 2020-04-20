import request from '@/utils/request'

/**
 * 代码生成模板实体的根 url
 * @type {string}
 */
const baseUrl = '/tool/gen'
/**
 * 获得代码生成模板的数据字典
 */
export function getGenDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增代码生成模板
 * @param model 需要保存的数据
 */
export function insertGen(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除代码生成模板
 * @param id 要删除的代码生成模板id
 */
export function deleteGen(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
* 根据id查询代码生成模板
* @param id 要查询的代码生成模板id
*/

export function getGenById(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'get'
  })
}

/**
 * 更新代码生成模板
 * @param id 要更新的代码生成模板id
 * @param model 需要更新的数据
 */
export function updateGen(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询代码生成模板,返回数组
 * @param model 查询条件
 */
export function getGenList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询代码生成模板,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getGenPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作代码生成模板
 * @param model
 */
export function batchOperation(model) {
  return request({
    url: `${baseUrl}/batch`,
    method: 'post',
    data: model
  })
}

/**
 * 查询实体类的代码生成情况
 * @param clazz 实体类名称
 */
export function getGenInfo(className) {
  return request({
    url: `${baseUrl}/genInfo`,
    method: 'get',
    params: { className }
  })
}

/**
 * 预览实体类生成的代码
 * @param clazz 实体类名称
 */
export function previewGenCode(genId, className) {
  return request({
    url: `${baseUrl}/preview/${genId}`,
    method: 'get',
    params: { className }
  })
}

