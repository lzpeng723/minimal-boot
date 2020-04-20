import { Base64 } from 'js-base64'

const TokenKey = 'Admin-Token'
const client = {
  clientId: 'lzpeng',
  clientSecret: '123456'
}

export function getToken() {
  return sessionStorage.getItem(TokenKey)
}

export function setToken(token) {
  return sessionStorage.setItem(TokenKey, JSON.stringify(token))
}

export function removeToken() {
  return sessionStorage.removeItem(TokenKey)
}

export function getBasicClient() {
  return Base64.encode(`${client.clientId}:${client.clientSecret}`)
}
