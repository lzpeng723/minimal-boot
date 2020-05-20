import request from '@/utils/request'

/**
 * 调用后台 OAuth2 密码模式登录
 * @param data
 */
export function login(data) {
  return request({
    url: '/oauth/token',
    method: 'post',
    params: { ...data, grant_type: 'password' }
  })
}

/**
 * 获取用户信息
 */
export function getInfo() {
  return request({
    url: '/sys/user/me',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
