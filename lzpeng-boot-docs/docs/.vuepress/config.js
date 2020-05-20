module.exports = {
    locales: {
        // 键名是该语言所属的子路径
        // 作为特例，默认语言可以使用 '/' 作为其路径。
        '/': {
            lang: 'en-US', // 将会被设置为 <html> 的 lang 属性
            title: 'Minimal Management System (Lzpeng Boot)',
            description: 'Minimal Management System (Lzpeng Boot)'
        },
        '/zh/': {
            lang: 'zh-CN',
            title: '极简后台管理系统 (Lzpeng Boot)',
            description: '极简后台管理系统 (Lzpeng Boot)'
        }
    },
    themeConfig: {
        locales: {
            '/': {
				logo: '/logo.png',
                selectText: 'Languages',
                label: 'English',
                ariaLabel: 'Languages',
                editLinkText: 'Edit this page on GitHub',
                serviceWorker: {
                    updatePopup: {
                        message: "New content is available.",
                        buttonText: "Refresh"
                    }
                },
                algolia: {},
                nav: [{
                        text: 'Home',
                        link: '/'
                    }, {
                        text: 'Guide',
                        link: '/guide/'
                    }, {
                        text: 'Code',
                        link: 'https://github.com/lzpeng723/lzpeng-boot'
                    }

                ],
                sidebar: {
                    '/guide/': [{
                            title: 'Guide',
                            collapsable: false,
                            children: [{
                                    title: 'Quickstart',
                                    collapsable: true,
                                    path: '/guide/getting-started.html',
                                }, {
                                    title: 'Contents',
                                    collapsable: true,
                                    path: '/guide/directory-structure.html',
                                }
                            ]
                        }
                    ]
                }
            },
            '/zh/': {
				// 图标
				logo: '/logo.png',
                // 多语言下拉菜单的标题
                selectText: '选择语言',
                // 该语言在下拉菜单中的标签
                label: '简体中文',
                // 编辑链接文字
                editLinkText: '在 GitHub 上编辑此页',
                // Service Worker 的配置
                serviceWorker: {
                    updatePopup: {
                        message: "发现新内容可用.",
                        buttonText: "刷新"
                    }
                },
                // 当前 locale 的 algolia docsearch 选项
                algolia: {},
                nav: [{
                        text: '首页',
                        link: '/zh/'
                    }, {
                        text: '指南',
                        link: '/zh/guide/'
                    }, {
                        text: '源码',
                        link: 'https://github.com/lzpeng723/lzpeng-boot'
                    }
                ],
                sidebar: {
                    '/zh/guide/': [{
                            title: '指南', // 侧边栏名称
                            collapsable: false, // 不可折叠
                            children: [{
                                    title: '快速上手',
                                    collapsable: true,
                                    path: '/zh/guide/getting-started.html',
                                }, {
                                    title: '目录结构',
                                    collapsable: true,
                                    path: '/zh/guide/directory-structure.html',
                                }
                            ]
                        }
                    ]
                }
            }
        }
    }
}