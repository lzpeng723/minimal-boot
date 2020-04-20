import request from '@/utils/request'

/**
 * 角色实体的根 url
 * @type {string}
 */
const baseUrl = '/sys/role'
/**
 * 获得角色的数据字典
 */
export function getRoleDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增角色
 * @param model 需要保存的数据
 */
export function insertRole(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除角色
 * @param id 要删除的角色id
 */
export function deleteRole(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 更新角色
 * @param id 要更新的角色id
 * @param model 需要更新的数据
 */
export function updateRole(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询角色,返回数组
 * @param model 查询条件
 */
export function getRoleList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询角色,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getRolePage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作角色
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
 * 分配权限
 * @param roleId 角色id
 * @param model 权限 id 数组
 */
export function setPermissions(roleId, model) {
  return request({
    url: `${baseUrl}/setPermissions/${roleId}`,
    method: 'post',
    data: model
  })
}
/**
 * 分配权限
 * @param roleId 角色id
 * @param model 权限 id 数组
 */
export function noPermissions(roleId) {
  return request({
    url: `${baseUrl}/noPermissions/${roleId}`,
    method: 'get'
  })
}

