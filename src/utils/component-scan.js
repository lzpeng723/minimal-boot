/**
 * vue 组件扫描
 * Copyright (c) 2019 lzpeng
 */

// 相对路径
const relativePrefix = './'
// 所有组件路径后缀
const suffix = '.vue'

/**
 * 扫描所有组件的路径数组
 */
function scanAllComponentPath() {
  const contexts = require.context('@/', true, /\.vue$/)
  return contexts.keys().reduce((result, key) => {
    // 相对 '@/views/' 路径转为相对项目路径 ./src/views/...
    const relativeProjectPath = contexts.resolve(key)
    // 去除相对项目路径前的 ./ 转为 src/views/...
    const path = relativeProjectPath.slice(relativePrefix.length)
    // 添加至返回结果
    result.push(path)
    return result
  }, [])
}

/**
 * 扫描组件
 * @param contexts
 * @param excludePath
 */
function scanComponents(contexts, excludePath = []) {
  return contexts.keys().reduce((result, key) => {
    // 相对 '@/views/' 路径转为相对项目路径 ./src/views/...
    const relativeProjectPath = contexts.resolve(key)
    // 去除相对项目路径前的 ./ 转为 src/views/...
    const path = relativeProjectPath.slice(relativePrefix.length)
    // 如果排除路径中包含此路径,则不扫描其组件
    if (excludePath.indexOf(path) !== -1) {
      return
    }
    // 获得vue组件实例
    const component = contexts(key).default || contexts(key)
    if (component/* && component.name*/) {
      // 数据库中存储的 key 为 src/views/...
      const k = path.slice(0, -suffix.length).replace('src', '@')
      result[k] = component
    }
    return result
  }, {})
}

/**
 * 扫描 用户自定义组件
 */
function scanUserComponents(excludePath = []) {
  const contexts = require.context('@/views/', true, /\.vue$/)
  return scanComponents(contexts)
}

/**
 * 扫描 公共组件
 */
function scanCommonComponents() {
  const contexts = require.context('@/components/', true, /\.vue$/)
  return scanComponents(contexts)
}

/**
 * 扫描 所有组件
 */
function scanAllComponents() {
  const contexts = require.context('@/', true, /\.vue$/)
  return scanComponents(contexts)
}

export const userComponents = scanUserComponents()
export const commonComponents = scanCommonComponents()
export const allComponents = scanAllComponents()
export const allComponentPath = scanAllComponentPath()
