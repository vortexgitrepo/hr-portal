import { Link, NavLink } from 'react-router-dom'
import { getUser, logout } from '../auth'
import Icon from './Icon'
import ThemeToggle from './ThemeToggle'
import './Navbar.css'

export default function Navbar() {
  const user = getUser()

  const handleLogout = () => {
    logout()
    window.location.href = '/'
  }

  return (
    <header className="navbar">
      <Link to="/" className="navbar-brand">
        <span className="navbar-logo">
          <Icon name="users" size={17} strokeWidth={2.2} />
        </span>
        PeopleHub
      </Link>

      <nav className="navbar-links">
        <NavLink to="/" end>
          Home
        </NavLink>
        <a href="#features">Features</a>
        <a href="#how">How it works</a>
        <a href="#about">About</a>
      </nav>

      <div className="navbar-actions">
        <ThemeToggle />
        {user ? (
          <>
            <Link to="/dashboard" className="btn btn-outline">
              Dashboard
            </Link>
            <button type="button" className="btn btn-ghost" onClick={handleLogout}>
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="nav-signin">
              Sign in
            </Link>
            <Link to="/login" className="btn btn-primary">
              Get Started
              <Icon name="arrowRight" size={16} />
            </Link>
          </>
        )}
      </div>
    </header>
  )
}
