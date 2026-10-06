import { api } from './client'

export function createCrud(path, { idKey = 'id', getAll } = {}) {
  return {
    idKey,
    base: path,
    getAll: getAll || (() => api.get(path)),
    getById: (id) => api.get(`${path}/${id}`),
    create: (body) => api.post(path, body),
    update: (id, body) => api.put(`${path}/${id}`, body),
    remove: (id) => api.delete(`${path}/${id}`),
  }
}
