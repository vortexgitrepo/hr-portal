import { api } from './client'

export function loginRequest({ email, password }) {
  return api.post('/users/login', { email, password })
}

export function signupRequest({ name, email, password, role }) {
  return api.post('/users/register', { name, email, password, role })
}
