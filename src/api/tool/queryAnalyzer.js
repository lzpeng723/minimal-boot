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
