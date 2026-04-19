import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8081',
  headers: {
    'Content-Type': 'application/json'
  }
})

// Add JWT token to every request if available
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// --- Auth ---
export function login(username, password) {
  return api.post('/auth/login', { username, password })
}

export function registerUser(data) {
  return api.post('/users', data)
}

// --- Entities ---
export function getEntities() {
  return api.get('/entities')
}

export function createEntity(data) {
  return api.post('/entities', data)
}

export function updateEntity(id, data) {
  return api.put(`/entities/${id}`, data)
}

export function deleteEntity(id) {
  return api.delete(`/entities/${id}`)
}

// --- Groups ---
export function getGroups() {
  return api.get('/groups')
}

export function createGroup(data) {
  return api.post('/groups', data)
}

// --- Reference Data ---
export function getCategories() {
  return api.get('/categories')
}

export function getAffiliations() {
  return api.get('/affiliations')
}

export default api
