import request from '@/utils/request'

/**
 * ${chineseClassName}实体的根 url
 * @type {string}
 */
const baseUrl = '/${moduleName}/${simpleClassName?uncap_first}'
/**
 * 获得${chineseClassName}的数据字典
 */
export function get${simpleClassName}Dict() {
  return request({
    url: `${r'${baseUrl}'}/dict`,
    method: 'get'
  })
}

/**
 * 新增${chineseClassName}
 * @param model 需要保存的数据
 */
export function insert${simpleClassName}(model) {
  return request({
    url: `${r'${baseUrl}'}`,
    method: 'post',
    data: model
  })
}

/**
 * 删除${chineseClassName}
 * @param id 要删除的${chineseClassName}id
 */
export function delete${simpleClassName}(id) {
  return request({
    url: `${r'${baseUrl}'}/${r'${id}'}`,
    method: 'delete'
  })
}

/**
* 根据id查询${chineseClassName}
* @param id 要查询的${chineseClassName}id
*/

export function get${simpleClassName}ById(id) {
  return request({
    url: `${r'${baseUrl}'}/${r'${id}'}`,
    method: 'get'
  })
}

/**
 * 更新${chineseClassName}
 * @param id 要更新的${chineseClassName}id
 * @param model 需要更新的数据
 */
export function update${simpleClassName}(id, model) {
  return request({
    url: `${r'${baseUrl}'}/${r'${id}'}`,
    method: 'put',
    data: model
  })
}

/**
 * 查询${chineseClassName},返回数组
 * @param model 查询条件
 */
export function get${simpleClassName}List(model) {
  return request({
    url: `${r'${baseUrl}'}`,
    method: 'get',
    params: model
  })
}

/**
 * 分页查询${chineseClassName},返回数组
 * @param page 第几页
 * @param size 没有多少条数据
 * @param model 查询条件
 */
export function get${simpleClassName}Page(page, size, model) {
  return request({
    url: `${r'${baseUrl}'}/${r'${page}'}/${r'${size}'}`,
    method: 'get',
    params: model
  })
}

/**
 * 批量操作${chineseClassName}
 * @param model
 */
export function batchOperation(model) {
  return request({
    url: `${r'${baseUrl}'}/batch`,
    method: 'post',
    data: model
  })
}
<#if entityType=="Tree">

/**
* 查询${chineseClassName}, 返回树形结构
* @param model 查询条件
*/
export function get${simpleClassName}Tree(model) {
  return request({
    url: `${r'${baseUrl}'}/tree`,
    method: 'get',
    params: model
  })
}
</#if>
<#if entityType=="LeftTreeRightTable">

/**
* 获取${chineseClassName}的左树数据
*/
export function leftTreeData() {
  return request({
    url: `${r'${baseUrl}'}/leftTree`,
    method: 'get'
  })
}
</#if>