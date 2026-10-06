import { useCallback, useEffect, useMemo, useState } from 'react'
import Icon from './Icon'
import './ResourcePage.css'

const TONES = {
  OPEN: 'ok',
  SELECTED: 'ok',
  COMPLETED: 'ok',
  APPROVED: 'ok',
  ACTIVE: 'ok',
  PAUSED: 'warn',
  RESCHEDULED: 'warn',
  UNDER_REVIEW: 'warn',
  PENDING: 'warn',
  SCHEDULED: 'info',
  APPLIED: 'info',
  SHORTLISTED: 'info',
  INTERVIEW_SCHEDULED: 'info',
  CLOSED: 'danger',
  REJECTED: 'danger',
  CANCELLED: 'danger',
  EXPIRED: 'danger',
  NO_SHOW: 'danger',
  WITHDRAWN: 'danger',
}

function formatValue(value) {
  if (value === null || value === undefined || value === '') return '—'
  if (Array.isArray(value)) return value.length ? value.join(', ') : '—'
  if (typeof value === 'boolean') return value ? 'Yes' : 'No'
  if (typeof value === 'string' && /^\d{4}-\d{2}-\d{2}T/.test(value)) {
    return value.replace('T', ' ').replace(/:\d{2}(\.\d+)?$/, '')
  }
  if (typeof value === 'string' && /^[A-Z][A-Z0-9_]*$/.test(value)) {
    return value.replace(/_/g, ' ')
  }
  return String(value)
}

function isPillValue(value) {
  return typeof value === 'string' && TONES[value]
}

function emptyForm(fields) {
  const form = {}
  fields.forEach((f) => {
    form[f.name] = f.type === 'checkbox' ? false : ''
  })
  return form
}

function formFromRow(row, fields) {
  const form = {}
  fields.forEach((f) => {
    const value = row[f.sourceKey || f.name]
    if (f.type === 'checkbox') form[f.name] = Boolean(value)
    else if (f.type === 'numberList') form[f.name] = Array.isArray(value) ? value.join(', ') : ''
    else if (value === null || value === undefined) form[f.name] = ''
    else if (f.type === 'datetime-local' && typeof value === 'string') form[f.name] = value.slice(0, 16)
    else form[f.name] = String(value)
  })
  return form
}

function buildPayload(form, fields) {
  const payload = {}
  fields.forEach((f) => {
    const value = form[f.name]
    if (f.type === 'checkbox') {
      payload[f.name] = Boolean(value)
      return
    }
    if (value === '' || value === null || value === undefined) {
      if (f.type === 'numberList') payload[f.name] = []
      return
    }
    if (f.type === 'number') payload[f.name] = Number(value)
    else if (f.type === 'numberList') {
      payload[f.name] = String(value)
        .split(',')
        .map((part) => part.trim())
        .filter(Boolean)
        .map(Number)
    } else if (f.type === 'datetime-local') payload[f.name] = `${value}:00`
    else payload[f.name] = value
  })
  return payload
}

function validate(form, fields) {
  for (const f of fields) {
    const value = form[f.name]
    if (f.required && (value === '' || value === null || value === undefined)) {
      return `${f.label} is required.`
    }
    if (f.type === 'number' && value !== '' && Number.isNaN(Number(value))) {
      return `${f.label} must be a number.`
    }
  }
  return ''
}

export default function ResourcePage({
  title,
  subtitle,
  singular,
  service,
  idKey = 'id',
  columns,
  fields,
  searchKeys,
  filters = [],
  addLabel,
}) {
  const [rows, setRows] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [notice, setNotice] = useState('')
  const [query, setQuery] = useState('')
  const [draftFilters, setDraftFilters] = useState({})
  const [appliedFilters, setAppliedFilters] = useState({})
  const [modal, setModal] = useState(null)
  const [form, setForm] = useState({})
  const [formError, setFormError] = useState('')
  const [saving, setSaving] = useState(false)
  const [deletingId, setDeletingId] = useState(null)

  const load = useCallback(async () => {
    try {
      const data = await service.getAll(appliedFilters)
      setRows(Array.isArray(data) ? data : [])
      setError('')
    } catch (err) {
      setRows([])
      setError(err.message || 'Failed to load data.')
    } finally {
      setLoading(false)
    }
  }, [service, appliedFilters])

  useEffect(() => {
    // Initial/filtered data fetch: every setState inside load() happens after
    // the request resolves (async continuation), never synchronously.
    // eslint-disable-next-line react-hooks/set-state-in-effect
    load()
  }, [load])

  useEffect(() => {
    if (!notice) return undefined
    const timer = setTimeout(() => setNotice(''), 4000)
    return () => clearTimeout(timer)
  }, [notice])

  const keys = useMemo(() => searchKeys || columns.map((c) => c.key), [searchKeys, columns])

  const visibleRows = useMemo(() => {
    const q = query.trim().toLowerCase()
    if (!q || keys.length === 0) return rows
    return rows.filter((row) =>
      keys.some((key) => String(row[key] ?? '').toLowerCase().includes(q)),
    )
  }, [rows, query, keys])

  const openCreate = () => {
    setForm(emptyForm(fields))
    setFormError('')
    setModal({ mode: 'create' })
  }

  const openEdit = (row) => {
    setForm(formFromRow(row, fields))
    setFormError('')
    setModal({ mode: 'edit', id: row[idKey] })
  }

  const closeModal = () => {
    setModal(null)
    setFormError('')
  }

  const handleFilterChange = (key, value) => {
    setDraftFilters((prev) => ({ ...prev, [key]: value }))
  }

  const applyFilters = () => {
    setLoading(true)
    setAppliedFilters({ ...draftFilters })
  }

  const refresh = () => {
    setLoading(true)
    load()
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const problem = validate(form, fields)
    if (problem) {
      setFormError(problem)
      return
    }

    setSaving(true)
    setFormError('')
    try {
      const payload = buildPayload(form, fields)
      const result =
        modal.mode === 'create'
          ? await service.create(payload)
          : await service.update(modal.id, payload)
      setNotice(result?.message || (modal.mode === 'create' ? 'Created successfully.' : 'Updated successfully.'))
      closeModal()
      setLoading(true)
      await load()
    } catch (err) {
      setFormError(err.message || 'Something went wrong.')
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async (row) => {
    const label = singular || title
    if (!window.confirm(`Delete this ${label.toLowerCase()}? This cannot be undone.`)) return
    setDeletingId(row[idKey])
    setError('')
    try {
      const result = await service.remove(row[idKey])
      setNotice(result?.message || 'Deleted successfully.')
      setLoading(true)
      await load()
    } catch (err) {
      setError(err.message || 'Failed to delete.')
    } finally {
      setDeletingId(null)
    }
  }

  const renderField = (field) => {
    const value = form[field.name]
    const common = {
      id: `res-${field.name}`,
      name: field.name,
      value: field.type === 'checkbox' ? undefined : value,
      placeholder: field.placeholder || '',
      required: field.required,
      disabled: saving,
    }

    if (field.type === 'select') {
      return (
        <select
          {...common}
          value={value}
          onChange={(e) => setForm({ ...form, [field.name]: e.target.value })}
        >
          <option value="">—</option>
          {field.options.map((option) => (
            <option key={option} value={option}>
              {option.replace(/_/g, ' ')}
            </option>
          ))}
        </select>
      )
    }

    if (field.type === 'textarea') {
      return (
        <textarea
          {...common}
          rows={4}
          value={value}
          onChange={(e) => setForm({ ...form, [field.name]: e.target.value })}
        />
      )
    }

    if (field.type === 'checkbox') {
      return (
        <label className="res-check">
          <input
            type="checkbox"
            name={field.name}
            checked={Boolean(value)}
            disabled={saving}
            onChange={(e) => setForm({ ...form, [field.name]: e.target.checked })}
          />
          {field.checkboxLabel || field.label}
        </label>
      )
    }

    const inputType = field.type === 'number' ? 'number' : field.type === 'date' ? 'date' : field.type === 'datetime-local' ? 'datetime-local' : 'text'
    return (
      <input
        {...common}
        type={inputType}
        step={field.type === 'number' ? 'any' : undefined}
        value={value}
        onChange={(e) => setForm({ ...form, [field.name]: e.target.value })}
      />
    )
  }

  return (
    <>
      <div className="dash-head">
        <div>
          <h1>{title}</h1>
          <p className="dash-sub">{subtitle}</p>
        </div>
        <div className="dash-head-actions">
          <button type="button" className="btn btn-outline" onClick={refresh} disabled={loading}>
            <Icon name="refresh" size={16} /> Refresh
          </button>
          <button type="button" className="btn btn-primary" onClick={openCreate}>
            <Icon name="plus" size={16} /> {addLabel || `Add ${singular || title}`}
          </button>
        </div>
      </div>

      {notice && (
        <div className="res-banner res-banner-ok">
          <Icon name="check" size={16} />
          <span>{notice}</span>
          <button type="button" onClick={() => setNotice('')} aria-label="Dismiss">
            <Icon name="x" size={14} />
          </button>
        </div>
      )}

      <div className="card res-card">
        <div className="res-toolbar">
          <div className="res-search">
            <Icon name="search" size={16} />
            <input
              type="search"
              placeholder={`Search ${title.toLowerCase()}…`}
              value={query}
              onChange={(e) => setQuery(e.target.value)}
            />
          </div>

          {filters.map((filter) => (
            <div className="res-filter" key={filter.key}>
              <input
                type="text"
                placeholder={filter.label}
                value={draftFilters[filter.key] || ''}
                onChange={(e) => handleFilterChange(filter.key, e.target.value)}
                onKeyDown={(e) => e.key === 'Enter' && applyFilters()}
              />
            </div>
          ))}

          {filters.length > 0 && (
            <button type="button" className="btn btn-outline res-apply" onClick={applyFilters}>
              Apply
            </button>
          )}

          <span className="res-count">
            {visibleRows.length} {visibleRows.length === 1 ? 'record' : 'records'}
          </span>
        </div>

        {error && (
          <div className="res-banner res-banner-danger">
            <Icon name="bell" size={16} />
            <span>{error}</span>
            <button type="button" onClick={() => setError('')} aria-label="Dismiss">
              <Icon name="x" size={14} />
            </button>
          </div>
        )}

        <div className="res-table-wrap">
          <table className="dash-table">
            <thead>
              <tr>
                {columns.map((column) => (
                  <th key={column.key}>{column.label}</th>
                ))}
                <th className="res-actions-head">Actions</th>
              </tr>
            </thead>
            <tbody>
              {visibleRows.map((row) => (
                <tr key={row[idKey]}>
                  {columns.map((column) => {
                    const raw = row[column.key]
                    return (
                      <td key={column.key} className={column.muted ? 'muted' : undefined}>
                        {column.render ? (
                          column.render(raw, row)
                        ) : isPillValue(raw) && column.pill !== false ? (
                          <span className={`pill pill-${TONES[raw] || 'tag'}`}>
                            {raw.replace(/_/g, ' ')}
                          </span>
                        ) : (
                          formatValue(raw)
                        )}
                      </td>
                    )
                  })}
                  <td className="res-actions">
                    <button
                      type="button"
                      className="res-icon-btn"
                      title="Edit"
                      onClick={() => openEdit(row)}
                      disabled={saving}
                    >
                      <Icon name="edit" size={15} />
                    </button>
                    <button
                      type="button"
                      className="res-icon-btn res-icon-btn-danger"
                      title="Delete"
                      onClick={() => handleDelete(row)}
                      disabled={deletingId === row[idKey]}
                    >
                      <Icon name={deletingId === row[idKey] ? 'refresh' : 'trash'} size={15} />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {loading && (
            <div className="res-overlay">
              <span className="res-spinner" />
              <p>Loading…</p>
            </div>
          )}

          {!loading && visibleRows.length === 0 && (
            <div className="res-empty">
              <Icon name="clipboard" size={26} />
              <p>{query ? 'No records match your search.' : `No ${title.toLowerCase()} yet.`}</p>
              {!query && (
                <button type="button" className="btn btn-primary" onClick={openCreate}>
                  <Icon name="plus" size={16} /> Create the first one
                </button>
              )}
            </div>
          )}
        </div>
      </div>

      {modal && (
        <div
          className="res-overlay res-modal-overlay"
          onMouseDown={(e) => e.target === e.currentTarget && closeModal()}
        >
          <div className="res-modal" role="dialog" aria-modal="true" aria-label={title}>
            <div className="res-modal-head">
              <div>
                <h2>
                  {modal.mode === 'create' ? 'Add' : 'Edit'} {singular || title}
                </h2>
                <p>
                  {modal.mode === 'create'
                    ? `Create a new ${singular || title.toLowerCase()}.`
                    : `Update ${singular || title.toLowerCase()} #${modal.id}.`}
                </p>
              </div>
              <button type="button" className="res-icon-btn" onClick={closeModal} aria-label="Close">
                <Icon name="x" size={16} />
              </button>
            </div>

            <form onSubmit={handleSubmit}>
              <div className="res-form">
                {fields.map((field) => (
                  <div
                    key={field.name}
                    className={`res-field ${field.type === 'textarea' || field.wide ? 'res-field-wide' : ''} ${
                      field.type === 'checkbox' ? 'res-field-check' : ''
                    }`}
                  >
                    {field.type !== 'checkbox' && (
                      <label htmlFor={`res-${field.name}`}>
                        {field.label}
                        {field.required && <span className="res-req">*</span>}
                      </label>
                    )}
                    {renderField(field)}
                  </div>
                ))}
              </div>

              {formError && (
                <div className="res-banner res-banner-danger">
                  <Icon name="bell" size={16} />
                  <span>{formError}</span>
                </div>
              )}

              <div className="res-modal-foot">
                <button type="button" className="btn btn-ghost" onClick={closeModal} disabled={saving}>
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary" disabled={saving}>
                  {saving ? (
                    <>
                      <span className="res-spinner res-spinner-sm" /> Saving…
                    </>
                  ) : modal.mode === 'create' ? (
                    'Create'
                  ) : (
                    'Save changes'
                  )}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  )
}
