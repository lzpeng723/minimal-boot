import request from '@/utils/request'

/**
 * 部门实体的根 url
 * @type {string}
 */
const baseUrl = '/sys/department'
/**
 * 获得部门的数据字典
 */
export function getDepartmentDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增部门
 * @param model 需要保存的数据
 */
export function insertDepartment(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除部门
 * @param id 要删除的部门id
 */
export function deleteDepartment(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 更新部门
 * @param id 要更新的部门id
 * @param model 需要更新的数据
 */
export function updateDepartment(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询部门,返回数组
 * @param model 查询条件
 */
export function getDepartmentList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询部门,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getDepartmentPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作部门
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
 * 查询部门, 返回树形结构
 * @param model 查询条件
 */
export function getDepartmentTree(model) {
  return request({
    url: `${baseUrl}/tree`,
    method: 'get',
    params: model
  })
}
