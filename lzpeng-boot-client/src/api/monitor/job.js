import request from '@/utils/request'

/**
 * 定时任务实体的根 url
 * @type {string}
 */
const baseUrl = '/monitor/job'
/**
 * 获得定时任务的数据字典
 */
export function getJobDict() {
  return request({
    url: `${baseUrl}/dict`,
    method: 'get'
  })
}

/**
 * 新增定时任务
 * @param model 需要保存的数据
 */
export function insertJob(model) {
  return request({
    url: `${baseUrl}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除定时任务
 * @param id 要删除的定时任务id
 */
export function deleteJob(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'delete'
  })
}

/**
 * 根据id查询定时任务
 * @param id 要查询的定时任务id
 */
export function getJobById(id) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'get'
  })
}

/**
 * 更新定时任务
 * @param id 要更新的定时任务id
 * @param model 需要更新的数据
 */
export function updateJob(id, model) {
  return request({
    url: `${baseUrl}/${id}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询定时任务,返回数组
 * @param model 查询条件
 */
export function getJobList(model) {
  return request({
    url: `${baseUrl}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询定时任务,返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function getJobPage(page, size, model) {
  return request({
    url: `${baseUrl}/${page}/${size}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作定时任务
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
 * 执行一次定时任务
 * @param id 定时任务id
 */
export function executeJob(id) {
  return request({
    url: `${baseUrl}/execute/${id}`,
    method: 'post'
  })
}
/**
 * 启动定时任务
 * @param id 定时任务id
 */
export function startJob(id) {
  return request({
    url: `${baseUrl}/start/${id}`,
    method: 'post'
  })
}
/**
 * 暂停定时任务
 * @param id 定时任务id
 */
export function pauseJob(id) {
  return request({
    url: `${baseUrl}/pause/${id}`,
    method: 'post'
  })
}
/**
 * 停止定时任务
 * @param id 定时任务id
 */
export function stopJob(id) {
  return request({
    url: `${baseUrl}/stop/${id}`,
    method: 'post'
  })
}
/**
 * 恢复定时任务
 * @param id 定时任务id
 */
export function resumeJob(id) {
  return request({
    url: `${baseUrl}/resume/${id}`,
    method: 'post'
  })
}
