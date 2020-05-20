import request from '@/utils/request'

/**
 * 查询分析器的根 url
 * @type {string}
 */
const baseUrl = '/tool/queryAnalyzer'

/**
 * 执行 SQL
 */
export function executeSQL(sql) {
  return request({
    url: `${baseUrl}/sql`,
    method: 'post',
    data: { sql }
  })
}

/**
 * 执行 JPQL
 */
export function executeJPQL(jpql) {
  return request({
    url: `${baseUrl}/jpql`,
    method: 'post',
    data: { jpql }
  })
}
/**
 * 执行 Rhino 脚本
 */
export function executeRhino(rhino) {
  return request({
    url: `${baseUrl}/rhino`,
    method: 'post',
    data: { rhino }
  })
}
/**
 * 执行 Nashorn 脚本
 */
export function executeNashorn(nashorn) {
  return request({
    url: `${baseUrl}/nashorn`,
    method: 'post',
    data: { nashorn }
  })
}

/**
 * id 查表名
 */
export function findTableById(id) {
  return request({
    url: `${baseUrl}/id`,
    method: 'get',
    params: { id }
  })
}
/**
 * 实体 查表名
 */
export function findTableByEntity(entityName) {
  return request({
    url: `${baseUrl}/entity`,
    method: 'get',
    params: { entityName }
  })
}
/**
 * 显示所有表
 */
export function findTables() {
  return request({
    url: `${baseUrl}/tables`,
    method: 'get'
  })
}
/**
 * 显示所有实体
 */
export function findEntities() {
  return request({
    url: `${baseUrl}/entities`,
    method: 'get'
  })
}
