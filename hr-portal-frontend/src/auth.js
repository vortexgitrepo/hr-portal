import { loginRequest, signupRequest } from './api/auth'
import { getToken, setToken } from './api/client'

const STORAGE_KEY = 'hr_portal_user'

export async function login({ email, password }) {
  const data = await loginRequest({ email, password })

  const user = {
    userId: data.userId,
    name: data.name,
    email: data.email || email,
    role: data.role || 'CANDIDATE',
  }

  localStorage.setItem(STORAGE_KEY, JSON.stringify(user))
  setToken(data.token)

  return user
}

export async function signup({ name, email, password, role }) {
  const data = await signupRequest({ name, email, password, role })

  const user = {
    userId: data.userId,
    name: data.name,
    email: data.email || email,
    role: data.role || role || 'CANDIDATE',
  }

  localStorage.setItem(STORAGE_KEY, JSON.stringify(user))

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
