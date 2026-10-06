const KEY = 'hr_portal_theme'

export function getTheme() {
  return document.documentElement.getAttribute('data-theme') || 'light'
}

export function applyTheme(theme) {
  document.documentElement.setAttribute('data-theme', theme)
  try {
    localStorage.setItem(KEY, theme)
  } catch {
    /* storage unavailable */
  }
}

export function initTheme() {
  let saved = null
  try {
    saved = localStorage.getItem(KEY)
  } catch {
    /* storage unavailable */
  }
  const theme = saved || 'dark'
  document.documentElement.setAttribute('data-theme', theme)
  return theme
}

export function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  applyTheme(next)
  return next
}
