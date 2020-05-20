import request from '@/utils/request'

/**
 * 类加载信息的根 url
 * @type {string}
 */
const baseUrl = '/tool/classLoader'

/**
 * 查询所有类加载器信息
 */
export function getClassLoaderList() {
  return request({
    url: `${baseUrl}`,
    method: 'get'
  })
}

/**
 * 查询指定类信息
 * @param className 查询指定类信息
 */
export function getClassInfo(className) {
  return request({
    url: `${baseUrl}/${className}`,
    method: 'get'
  })
}

