import { useState } from 'react'
import Icon from './Icon'
import { getTheme, toggleTheme } from '../theme'
import './ThemeToggle.css'

export default function ThemeToggle({ className = '' }) {
  const [theme, setTheme] = useState(() => getTheme())

  const handleToggle = () => setTheme(toggleTheme())

  return (
    <button
      type="button"
      className={`theme-toggle ${className}`}
      onClick={handleToggle}
      aria-label={theme === 'dark' ? 'Switch to light mode' : 'Switch to dark mode'}
      title={theme === 'dark' ? 'Light mode' : 'Dark mode'}
    >
      <Icon name={theme === 'dark' ? 'sun' : 'moon'} size={17} />
    </button>
  )
}
