import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { getUser, logout } from '../auth'
import { api } from '../api/client'
import Icon from '../components/Icon'
import ThemeToggle from '../components/ThemeToggle'
import ResourcePage from '../components/ResourcePage'
import DashboardOverview from '../components/DashboardOverview'
import { resourceConfigs } from './resourceConfigs'
import './Dashboard.css'

const hrResourceConfigs = resourceConfigs.filter((c) =>
  ['jobs', 'companies', 'applications', 'interviews', 'employees', 'profiles', 'skills'].includes(c.key)
)

export default function HRDashboard() {
  const navigate = useNavigate()
  const user = getUser()
  const [active, setActive] = useState('overview')

  const activeConfig = hrResourceConfigs.find((config) => config.key === active)

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  const navItems = [
    { key: 'overview', label: 'Overview', icon: 'home' },
    ...hrResourceConfigs.map((c) => ({ key: c.key, label: c.title, icon: c.icon })),
  ]

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
          <p className="dash-nav-label">HR Portal</p>
          <nav className="dash-nav">
            {navItems.map((item) => (
              <button
                key={item.key}
                type="button"
                className={active === item.key ? 'active' : ''}
                onClick={() => setActive(item.key)}
              >
                <Icon name={item.icon} size={17} />
                {item.label}
              </button>
            ))}
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
            <input type="search" placeholder="Search..." />
          </div>
          <div className="dash-top-actions">
            <ThemeToggle />
            <div className="dash-user">
              <span className="dash-avatar">{(user?.name || 'H').charAt(0).toUpperCase()}</span>
              <div>
                <b>{user?.name || 'HR'}</b>
                <small>HR</small>
              </div>
            </div>
          </div>
        </header>

        {activeConfig ? (
          <ResourcePage key={activeConfig.key} {...activeConfig} />
        ) : (
          <DashboardOverview user={user} onAddEmployee={() => setActive('employees')} />
        )}
      </main>
    </div>
  )
}
