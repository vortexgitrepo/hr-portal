import Icon from './Icon'

const stats = [
  {
    label: 'Total Employees',
    value: '1,248',
    change: '+12 this month',
    tone: 'up',
    icon: 'users',
    color: 'blue',
  },
  {
    label: 'Present Today',
    value: '1,104',
    change: '88% attendance',
    tone: 'up',
    icon: 'check',
    color: 'green',
  },
  {
    label: 'On Leave',
    value: '64',
    change: '12 pending approval',
    tone: 'warn',
    icon: 'calendar',
    color: 'amber',
  },
  {
    label: 'Open Positions',
    value: '7',
    change: '38 applicants',
    tone: 'info',
    icon: 'briefcase',
    color: 'violet',
  },
]

const employees = [
  { id: 'EMP-1001', name: 'Aarav Sharma', dept: 'Engineering', role: 'Frontend Dev', status: 'Active', tone: 'blue' },
  { id: 'EMP-1002', name: 'Priya Nair', dept: 'Design', role: 'Product Designer', status: 'Active', tone: 'violet' },
  { id: 'EMP-1003', name: 'Daniel Lee', dept: 'Sales', role: 'Account Exec', status: 'Remote', tone: 'teal' },
  { id: 'EMP-1004', name: 'Sofia Martins', dept: 'HR', role: 'HR Executive', status: 'Active', tone: 'rose' },
  { id: 'EMP-1005', name: 'Kabir Singh', dept: 'Finance', role: 'Analyst', status: 'On Leave', tone: 'amber' },
]

const leaveRequests = [
  { name: 'Aarav Sharma', type: 'Casual Leave', dates: '12 – 13 Oct', status: 'Pending' },
  { name: 'Sofia Martins', type: 'Sick Leave', dates: '08 Oct', status: 'Approved' },
  { name: 'Kabir Singh', type: 'Vacation', dates: '20 – 25 Oct', status: 'Pending' },
  { name: 'Priya Nair', type: 'Work From Home', dates: '10 Oct', status: 'Rejected' },
]

const chartData = [
  { day: 'Mon', value: 92 },
  { day: 'Tue', value: 96 },
  { day: 'Wed', value: 88 },
  { day: 'Thu', value: 94 },
  { day: 'Fri', value: 90 },
  { day: 'Sat', value: 41 },
  { day: 'Sun', value: 18 },
]

const activity = [
  { icon: 'check', color: 'green', text: 'Leave request approved for Sofia Martins', time: '12 min ago' },
  { icon: 'user', color: 'blue', text: 'New employee onboarding: Kabir Singh', time: '1 hour ago' },
  { icon: 'wallet', color: 'violet', text: 'Payroll draft for October generated', time: '3 hours ago' },
  { icon: 'file', color: 'amber', text: 'Attendance report exported by Priya Nair', time: 'Yesterday' },
]

const initials = (name) =>
  name
    .split(' ')
    .map((n) => n[0])
    .slice(0, 2)
    .join('')

export default function DashboardOverview({ user, onAddEmployee }) {
  return (
    <>
      <div className="dash-head">
        <div>
          <h1>Overview</h1>
          <p className="dash-sub">
            Welcome back, {user?.name || 'HR Admin'} — here&apos;s what&apos;s happening today.
          </p>
        </div>
        <div className="dash-head-actions">
          <button type="button" className="btn btn-outline">
            <Icon name="file" size={16} /> Export
          </button>
          <button type="button" className="btn btn-primary" onClick={onAddEmployee}>
            <Icon name="plus" size={16} /> Add Employee
          </button>
        </div>
      </div>

      <section className="dash-stats">
        {stats.map((s) => (
          <div key={s.label} className="dash-stat card">
            <div className={`dash-stat-icon color-${s.color}`}>
              <Icon name={s.icon} size={20} />
            </div>
            <div className="dash-stat-body">
              <small>{s.label}</small>
              <b>{s.value}</b>
              <span className={`tone-${s.tone}`}>
                <Icon name="trendingUp" size={13} /> {s.change}
              </span>
            </div>
          </div>
        ))}
      </section>

      <section className="dash-grid">
        <div className="card dash-block dash-attendance">
          <div className="dash-block-head">
            <div>
              <h2>Attendance this week</h2>
              <p>Daily presence across all departments</p>
            </div>
            <span className="pill pill-active">+4.2% vs last week</span>
          </div>
          <div className="dash-chart">
            {chartData.map((d) => (
              <div key={d.day} className="dash-chart-col">
                <div className="dash-chart-bar" style={{ height: `${d.value}%` }}>
                  <em>{d.value}%</em>
                </div>
                <span>{d.day}</span>
              </div>
            ))}
          </div>
        </div>

        <div className="card dash-block">
          <div className="dash-block-head">
            <div>
              <h2>Recent activity</h2>
              <p>Last 24 hours</p>
            </div>
          </div>
          <ul className="dash-activity">
            {activity.map((a) => (
              <li key={a.text}>
                <span className={`activity-icon color-${a.color}`}>
                  <Icon name={a.icon} size={14} />
                </span>
                <div>
                  <p>{a.text}</p>
                  <small>{a.time}</small>
                </div>
              </li>
            ))}
          </ul>
        </div>
      </section>

      <section className="dash-grid">
        <div className="card dash-block">
          <div className="dash-block-head">
            <div>
              <h2>Employees</h2>
              <p>Recently updated profiles</p>
            </div>
            <button type="button" className="btn btn-outline">
              View all
            </button>
          </div>
          <table className="dash-table">
            <thead>
              <tr>
                <th>Employee</th>
                <th>ID</th>
                <th>Department</th>
                <th>Role</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {employees.map((emp) => (
                <tr key={emp.id}>
                  <td>
                    <div className="emp-cell">
                      <span className={`ava tone-${emp.tone}`}>{initials(emp.name)}</span>
                      <b>{emp.name}</b>
                    </div>
                  </td>
                  <td className="muted">{emp.id}</td>
                  <td>{emp.dept}</td>
                  <td className="muted">{emp.role}</td>
                  <td>
                    <span className={`pill pill-${emp.status.toLowerCase().replace(/\s/g, '-')}`}>
                      {emp.status}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="card dash-block">
          <div className="dash-block-head">
            <div>
              <h2>Leave requests</h2>
              <p>Awaiting your action</p>
            </div>
            <button type="button" className="btn btn-ghost">
              View all
            </button>
          </div>
          <ul className="dash-leaves">
            {leaveRequests.map((l, i) => (
              <li key={l.name + l.dates}>
                <span className={`ava tone-${['blue', 'teal', 'amber', 'violet'][i % 4]}`}>
                  {initials(l.name)}
                </span>
                <div className="leave-info">
                  <b>{l.name}</b>
                  <small>
                    {l.type} · {l.dates}
                  </small>
                </div>
                <span className={`pill pill-${l.status.toLowerCase()}`}>{l.status}</span>
              </li>
            ))}
          </ul>
          <div className="leave-actions">
            <button type="button" className="btn btn-primary btn-block">
              Approve selected
            </button>
            <button type="button" className="btn btn-outline btn-block">
              Reject
            </button>
          </div>
        </div>
      </section>

      <p className="dash-note">
        Overview widgets are sample data — manage live records from the menu on the left.
      </p>
    </>
  )
}
