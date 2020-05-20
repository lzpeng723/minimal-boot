/**
 * 通过树形数据把每个节点的父子关系存起来
 * @param treeData 后台返回的树形数据
 * @param re 初始对象默认为空对象
 * @returns {*}
 */
export function parseTreeNode(treeData, re = {}) {
  return treeData.reduce((result, node) => {
    const parentId = node.id // 当前节点id
    result[parentId] = result[parentId] || {} // 初始化当前节点id的键
    // 如果有子节点
    if (node.children && node.children.length > 0) {
      const childIds = node.children.map(child => child.id) // 子节点id数组
      result[parentId].childIds = childIds // 将子节点id数组设置为当前节点的子节点
      result[parentId].childrenNum = childIds.length // 设置子节点数量
      for (let i = 0; i < childIds.length; i++) { // 遍历子节点
        const childId = childIds[i] // 获得当前子节点
        result[childId] = result[childId] || {} // 初始化子节点id的键
        result[childId].parentId = parentId // 将子节点id的父节点设置为当前节点
      }
      return parseTreeNode(node.children, result) // 递归找子节点的子节点
    } else {
      // 如果没有子节点
      result[parentId].childrenNum = 0
    }
    return result
  }, re)
}

/**
 * 树状数据转 数组
 * @param treeData
 * @param re
 * @returns {*}
 */
export function treeToList(treeData, re = []) {
  return treeData.reduce((result, node) => {
    // 如果有子节点
    if (node.children && node.children.length > 0) {
      const tempArray = treeToList(node.children, result)
      result.concat(tempArray)
      delete node.children
    }
    result.push(node)
    return result
  }, re)
}

/**
 * 数组转 树状数据
 * @param listData
 * @param re
 * @returns {*}
 */
export function listToTree(listData, re = []) {
  return listData.reduce((result, node) => {
    if (node.parentId) {
      // 不是根节点
      const parent = listData.find(p => node.parentId === p.id)
      parent.children = parent.children || []
      parent.children.push(node)
    } else {
      // 是根节点
      result.push(node)
    }
    return result
  }, re)
}
