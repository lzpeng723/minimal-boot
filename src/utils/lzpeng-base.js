import FileSaver from 'file-saver'
/**
 * 通用js方法封装处理
 * Copyright (c) 2020 lzpeng
 */

export const baseURL = process.env.VUE_APP_BASE_API

/**
 * 计算时间差
 * @param firstTime 第一个时间
 * @param secondTime 第二个时间
 * @returns {string}
 */
export function distanceTime(firstTime, secondTime = Date.now()) {
  if (('' + firstTime).length === 10) {
    firstTime = parseInt(firstTime) * 1000
  } else {
    firstTime = +firstTime
  }
  const d = new Date(firstTime)

  const diff = (secondTime - d) / 1000

  if (diff < 60) {
    return `${Math.floor(diff)} 秒`
  } else if (diff < 3600) {
    const minute = diff / 60
    const second = diff % 60
    return `${Math.floor(minute)} 分 ${Math.floor(second)} 秒`
  } else if (diff < 3600 * 24) {
    const hours = diff / 3600
    const minute = (diff % 3600) / 60
    const second = diff % 60
    return `${Math.floor(hours)} 小时 ${Math.floor(minute)} 分 ${Math.floor(second)} 秒`
  } else if (diff < 3600 * 24 * 2) {
    const days = diff / (3600 * 24)
    const hours = diff % (3600 * 24) / 3600
    const minute = (diff % 3600) / 60
    const second = diff % 60
    return `${Math.floor(days)} 天 ${Math.floor(hours)} 小时 ${Math.floor(minute)} 分 ${Math.floor(second)} 秒`
  }
}

// 表单重置
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields()
  }
}

// 获取对象类型
export function type(obj) {
  // Object.prototype.toString.call 判断对象是什么类型
  // Object.prototype.toString.call(true) Boolean
  // Object.prototype.toString.call(blob) Blob
  // Object.prototype.toString.call(1) Number
  // Object.prototype.toString.call('') String
  // Object.prototype.toString.call({}) Object
  // Object.prototype.toString.call([]) Array
  // Object.prototype.toString.call(/\d/) RegExp
  // Object.prototype.toString.call(async function(){}) AsyncFunction
  // Object.prototype.toString.call(function*(){}) GeneratorFunction
  // Object.prototype.toString.call(function(){}) Function
  return Object.prototype.toString.call(obj).slice(8, -1)
}

/**
 * 递归排序树形数组
 * @param array 树形数组
 * @param childrenKey 子节点的 key
 * @param func 排序方法
 * @returns {*} 排序好之后的数组
 */
export function sortTree(array, childrenKey, func) {
  for (let i = 0; i < array.length; i++) {
    const item = array[i]
    if (item[childrenKey] && item[childrenKey].length > 1) {
      item[childrenKey] = sortTree(item[childrenKey], childrenKey, func)
    }
  }
  array = array.sort(func)
  return array
}

/**
 * 将扁平化对象转为树形对象
 * @param data 需要转化的扁平化数据
 * @param split data中的 key 用什么进行分割 默认为 .
 * @return {} convert {'a.a.a':1,'a.a.b':2,'a.b.a':3,'a.b.b':4} to {a:{a:{a:1,b:2},b:{a:3,b:4}}}
 */
export function convertStringToTree(data, split = '.', needValue = false) {
  // 数组情况
  if (type(data) === 'Array') {
    return data.reduce((result, curKey) => {
      curKey.split(split).reduce((re, cur, curIndex, keys) => {
        re[cur] = re[cur] || {} // 如果该key不存在则赋值为空对象
        if (keys.length - 1 === curIndex) {
          re[cur] = curKey // 如果遍历到最后将原始key 赋给末级的 key
        }
        return re[cur]
      }, result)
      return result
    }, {})
  }
  // 对象情况
  if (type(data) === 'Object') {
    return Object.keys(data).reduce((result, curKey) => {
      curKey.split(split).reduce((re, cur, curIndex, keys) => {
        re[cur] = re[cur] || {} // 如果该key不存在则赋值为空对象
        if (keys.length - 1 === curIndex) {
          re[cur] = needValue ? data[curKey] : curKey // 如果遍历到最后将 value 或者原始key 赋给末级的 key
        }
        return re[cur]
      }, result)
      return result
    }, {})
  }
}

/**
 * json 转成 el-Tree可识别的数组
 * @param json
 * @param label
 * @param children
 * @returns {Array}
 */
export function jsonToTreeSelectData(json, label = 'label', children = 'children', prefix = '') {
  var result = []
  if (type(json) === 'Object') {
    for (const key in json) {
      result.push({
        label: key,
        id: prefix + key,
        children: jsonToTreeSelectData(json[key], label, children, prefix + key + '/')
      })
    }
  }
  return result
}

/**
 * 解析数据字典将每列的详细信息{fieldName: columnInfo}和数据字典{fieldName: dictValues}缓存到vue实例
 * @param tableInfo 后台返回的当前单据元数据信息
 */
export function parseTableMeta(tableInfo) {
  return tableInfo.columns.reduce((result, cur) => {
    result.columnInfo[cur.fieldName] = cur // 每列的详细信息
    if (cur.dictValues && cur.dictValues.length > 0) {
      result.dictValues[cur.fieldName] = cur.dictValues // 每列的数据字典
    }
    return result
  }, { columnInfo: {}, dictValues: {}})
}

/**
 * 格式化字节单位
 * @param bytes
 * @returns {string}
 */
export function formatBytes(bytes) {
  if (bytes === -1) {
    return '----'
  }
  const units = ['byte', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB']
  for (let i = 0; i < units.length; i++) {
    if (bytes < 1024) {
      return `${bytes.toFixed(2)} ${units[i]}`
    }
    bytes = bytes / 1024
  }
  bytes = bytes * 1024
  return `${bytes.toFixed(2)} ${units[units.length - 1]}`
}
/**
 * 将model中的数组和对象过滤掉,不传递给后台
 * @param model 对象所有信息
 * @return 只留下 String, Boolean和 Number
 */
export function filterModel(model) {
  return Object.keys(model).reduce((result, key) => {
    if (type(model[key]) === 'String' ||
      type(model[key]) === 'Number' ||
      type(model[key]) === 'Boolean') {
      result[key] = model[key]
    }
    return result
  }, {})
}

/**
 * 下载文件
 * @param data 文件内容
 * @param fileName 文件名
 * @return
 * @see https://www.jianshu.com/p/a377af113338?tdsourcetag=s_pcqq_aiomsg
 */
export function downloadFile(data, fileName) {
  const blob = type(data) === 'Blob' ? data : new Blob([data])
  FileSaver.saveAs(blob, fileName)
}
