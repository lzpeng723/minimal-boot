import request from '@/utils/request'

/**
 * 菜单实体的根 url
 * @type {string}
 */
const baseUrl = '/sys/menu'

/**
 * 获取所有路由菜单
 */
export function getAllRouters() {
  return request({
    url: `${baseUrl}/allRouters`,
    method: 'get'
  })
}
/**
 * 获取用户路由菜单
 */
export function getRouters() {
  return request({
    url: `${baseUrl}/routers`,
    method: 'get'
  })
}

/**
 * 获得菜单的数据字典
 */
export function getMenuDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增菜单
 * @param model 需要保存的数据
 */
export function insertMenu(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除菜单
 * @param id 要删除的菜单id
 */
export function deleteMenu(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 更新菜单
 * @param id 要更新的菜单id
 * @param model 需要更新的数据
 */
export function updateMenu(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询菜单,返回数组
 * @param model 查询条件
 */
export function getMenuList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询菜单,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getMenuPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作菜单
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
 * 查询菜单, 返回树形结构
 * @param model 查询条件
 */
export function getMenuTree(model) {
  return request({
    url: `${baseUrl}/tree`,
    method: 'get',
    params: model
  })
}
