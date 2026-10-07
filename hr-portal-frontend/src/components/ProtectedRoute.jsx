import { Navigate } from 'react-router-dom'
import { getUser } from '../auth'
import CandidateDashboard from '../pages/CandidateDashboard'
import HRDashboard from '../pages/HRDashboard'
import AdminDashboard from '../pages/AdminDashboard'

export default function ProtectedRoute({ children }) {
  const user = getUser()
  if (!user) return <Navigate to="/login" replace />

  const role = user.role || 'CANDIDATE'

  if (role === 'ADMIN') return <AdminDashboard />
  if (role === 'HR') return <HRDashboard />
  return <CandidateDashboard />
}
