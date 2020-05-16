import request from '@/utils/request'

/**
 * 通知实体的根 url
 * @type {string}
 */
const baseUrl = '/sys/notice'

/**
 * 获得通知的数据字典
 */
export function getNoticeDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增通知
 * @param model 需要保存的数据
 */
export function insertNotice(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除通知
 * @param id 要删除的通知id
 */
export function deleteNotice(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 根据id查询通知
 * @param id 要查询的通知id
 */

export function getNoticeById(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'get'
  })
}

/**
 * 更新通知
 * @param id 要更新的通知id
 * @param model 需要更新的数据
 */
export function updateNotice(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询通知,返回数组
 * @param model 查询条件
 */
export function getNoticeList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询通知,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getNoticePage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作通知
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
 * 发送通知
 * @param model
 */
export function sendNotice(noticeId, data) {
  return request({
    url: `${baseUrl}/sendNotice/${noticeId}`,
    method: 'post',
    data: data
  })
}

/**
 * 接收通知
 */
export function receiveNotice() {
  return request({
    url: `${baseUrl}/receiveNotice`,
    method: 'get'
  })
}
