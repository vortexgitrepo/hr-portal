import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { getUser, logout } from '../auth'
import Icon from '../components/Icon'
import ThemeToggle from '../components/ThemeToggle'
import DashboardOverview from '../components/DashboardOverview'
import ResourcePage from '../components/ResourcePage'
import { resourceConfigs } from './resourceConfigs'
import './Dashboard.css'

const overviewItem = { key: 'overview', label: 'Overview', icon: 'home' }

const navGroups = (() => {
  const groups = [{ label: 'Main menu', items: [overviewItem] }]
  resourceConfigs.forEach((config) => {
    let group = groups.find((g) => g.label === config.group)
    if (!group) {
      group = { label: config.group, items: [] }
      groups.push(group)
    }
    group.items.push({ key: config.key, label: config.title, icon: config.icon })
  })
  return groups
})()

export default function Dashboard() {
  const navigate = useNavigate()
  const user = getUser()
  const [active, setActive] = useState('overview')

  const activeConfig = resourceConfigs.find((config) => config.key === active)

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  return (
    <div className="dashboard">
      <aside className="dash-sidebar">
        <Link to="/" className="dash-brand">
          <span className="dash-brand-mark">
            <Icon name="users" size={16} strokeWidth={2.2} />
          </span>
          PeopleHub
        </Link>

        {navGroups.map((group) => (
          <div key={group.label} className="dash-nav-group">
            <p className="dash-nav-label">{group.label}</p>
            <nav className="dash-nav">
              {group.items.map((item) => (
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
        ))}

        <div className="dash-upgrade">
          <Icon name="sparkles" size={16} />
          <div>
            <b>Live API</b>
            <small>Connected to hr-portal</small>
          </div>
        </div>

        <button type="button" className="dash-logout" onClick={handleLogout}>
          <Icon name="logout" size={17} /> Logout
        </button>
      </aside>

      <main className="dash-main">
        <header className="dash-topbar">
          <div className="dash-search">
            <Icon name="search" size={17} />
            <input type="search" placeholder="Search employees, reports, leave…" />
          </div>

          <div className="dash-top-actions">
            <ThemeToggle />
            <button type="button" className="dash-icon-btn">
              <Icon name="bell" size={18} />
              <span className="dash-dot" />
            </button>
            <div className="dash-user">
              <span className="dash-avatar">{(user?.name || 'H').charAt(0).toUpperCase()}</span>
              <div>
                <b>{user?.name || 'HR Admin'}</b>
                <small>{user?.role || 'HR Admin'}</small>
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
