import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { login } from '../auth'
import Icon from '../components/Icon'
import ThemeToggle from '../components/ThemeToggle'
import './Login.css'

export default function Login() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ email: 'sajid@gmail.com', password: '12345' })
  const [showPassword, setShowPassword] = useState(false)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
    setError('')
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!form.email || !form.password) {
      setError('Please enter email and password.')
      return
    }

    setLoading(true)
    setError('')
    try {
      await login({ email: form.email, password: form.password })
      navigate('/dashboard')
    } catch (err) {
      setError(err.message || 'Login failed. Please try again.')
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
            <Icon name="shield" size={14} /> Secure HR workspace
          </span>
          <h1>
            Welcome back to your <span className="gradient-text">people hub</span>
          </h1>
          <p>
            Sign in to manage employees, leave requests, attendance and payroll — all in
            one place.
          </p>
          <ul>
            <li>
              <Icon name="check" size={16} /> Role-based access for every team
            </li>
            <li>
              <Icon name="check" size={16} /> Real-time workforce insights
            </li>
            <li>
              <Icon name="check" size={16} /> Demo mode — no real data is used
            </li>
          </ul>
        </div>

        <div className="login-mini card">
          <div className="login-mini-row">
            <span className="ava blue">AS</span>
            <div>
              <b>342 employees checked in</b>
              <small>Today · 9:00 AM cutoff</small>
            </div>
            <span className="pill pill-active">Live</span>
          </div>
        </div>
      </section>

      <section className="login-form-wrap">
        <div className="login-theme-toggle">
          <ThemeToggle />
        </div>
        <form className="login-form card" onSubmit={handleSubmit}>
          <h2>Sign in</h2>
          <p className="login-sub">Sign in with your registered email and password.</p>

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
              autoComplete="current-password"
              placeholder="••••••••"
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

          <div className="login-row">
            <label className="login-check">
              <input type="checkbox" defaultChecked /> Remember me
            </label>
            <a href="#forgot">Forgot password?</a>
          </div>

          {error && (
            <div className="login-error">
              <Icon name="bell" size={15} /> {error}
            </div>
          )}

          <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
            {loading ? (
              <>
                <span className="spinner" /> Signing in…
              </>
            ) : (
              <>
                Sign in to Dashboard <Icon name="arrowRight" size={16} />
              </>
            )}
          </button>

          <div className="login-demo">
            <Icon name="zap" size={14} />
            Demo login — <b>sajid@gmail.com</b> / <b>12345</b>
          </div>

          <p className="login-foot">
            Don&apos;t have an account? <a href="#signup">Request access</a>
          </p>
        </form>
      </section>
    </div>
  )
}
