import { loginRequest } from './api/auth'
import { getToken, setToken } from './api/client'

const STORAGE_KEY = 'hr_portal_user'

export async function login({ email, password }) {
  const data = await loginRequest({ email, password })

  const user = {
    userId: data.userId,
    name: data.name,
    email,
    role: 'HR Admin',
  }

  localStorage.setItem(STORAGE_KEY, JSON.stringify(user))
  setToken(data.token)

  return user
}

export function getUser() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    return raw ? JSON.parse(raw) : null
  } catch {
    return null
  }
}

export function getAuthToken() {
  return getToken()
}

export function logout() {
  localStorage.removeItem(STORAGE_KEY)
  setToken(null)
}
