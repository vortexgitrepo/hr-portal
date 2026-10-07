import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { getUser, logout } from '../auth'
import { api } from '../api/client'
import Icon from '../components/Icon'
import ThemeToggle from '../components/ThemeToggle'
import './Dashboard.css'

export default function AdminDashboard() {
  const navigate = useNavigate()
  const user = getUser()
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  useEffect(() => {
    fetchUsers()
  }, [])

  const fetchUsers = async () => {
    setLoading(true)
    try {
      const data = await api.get('/users/all')
      setUsers(data)
    } catch (err) {
      setError('Failed to load users.')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (userId, name) => {
    if (!window.confirm(`Are you sure you want to delete ${name}?`)) return
    setError('')
    setSuccess('')
    try {
      await api.delete(`/users/${userId}`)
      setSuccess(`${name} has been deleted.`)
      fetchUsers()
    } catch (err) {
      setError(err.message || 'Failed to delete user.')
    }
  }

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  const candidates = users.filter((u) => u.role === 'CANDIDATE')
  const hrs = users.filter((u) => u.role === 'HR')

  return (
    <div className="dashboard">
      <aside className="dash-sidebar">
        <div className="dash-brand">
          <span className="dash-brand-mark">
            <Icon name="users" size={16} strokeWidth={2.2} />
          </span>
          PeopleHub
        </div>

        <div className="dash-nav-group">
          <p className="dash-nav-label">Admin</p>
          <nav className="dash-nav">
            <button type="button" className="active">
              <Icon name="shield" size={17} />
              User Management
            </button>
          </nav>
        </div>

        <button type="button" className="dash-logout" onClick={handleLogout}>
          <Icon name="logout" size={17} /> Logout
        </button>
      </aside>

      <main className="dash-main">
        <header className="dash-topbar">
          <div className="dash-search">
            <Icon name="search" size={17} />
            <input type="search" placeholder="Search users..." />
          </div>
          <div className="dash-top-actions">
            <ThemeToggle />
            <div className="dash-user">
              <span className="dash-avatar">{(user?.name || 'A').charAt(0).toUpperCase()}</span>
              <div>
                <b>{user?.name || 'Admin'}</b>
                <small>Admin</small>
              </div>
            </div>
          </div>
        </header>

        <div className="dash-head">
          <div>
            <h1>User Management</h1>
            <p className="dash-sub">Manage HR and candidate accounts</p>
          </div>
        </div>

        {error && (
          <div className="login-error" style={{ marginBottom: 16 }}>
            <Icon name="bell" size={15} /> {error}
          </div>
        )}
        {success && (
          <div style={{ padding: '12px 16px', background: 'var(--success-soft)', color: 'var(--success)', borderRadius: 10, marginBottom: 16, fontSize: 14 }}>
            {success}
          </div>
        )}

        <div className="dash-stats" style={{ gridTemplateColumns: 'repeat(3, 1fr)' }}>
          <div className="dash-stat card">
            <div className="dash-stat-icon color-blue">
              <Icon name="users" size={22} />
            </div>
            <div className="dash-stat-body">
              <small>Total Users</small>
              <b>{users.length}</b>
            </div>
          </div>
          <div className="dash-stat card">
            <div className="dash-stat-icon color-green">
              <Icon name="user" size={22} />
            </div>
            <div className="dash-stat-body">
              <small>Candidates</small>
              <b>{candidates.length}</b>
            </div>
          </div>
          <div className="dash-stat card">
            <div className="dash-stat-icon color-violet">
              <Icon name="briefcase" size={22} />
            </div>
            <div className="dash-stat-body">
              <small>HR Users</small>
              <b>{hrs.length}</b>
            </div>
          </div>
        </div>

        <div className="dash-block card">
          <div className="dash-block-head">
            <div>
              <h2>All Users</h2>
              <p>Delete HR or candidate accounts</p>
            </div>
          </div>
          {loading ? (
            <p className="muted">Loading users...</p>
          ) : users.length === 0 ? (
            <p className="muted">No users found.</p>
          ) : (
            <table className="dash-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {users.map((u) => (
                  <tr key={u.id}>
                    <td>
                      <div className="emp-cell">
                        <span className="dash-avatar" style={{ width: 32, height: 32, fontSize: 13 }}>
                          {u.name?.charAt(0).toUpperCase()}
                        </span>
                        <b>{u.name}</b>
                      </div>
                    </td>
                    <td className="muted">{u.email}</td>
                    <td>
                      <span className={`pill ${u.role === 'ADMIN' ? 'pill-danger' : u.role === 'HR' ? 'pill-active' : ''}`}>
                        {u.role}
                      </span>
                    </td>
                    <td>
                      {u.role !== 'ADMIN' && (
                        <button
                          type="button"
                          className="btn btn-outline"
                          style={{ padding: '6px 12px', fontSize: 12, color: 'var(--danger)', borderColor: 'rgba(220,38,38,0.4)' }}
                          onClick={() => handleDelete(u.id, u.name)}
                        >
                          Delete
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </main>
    </div>
  )
}
