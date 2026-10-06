import { api } from './client'
import { createCrud } from './crud'

export const jobsApi = createCrud('/jobs')

export const companiesApi = createCrud('/companies')

export const employeesApi = createCrud('/employees', {
  idKey: 'employeeId',
  getAll: ({ department } = {}) =>
    department
      ? api.get(`/employees/department?department=${encodeURIComponent(department)}`)
      : api.get('/employees'),
})

export const applicationsApi = createCrud('/applications')

export const statusHistoriesApi = createCrud('/application-status-histories')

export const educationsApi = createCrud('/educations')

export const experiencesApi = createCrud('/experiences')

export const interviewsApi = createCrud('/interviews')

export const profilesApi = createCrud('/job-seeker-profiles')

export const notificationsApi = createCrud('/notifications')

export const resumesApi = createCrud('/resumes')

export const savedJobsApi = createCrud('/saved-jobs')

export const skillsApi = createCrud('/skills')
