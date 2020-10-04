import request from '@/utils/request'

export function login(username, password, code, uuid) {
  return request({
    url: '/login',
    method: 'post',
    data: {
      username,
      password,
      code,
      uuid
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/system/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/system/user/logout',
    method: 'post'
  })
}

export function getCodeImg() {
  return request({
    url: '/captchaImage',
    method:'get'
  })
}
