import request from '@/utils/request'

/**
 * 用户实体的根 url
 * @type {string}
 */
const baseUrl = '/sys/user'
/**
 * 获得用户的数据字典
 */
export function getUserDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增用户
 * @param model 需要保存的数据
 */
export function insertUser(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除用户
 * @param id 要删除的用户id
 */
export function deleteUser(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 更新用户
 * @param id 要更新的用户id
 * @param model 需要更新的数据
 */
export function updateUser(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询用户,返回数组
 * @param model 查询条件
 */
export function getUserList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询用户,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getUserPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作用户
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
 * 分配角色
 * @param userId 用户id
 * @param model 角色 id 数组
 */
export function setRoles(userId, model) {
  return request({
    url: `${baseUrl}/setRoles/${userId}`,
    method: 'post',
    data: model
  })
}

