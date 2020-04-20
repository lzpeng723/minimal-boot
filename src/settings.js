module.exports = {
  title: '极简后台管理系统',

  /**
   * @type {boolean} true | false
   * @description 是否在右侧展示布局设置面板
   */
  showSettings: true,

  /**
   * @type {boolean} true | false
   * @description 是否需要 tagsView
   */
  tagsView: true,

  /**
   * @type {boolean} true | false
   * @description 是否固定 Header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description 侧边栏 logo
   */
  sidebarLogo: true,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description 在什么环境下收集错误日志 支持字符串和数组
   */
  errorLog: ['production', 'development']
}
