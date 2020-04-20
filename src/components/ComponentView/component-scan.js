/**
 * vue 组件扫描
 * Copyright (c) 2020 lzpeng
 */
// 相对路径
const relativePrefix = './'
// 所有组件路径后缀
const suffix = '.vue'

/**
 * 扫描组件
 * @param contexts
 * @param excludePath
 */
function scanComponents(contexts, excludePath = []) {
  const components = {}
  contexts.keys().forEach(key => {
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
      // 将组件 path 中的 '/' 替换成 '-'
      // Vue 不允许组件名称中出现 '/'
      const k = path.slice(0, -suffix.length).replace(/\//g, '-')
      components[k] = component
    }
  })
  return components
}

/**
 * 扫描 用户自定义组件
 * @param excludePath 排除组件路径 ['src/views/...', 'src/views/...']
 */
export function scanUserComponents(excludePath = []) {
  const contexts = require.context('@/views/', true, /\.vue$/)
  return scanComponents(contexts, excludePath)
}

/**
 * 扫描 公共组件
 * @param excludePath 排除组件路径 ['src/views/...', 'src/views/...']
 */
export function scanCommonComponents(excludePath = []) {
  const contexts = require.context('@/components/', true, /\.vue$/)
  return scanComponents(contexts, excludePath)
}

/**
 * 扫描所有组件
 * @param excludePath 排除组件路径 ['src/views/...', 'src/views/...']
 */
export function scanAllComponents(excludePath = []) {
  return { ...scanUserComponents(excludePath), ...scanCommonComponents(excludePath) }
}

