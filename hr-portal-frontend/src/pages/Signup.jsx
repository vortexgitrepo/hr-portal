import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { signup } from '../auth'
import Icon from '../components/Icon'
import ThemeToggle from '../components/ThemeToggle'
import './Login.css'

export default function Signup() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ name: '', email: '', password: '', role: 'CANDIDATE' })
  const [showPassword, setShowPassword] = useState(false)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
    setError('')
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!form.name || !form.email || !form.password) {
      setError('Please fill in all fields.')
      return
    }
    if (form.password.length < 8) {
      setError('Password must be at least 8 characters.')
      return
    }

    setLoading(true)
    setError('')
    try {
      await signup({ name: form.name, email: form.email, password: form.password, role: form.role })
      navigate('/login')
    } catch (err) {
      setError(err.message || 'Signup failed. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login">
      <section className="login-brand">
        <div className="login-blob" />
        <Link to="/" className="login-logo">
          <span className="login-logo-mark">
            <Icon name="users" size={17} strokeWidth={2.2} />
          </span>
          PeopleHub
        </Link>

        <div className="login-brand-text">
          <span className="login-tag">
            <Icon name="shield" size={14} /> Join our platform
          </span>
          <h1>
            Create your <span className="gradient-text">account</span>
          </h1>
          <p>
            Sign up as a candidate to search and apply for jobs, or as an HR
            to manage recruitment — all in one place.
          </p>
          <ul>
            <li>
              <Icon name="check" size={16} /> Candidates can apply for jobs & build profiles
            </li>
            <li>
              <Icon name="check" size={16} /> HR can post jobs & manage applications
            </li>
            <li>
              <Icon name="check" size={16} /> Secure role-based access
            </li>
          </ul>
        </div>
      </section>

      <section className="login-form-wrap">
        <div className="login-theme-toggle">
          <ThemeToggle />
        </div>
        <form className="login-form card" onSubmit={handleSubmit}>
          <h2>Sign up</h2>
          <p className="login-sub">Create your account to get started.</p>

          <label htmlFor="name">Full name</label>
          <div className="input-field">
            <Icon name="user" size={17} />
            <input
              id="name"
              name="name"
              type="text"
              autoComplete="name"
              placeholder="e.g. Aarav Sharma"
              value={form.name}
              onChange={handleChange}
            />
          </div>

          <label htmlFor="email">Email address</label>
          <div className="input-field">
            <Icon name="mail" size={17} />
            <input
              id="email"
              name="email"
              type="email"
              autoComplete="email"
              placeholder="you@company.com"
              value={form.email}
              onChange={handleChange}
            />
          </div>

          <label htmlFor="password">Password</label>
          <div className="input-field">
            <Icon name="lock" size={17} />
            <input
              id="password"
              name="password"
              type={showPassword ? 'text' : 'password'}
              autoComplete="new-password"
              placeholder="Min 8 characters"
              value={form.password}
              onChange={handleChange}
            />
            <button
              type="button"
              className="input-action"
              onClick={() => setShowPassword((v) => !v)}
              aria-label={showPassword ? 'Hide password' : 'Show password'}
            >
              <Icon name="eye" size={17} />
            </button>
          </div>

          <label>Register as</label>
          <div className="signup-roles">
            <label className={`signup-role-card ${form.role === 'CANDIDATE' ? 'selected' : ''}`}>
              <input
                type="radio"
                name="role"
                value="CANDIDATE"
                checked={form.role === 'CANDIDATE'}
                onChange={handleChange}
              />
              <Icon name="user" size={20} />
              <div>
                <b>Candidate</b>
                <small>Search & apply for jobs</small>
              </div>
            </label>
            <label className={`signup-role-card ${form.role === 'HR' ? 'selected' : ''}`}>
              <input
                type="radio"
                name="role"
                value="HR"
                checked={form.role === 'HR'}
                onChange={handleChange}
              />
              <Icon name="briefcase" size={20} />
              <div>
                <b>HR</b>
                <small>Post jobs & manage hiring</small>
              </div>
            </label>
          </div>

          {error && (
            <div className="login-error">
              <Icon name="bell" size={15} /> {error}
            </div>
          )}

          <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
            {loading ? (
              <>
                <span className="spinner" /> Creating account…
              </>
            ) : (
              <>
                Create account <Icon name="arrowRight" size={16} />
              </>
            )}
          </button>

          <p className="login-foot">
            Already have an account? <Link to="/login">Sign in</Link>
          </p>
        </form>
      </section>
    </div>
  )
}
