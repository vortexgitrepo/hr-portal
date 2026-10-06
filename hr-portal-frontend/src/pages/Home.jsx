import { Link } from 'react-router-dom'
import Navbar from '../components/Navbar'
import Icon from '../components/Icon'
import './Home.css'

const features = [
  {
    icon: 'users',
    title: 'Employee Records',
    text: 'Centralized profiles, documents and a live org chart for your entire workforce.',
    tone: 'blue',
  },
  {
    icon: 'calendar',
    title: 'Leave Management',
    text: 'Request, approve and track leave balances without spreadsheet chaos.',
    tone: 'violet',
  },
  {
    icon: 'clock',
    title: 'Attendance & Shifts',
    text: 'Track check-ins, work hours and shift schedules in real time.',
    tone: 'teal',
  },
  {
    icon: 'wallet',
    title: 'Payroll Overview',
    text: 'Review salary runs, deductions and payslips from a single screen.',
    tone: 'amber',
  },
  {
    icon: 'briefcase',
    title: 'Recruitment',
    text: 'Post openings, screen applicants and move candidates through stages.',
    tone: 'rose',
  },
  {
    icon: 'chart',
    title: 'Reports & Insights',
    text: 'Headcount, attrition and attendance reports ready for leadership.',
    tone: 'indigo',
  },
]

const stats = [
  { value: '1,200+', label: 'Employees managed' },
  { value: '98%', label: 'On-time payroll' },
  { value: '45%', label: 'Less admin work' },
  { value: '4.9/5', label: 'HR team rating' },
]

const logos = ['Northwind', 'Acme Corp', 'Lumina', 'Vertex Labs', 'Orbital', 'Fintrail']

const steps = [
  {
    icon: 'zap',
    title: 'Set up your org',
    text: 'Import employees, departments and reporting lines in minutes.',
  },
  {
    icon: 'sliders',
    title: 'Automate the busywork',
    text: 'Leave workflows, attendance rules and payroll runs run themselves.',
  },
  {
    icon: 'award',
    title: 'Lead with insights',
    text: 'Live dashboards give HR and leadership a clear picture of the workforce.',
  },
]

export default function Home() {
  return (
    <div className="home">
      <Navbar />

      <section className="hero">
        <div className="hero-blob hero-blob-1" />
        <div className="hero-blob hero-blob-2" />

        <div className="hero-content fade-up">
          <span className="hero-badge">
            <Icon name="sparkles" size={14} /> People management, simplified
          </span>
          <h1>
            Run your entire <span className="gradient-text">HR workflow</span> from one
            elegant portal
          </h1>
          <p>
            PeopleHub helps HR teams manage employees, leave, attendance and payroll in a
            single place — no more scattered files, manual updates or missed approvals.
          </p>
          <div className="hero-actions">
            <Link to="/login" className="btn btn-primary">
              Login to Portal
              <Icon name="arrowRight" size={16} />
            </Link>
            <a href="#features" className="btn btn-outline">
              Explore Features
            </a>
          </div>
          <div className="hero-trust">
            <div className="hero-avatars">
              <span>AS</span>
              <span>PN</span>
              <span>DL</span>
              <span>SM</span>
            </div>
            <p>
              Trusted by <b>240+ HR teams</b> to run daily people operations
            </p>
          </div>
        </div>

        <div className="hero-visual fade-up" aria-hidden="true">
          <div className="mock-window card">
            <div className="mock-top">
              <i className="dot red" />
              <i className="dot amber" />
              <i className="dot green" />
              <strong>PeopleHub · Overview</strong>
            </div>

            <div className="mock-stats">
              <div>
                <small>Present today</small>
                <b>342</b>
                <span className="up">+4.2%</span>
              </div>
              <div>
                <small>On leave</small>
                <b>18</b>
                <span className="down">-1.1%</span>
              </div>
              <div>
                <small>Open roles</small>
                <b>7</b>
                <span className="up">+2</span>
              </div>
            </div>

            <div className="mock-chart">
              {[52, 74, 45, 88, 63, 79, 58, 92].map((h, i) => (
                <span key={i} style={{ height: `${h}%` }} />
              ))}
            </div>

            <div className="mock-rows">
              <div className="mock-row">
                <span className="ava blue">AS</span>
                <div>
                  <b>Aarav Sharma</b>
                  <small>Engineering · Checked in 9:04 AM</small>
                </div>
                <em className="pill pill-active">Active</em>
              </div>
              <div className="mock-row">
                <span className="ava violet">PN</span>
                <div>
                  <b>Priya Nair</b>
                  <small>Design · Leave until 13 Oct</small>
                </div>
                <em className="pill pill-on-leave">On Leave</em>
              </div>
            </div>
          </div>

          <div className="float-chip float-chip-1">
            <Icon name="check" size={15} /> Payroll approved
          </div>
          <div className="float-chip float-chip-2">
            <Icon name="bell" size={15} /> 3 leave requests
          </div>
        </div>
      </section>

      <section className="logos">
        <p>Powering people teams at</p>
        <div className="logos-row">
          {logos.map((l) => (
            <span key={l}>{l}</span>
          ))}
        </div>
      </section>

      <section className="stats">
        {stats.map((s) => (
          <div key={s.label} className="stat card">
            <b>{s.value}</b>
            <small>{s.label}</small>
          </div>
        ))}
      </section>

      <section className="features" id="features">
        <div className="section-head">
          <span className="section-tag">Features</span>
          <h2>Everything your HR team needs</h2>
          <p>Built for growing companies that want structure without complexity.</p>
        </div>
        <div className="feature-grid">
          {features.map((f) => (
            <div key={f.title} className="feature card">
              <div className={`feature-icon tone-${f.tone}`}>
                <Icon name={f.icon} size={22} />
              </div>
              <h3>{f.title}</h3>
              <p>{f.text}</p>
              <span className="feature-link">
                Learn more <Icon name="arrowRight" size={14} />
              </span>
            </div>
          ))}
        </div>
      </section>

      <section className="how" id="how">
        <div className="section-head">
          <span className="section-tag">How it works</span>
          <h2>Live in days, not months</h2>
          <p>Three simple steps to a structured, transparent HR process.</p>
        </div>
        <div className="steps">
          {steps.map((s, i) => (
            <div key={s.title} className="step card">
              <span className="step-num">0{i + 1}</span>
              <div className="step-icon">
                <Icon name={s.icon} size={20} />
              </div>
              <h3>{s.title}</h3>
              <p>{s.text}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="about" id="about">
        <div className="about-card card">
          <div className="about-quote">
            <Icon name="sparkles" size={26} />
            <p>
              “We replaced four spreadsheets and a shared inbox with PeopleHub. Leave
              approvals that took days now happen the same morning.”
            </p>
            <div className="about-author">
              <span className="ava violet">SM</span>
              <div>
                <b>Sofia Martins</b>
                <small>Head of People, Northwind</small>
              </div>
            </div>
          </div>
          <div className="about-points">
            <h2>Built for modern HR teams</h2>
            <ul>
              <li>
                <Icon name="check" size={16} /> Role-based access for HR, managers and staff
              </li>
              <li>
                <Icon name="check" size={16} /> Audit-friendly history on every approval
              </li>
              <li>
                <Icon name="check" size={16} /> Works on desktop, tablet and mobile
              </li>
              <li>
                <Icon name="shield" size={16} /> Demo mode — no real employee data stored
              </li>
            </ul>
            <Link to="/login" className="btn btn-primary">
              Try the demo
              <Icon name="arrowRight" size={16} />
            </Link>
          </div>
        </div>
      </section>

      <section className="cta">
        <h2>Ready to streamline HR?</h2>
        <p>Sign in with the demo credentials and explore the dashboard.</p>
        <Link to="/login" className="btn cta-btn">
          Go to Login
          <Icon name="arrowRight" size={16} />
        </Link>
      </section>

      <footer className="footer">
        <div className="footer-grid">
          <div className="footer-brand">
            <span className="footer-logo">
              <Icon name="users" size={16} strokeWidth={2.2} />
            </span>
            <b>PeopleHub</b>
            <p>The friendly HR portal for growing teams.</p>
          </div>
          <div>
            <h4>Product</h4>
            <a href="#features">Features</a>
            <a href="#how">How it works</a>
            <a href="/login">Demo login</a>
          </div>
          <div>
            <h4>Company</h4>
            <a href="#about">About</a>
            <a href="#about">Careers</a>
            <a href="#about">Contact</a>
          </div>
          <div>
            <h4>Support</h4>
            <a href="#about">Help center</a>
            <a href="#about">Privacy</a>
            <a href="#about">Terms</a>
          </div>
        </div>
        <div className="footer-bottom">
          <p>© {new Date().getFullYear()} PeopleHub HR Portal — demo build, data is dummy.</p>
        </div>
      </footer>
    </div>
  )
}
