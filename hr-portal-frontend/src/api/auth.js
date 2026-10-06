import { api } from './client'

export function loginRequest({ email, password }) {
  return api.post('/users/login', { email, password })
}
