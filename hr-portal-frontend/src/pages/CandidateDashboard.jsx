import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { getUser, logout } from '../auth'
import { api } from '../api/client'
import Icon from '../components/Icon'
import ThemeToggle from '../components/ThemeToggle'
import './Dashboard.css'

export default function CandidateDashboard() {
  const navigate = useNavigate()
  const user = getUser()
  const [active, setActive] = useState('jobs')
  const [jobs, setJobs] = useState([])
  const [applications, setApplications] = useState([])
  const [profile, setProfile] = useState(null)
  const [loading, setLoading] = useState(false)
  const [search, setSearch] = useState('')
  const [showApplyModal, setShowApplyModal] = useState(false)
  const [selectedJob, setSelectedJob] = useState(null)
  const [applyForm, setApplyForm] = useState({ resumeId: '' })
  const [resumes, setResumes] = useState([])
  const [applySuccess, setApplySuccess] = useState('')
  const [applyError, setApplyError] = useState('')

  const [profileForm, setProfileForm] = useState({
    headline: '',
    summary: '',
    experienceYears: '',
    currentCompany: '',
    currentLocation: '',
    preferredLocation: '',
    expectedSalary: '',
    noticePeriod: '',
  })
  const [profileMsg, setProfileMsg] = useState('')

  const [showResumeModal, setShowResumeModal] = useState(false)
  const [editingResume, setEditingResume] = useState(null)
  const [resumeForm, setResumeForm] = useState({ fileName: '', resumeUrl: '', isDefault: false })
  const [resumeMsg, setResumeMsg] = useState('')

  useEffect(() => {
    fetchJobs()
    fetchApplications()
    fetchProfile()
    fetchResumes()
  }, [])

  const fetchJobs = async () => {
    try {
      const data = await api.get('/jobs')
      setJobs(data)
    } catch (err) {
      console.error('Failed to fetch jobs', err)
    }
  }

  const fetchApplications = async () => {
    try {
      const data = await api.get(`/applications?jobSeekerId=${user.userId}`)
      setApplications(data)
    } catch (err) {
      console.error('Failed to fetch applications', err)
    }
  }

  const fetchProfile = async () => {
    try {
      const data = await api.get(`/job-seeker-profiles?userId=${user.userId}`)
      if (data && data.length > 0) {
        setProfile(data[0])
        setProfileForm({
          headline: data[0].headline || '',
          summary: data[0].summary || '',
          experienceYears: data[0].experienceYears || '',
          currentCompany: data[0].currentCompany || '',
          currentLocation: data[0].currentLocation || '',
          preferredLocation: data[0].preferredLocation || '',
          expectedSalary: data[0].expectedSalary || '',
          noticePeriod: data[0].noticePeriod || '',
        })
      }
    } catch (err) {
      console.error('Failed to fetch profile', err)
    }
  }

  const fetchResumes = async () => {
    try {
      const data = await api.get('/resumes/my')
      setResumes(data)
    } catch (err) {
      console.error('Failed to fetch resumes', err)
    }
  }

  const openCreateResume = () => {
    setEditingResume(null)
    setResumeForm({ fileName: '', resumeUrl: '', isDefault: false })
    setResumeMsg('')
    setShowResumeModal(true)
  }

  const openEditResume = (resume) => {
    setEditingResume(resume)
    setResumeForm({
      fileName: resume.fileName || '',
      resumeUrl: resume.resumeUrl || '',
      isDefault: resume.isDefault || false,
    })
    setResumeMsg('')
    setShowResumeModal(true)
  }

  const handleSaveResume = async (e) => {
    e.preventDefault()
    setResumeMsg('')
    if (!resumeForm.fileName || !resumeForm.resumeUrl) {
      setResumeMsg('Please fill in all fields.')
      return
    }
    try {
      if (editingResume) {
        await api.put(`/resumes/my/${editingResume.id}`, resumeForm)
        setResumeMsg('Resume updated successfully!')
      } else {
        await api.post('/resumes/my', resumeForm)
        setResumeMsg('Resume created successfully!')
      }
      setShowResumeModal(false)
      fetchResumes()
    } catch (err) {
      setResumeMsg(err.message || 'Failed to save resume.')
    }
  }

  const handleDeleteResume = async (resume) => {
    if (!window.confirm(`Delete resume "${resume.fileName}"?`)) return
    try {
      await api.delete(`/resumes/my/${resume.id}`)
      fetchResumes()
    } catch (err) {
      console.error('Failed to delete resume', err)
    }
  }

  const handleApply = async (e) => {
    e.preventDefault()
    setApplyError('')
    setApplySuccess('')
    if (!applyForm.resumeId) {
      setApplyError('Please select a resume.')
      return
    }
    try {
      await api.post('/applications', {
        jobId: selectedJob.id,
        jobSeekerId: user.userId,
        resumeId: Number(applyForm.resumeId),
        status: 'APPLIED',
      })
      setApplySuccess('Application submitted successfully!')
      setShowApplyModal(false)
      setApplyForm({ resumeId: '' })
      fetchApplications()
    } catch (err) {
      setApplyError(err.message || 'Failed to apply.')
    }
  }

  const handleSaveProfile = async (e) => {
    e.preventDefault()
    setProfileMsg('')
    try {
      const payload = {
        userId: user.userId,
        ...profileForm,
        experienceYears: profileForm.experienceYears ? Number(profileForm.experienceYears) : null,
        expectedSalary: profileForm.expectedSalary ? Number(profileForm.expectedSalary) : null,
        noticePeriod: profileForm.noticePeriod ? Number(profileForm.noticePeriod) : null,
      }
      if (profile) {
        await api.put(`/job-seeker-profiles/${profile.id}`, payload)
        setProfileMsg('Profile updated successfully!')
      } else {
        await api.post('/job-seeker-profiles', payload)
        setProfileMsg('Profile created successfully!')
      }
      fetchProfile()
    } catch (err) {
      setProfileMsg(err.message || 'Failed to save profile.')
    }
  }

  const filteredJobs = jobs.filter(
    (j) =>
      j.title?.toLowerCase().includes(search.toLowerCase()) ||
      j.location?.toLowerCase().includes(search.toLowerCase()) ||
      j.description?.toLowerCase().includes(search.toLowerCase())
  )

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  const navItems = [
    { key: 'jobs', label: 'Browse Jobs', icon: 'briefcase' },
    { key: 'applications', label: 'My Applications', icon: 'clipboard' },
    { key: 'resumes', label: 'My Resumes', icon: 'file' },
    { key: 'profile', label: 'My Profile', icon: 'user' },
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
          <p className="dash-nav-label">Candidate</p>
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
            <input
              type="search"
              placeholder="Search jobs..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
          </div>
          <div className="dash-top-actions">
            <ThemeToggle />
            <div className="dash-user">
              <span className="dash-avatar">{(user?.name || 'C').charAt(0).toUpperCase()}</span>
              <div>
                <b>{user?.name || 'Candidate'}</b>
                <small>Candidate</small>
              </div>
            </div>
          </div>
        </header>

        {active === 'jobs' && (
          <div>
            <div className="dash-head">
              <div>
                <h1>Browse Jobs</h1>
                <p className="dash-sub">Find and apply for your next opportunity</p>
              </div>
            </div>
            <div className="dash-grid" style={{ gridTemplateColumns: '1fr' }}>
              <div className="dash-block card">
                {filteredJobs.length === 0 ? (
                  <p className="muted">No jobs found.</p>
                ) : (
                  filteredJobs.map((job) => (
                    <div key={job.id} style={{ padding: '16px 0', borderBottom: '1px solid var(--border)' }}>
                      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                        <div>
                          <h3 style={{ fontSize: 16, color: 'var(--ink)' }}>{job.title}</h3>
                          <p className="muted" style={{ fontSize: 13, marginTop: 4 }}>
                            {job.location} · {job.employmentType} · {job.status}
                          </p>
                          {job.salaryMin && (
                            <p style={{ fontSize: 13, color: 'var(--primary)', marginTop: 4 }}>
                              ₹{Number(job.salaryMin).toLocaleString()} - ₹{Number(job.salaryMax).toLocaleString()}
                            </p>
                          )}
                        </div>
                        <button
                          type="button"
                          className="btn btn-primary"
                          style={{ padding: '8px 16px', fontSize: 13 }}
                          onClick={() => {
                            setSelectedJob(job)
                            setShowApplyModal(true)
                            setApplyError('')
                            setApplySuccess('')
                          }}
                        >
                          Apply
                        </button>
                      </div>
                    </div>
                  ))
                )}
              </div>
            </div>
          </div>
        )}

        {active === 'applications' && (
          <div>
            <div className="dash-head">
              <div>
                <h1>My Applications</h1>
                <p className="dash-sub">Track your job applications</p>
              </div>
            </div>
            <div className="dash-block card">
              {applications.length === 0 ? (
                <p className="muted">No applications yet. Start by browsing jobs!</p>
              ) : (
                <table className="dash-table">
                  <thead>
                    <tr>
                      <th>Job ID</th>
                      <th>Status</th>
                      <th>Applied At</th>
                    </tr>
                  </thead>
                  <tbody>
                    {applications.map((app) => (
                      <tr key={app.id}>
                        <td>{app.jobId}</td>
                        <td>
                          <span className="pill pill-active">{app.status}</span>
                        </td>
                        <td className="muted">{new Date(app.appliedAt).toLocaleDateString()}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              )}
            </div>
          </div>
        )}

        {active === 'resumes' && (
          <div>
            <div className="dash-head">
              <div>
                <h1>My Resumes</h1>
                <p className="dash-sub">Manage your resume files</p>
              </div>
              <div className="dash-head-actions">
                <button type="button" className="btn btn-primary" onClick={openCreateResume}>
                  <Icon name="plus" size={16} /> Upload Resume
                </button>
              </div>
            </div>
            <div className="dash-block card">
              {resumes.length === 0 ? (
                <p className="muted">No resumes yet. Upload your first resume!</p>
              ) : (
                <table className="dash-table">
                  <thead>
                    <tr>
                      <th>File Name</th>
                      <th>URL</th>
                      <th>Default</th>
                      <th>Uploaded</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {resumes.map((resume) => (
                      <tr key={resume.id}>
                        <td>{resume.fileName}</td>
                        <td className="muted" style={{ maxWidth: 200, overflow: 'hidden', textOverflow: 'ellipsis' }}>
                          {resume.resumeUrl}
                        </td>
                        <td>
                          {resume.isDefault && <span className="pill pill-active">Default</span>}
                        </td>
                        <td className="muted">{new Date(resume.createdAt).toLocaleDateString()}</td>
                        <td>
                          <button
                            type="button"
                            className="res-icon-btn"
                            title="Edit"
                            onClick={() => openEditResume(resume)}
                          >
                            <Icon name="edit" size={15} />
                          </button>
                          <button
                            type="button"
                            className="res-icon-btn res-icon-btn-danger"
                            title="Delete"
                            onClick={() => handleDeleteResume(resume)}
                          >
                            <Icon name="trash" size={15} />
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              )}
            </div>
          </div>
        )}

        {active === 'profile' && (
          <div>
            <div className="dash-head">
              <div>
                <h1>My Profile</h1>
                <p className="dash-sub">Build your job seeker profile</p>
              </div>
            </div>
            <div className="dash-block card">
              <form onSubmit={handleSaveProfile}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 16 }}>
                  <div>
                    <label style={{ fontSize: 13, fontWeight: 600 }}>Headline</label>
                    <input
                      type="text"
                      className="input-field"
                      style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                      placeholder="e.g. Senior React Developer"
                      value={profileForm.headline}
                      onChange={(e) => setProfileForm({ ...profileForm, headline: e.target.value })}
                    />
                  </div>
                  <div>
                    <label style={{ fontSize: 13, fontWeight: 600 }}>Experience (years)</label>
                    <input
                      type="number"
                      className="input-field"
                      style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                      placeholder="e.g. 5"
                      value={profileForm.experienceYears}
                      onChange={(e) => setProfileForm({ ...profileForm, experienceYears: e.target.value })}
                    />
                  </div>
                  <div>
                    <label style={{ fontSize: 13, fontWeight: 600 }}>Current Company</label>
                    <input
                      type="text"
                      className="input-field"
                      style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                      placeholder="e.g. Acme Corp"
                      value={profileForm.currentCompany}
                      onChange={(e) => setProfileForm({ ...profileForm, currentCompany: e.target.value })}
                    />
                  </div>
                  <div>
                    <label style={{ fontSize: 13, fontWeight: 600 }}>Current Location</label>
                    <input
                      type="text"
                      className="input-field"
                      style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                      placeholder="e.g. Bengaluru"
                      value={profileForm.currentLocation}
                      onChange={(e) => setProfileForm({ ...profileForm, currentLocation: e.target.value })}
                    />
                  </div>
                  <div>
                    <label style={{ fontSize: 13, fontWeight: 600 }}>Expected Salary</label>
                    <input
                      type="number"
                      className="input-field"
                      style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                      placeholder="e.g. 1500000"
                      value={profileForm.expectedSalary}
                      onChange={(e) => setProfileForm({ ...profileForm, expectedSalary: e.target.value })}
                    />
                  </div>
                  <div>
                    <label style={{ fontSize: 13, fontWeight: 600 }}>Notice Period (days)</label>
                    <input
                      type="number"
                      className="input-field"
                      style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                      placeholder="e.g. 30"
                      value={profileForm.noticePeriod}
                      onChange={(e) => setProfileForm({ ...profileForm, noticePeriod: e.target.value })}
                    />
                  </div>
                </div>
                <div style={{ marginTop: 16 }}>
                  <label style={{ fontSize: 13, fontWeight: 600 }}>Summary</label>
                  <textarea
                    className="input-field"
                    style={{ width: '100%', marginTop: 6, padding: '10px 14px', minHeight: 100 }}
                    placeholder="Short professional summary..."
                    value={profileForm.summary}
                    onChange={(e) => setProfileForm({ ...profileForm, summary: e.target.value })}
                  />
                </div>
                {profileMsg && (
                  <p style={{ marginTop: 12, color: 'var(--success)', fontSize: 14 }}>{profileMsg}</p>
                )}
                <button type="submit" className="btn btn-primary" style={{ marginTop: 16 }}>
                  Save Profile
                </button>
              </form>
            </div>
          </div>
        )}
      </main>

      {showApplyModal && selectedJob && (
        <div className="modal-overlay" onClick={() => setShowApplyModal(false)}>
          <div className="modal card" onClick={(e) => e.stopPropagation()}>
            <h3>Apply for {selectedJob.title}</h3>
            <p className="muted" style={{ marginTop: 4 }}>{selectedJob.location} · {selectedJob.employmentType}</p>
            <form onSubmit={handleApply} style={{ marginTop: 16 }}>
              <label style={{ fontSize: 13, fontWeight: 600 }}>Select Resume</label>
              <select
                className="input-field"
                style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                value={applyForm.resumeId}
                onChange={(e) => setApplyForm({ ...applyForm, resumeId: e.target.value })}
              >
                <option value="">-- Select a resume --</option>
                {resumes.map((r) => (
                  <option key={r.id} value={r.id}>{r.fileName}</option>
                ))}
              </select>
              {resumes.length === 0 && (
                <p className="muted" style={{ fontSize: 13, marginTop: 8 }}>
                  No resumes found. Please upload a resume first.
                </p>
              )}
              {applyError && <p style={{ color: 'var(--danger)', fontSize: 13, marginTop: 8 }}>{applyError}</p>}
              {applySuccess && <p style={{ color: 'var(--success)', fontSize: 13, marginTop: 8 }}>{applySuccess}</p>}
              <div style={{ display: 'flex', gap: 10, marginTop: 16 }}>
                <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>
                  Submit Application
                </button>
                <button type="button" className="btn btn-outline" onClick={() => setShowApplyModal(false)}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {showResumeModal && (
        <div className="modal-overlay" onClick={() => setShowResumeModal(false)}>
          <div className="modal card" onClick={(e) => e.stopPropagation()}>
            <h3>{editingResume ? 'Edit Resume' : 'Upload Resume'}</h3>
            <form onSubmit={handleSaveResume} style={{ marginTop: 16 }}>
              <label style={{ fontSize: 13, fontWeight: 600 }}>File Name</label>
              <input
                type="text"
                className="input-field"
                style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                placeholder="e.g. resume.pdf"
                value={resumeForm.fileName}
                onChange={(e) => setResumeForm({ ...resumeForm, fileName: e.target.value })}
              />
              <label style={{ fontSize: 13, fontWeight: 600, marginTop: 16 }}>Resume URL</label>
              <input
                type="text"
                className="input-field"
                style={{ width: '100%', marginTop: 6, padding: '10px 14px' }}
                placeholder="https://example.com/resume.pdf"
                value={resumeForm.resumeUrl}
                onChange={(e) => setResumeForm({ ...resumeForm, resumeUrl: e.target.value })}
              />
              <label style={{ display: 'flex', alignItems: 'center', gap: 8, marginTop: 16, fontSize: 14 }}>
                <input
                  type="checkbox"
                  checked={resumeForm.isDefault}
                  onChange={(e) => setResumeForm({ ...resumeForm, isDefault: e.target.checked })}
                />
                Set as default resume
              </label>
              {resumeMsg && (
                <p style={{ color: resumeMsg.includes('success') ? 'var(--success)' : 'var(--danger)', fontSize: 13, marginTop: 12 }}>
                  {resumeMsg}
                </p>
              )}
              <div style={{ display: 'flex', gap: 10, marginTop: 16 }}>
                <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>
                  {editingResume ? 'Save Changes' : 'Upload'}
                </button>
                <button type="button" className="btn btn-outline" onClick={() => setShowResumeModal(false)}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
