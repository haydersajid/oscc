<template>
  <div id="osc-app">
    <!-- Login Section -->
    <div v-if="!token" class="login-card">
      <h1>OSC System Login</h1>
      <div v-if="loginError" class="msg error">{{ loginError }}</div>
      <form @submit.prevent="handleLogin">
        <label>Username</label>
        <input v-model="loginForm.username" type="text" placeholder="Username" />
        <label>Password</label>
        <input v-model="loginForm.password" type="password" placeholder="Password" />
        <button type="submit" :disabled="loggingIn">
          {{ loggingIn ? 'Logging in...' : 'Login' }}
        </button>
      </form>
    </div>

    <!-- Main Content -->
    <div v-else>
      <header>
        <div style="display: flex; align-items: center; gap: 20px;">
          <h1>OSC System</h1>
          <nav v-if="isAdmin" class="top-nav">
            <button :class="{ active: currentView === 'dashboard' }" @click="currentView = 'dashboard'">Dashboard</button>
            <button :class="{ active: currentView === 'users' }" @click="currentView = 'users'">Manage Users</button>
          </nav>
        </div>
        <button class="logout-btn" @click="handleLogout">Logout</button>
      </header>

      <div class="content">
        <div v-if="currentView === 'dashboard'">
          <div class="forms-row">
            <EntityForm
              :flatEntities="entities"
              :categories="categories"
              :affiliations="affiliations"
              :groups="groups"
              @entity-created="loadEntities"
            />
            <GroupForm
              :categories="categories"
              @group-created="loadGroups"
            />
          </div>

          <EntityTable
            :entities="entities"
            :groups="groups"
            :categories="categories"
            :affiliations="affiliations"
            @entity-updated="loadEntities"
            @entity-deleted="loadEntities"
          />
        </div>

        <div v-if="isAdmin && currentView === 'users'">
          <div class="form-card" style="margin-bottom: 28px; max-width: 800px;">
          <h2>Create User</h2>
          <div v-if="registerError" class="msg error">{{ registerError }}</div>
          <div v-if="registerSuccess" class="msg success">{{ registerSuccess }}</div>
          <form @submit.prevent="handleRegister">
            <div style="display: flex; gap: 16px;">
              <div style="flex: 1;">
                <label>Username <span class="req">*</span></label>
                <input v-model="registerForm.username" type="text" placeholder="Username" required />
              </div>
              <div style="flex: 1;">
                <label>Password <span class="req">*</span></label>
                <input v-model="registerForm.password" type="password" placeholder="Password" required />
              </div>
            </div>

            <div style="display: flex; gap: 16px;">
              <div style="flex: 1;">
                <label>Role <span class="req">*</span></label>
                <select v-model="registerForm.role" required>
                  <option value="MINISTRY_ADMIN">MINISTRY_ADMIN</option>
                  <option value="UNIVERSITY_ADMIN">UNIVERSITY_ADMIN</option>
                  <option value="COLLEGE_ADMIN">COLLEGE_ADMIN</option>
                </select>
              </div>

              <div style="flex: 1;" v-if="registerForm.role !== 'MINISTRY_ADMIN'">
                <label>Scope Entity <span class="req">*</span></label>
                <select v-model="registerForm.scopeEntityId" :required="registerForm.role !== 'MINISTRY_ADMIN'">
                  <option :value="null" disabled>Select an entity...</option>
                  <option v-for="ent in entities" :key="ent.id" :value="ent.id">
                    {{ ent.name }} ({{ ent.fullCode }})
                  </option>
                </select>
              </div>
            </div>

            <button type="submit" :disabled="registering" style="max-width: 200px; margin-top: 16px;">
              {{ registering ? 'Creating...' : 'Register User' }}
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue'
import { login, registerUser, getEntities, getGroups, getCategories, getAffiliations } from './services/api.js'
import EntityForm from './components/EntityForm.vue'
import GroupForm from './components/GroupForm.vue'
import EntityTable from './components/EntityTable.vue'

const token = ref(localStorage.getItem('token') || '')
const currentView = ref('dashboard')

const isAdmin = computed(() => {
  if (!token.value) return false;
  try {
    const payload = JSON.parse(atob(token.value.split('.')[1]));
    return payload.role === 'MINISTRY_ADMIN';
  } catch(e) {
    return false;
  }
})

const loginForm = ref({ username: '', password: '' })
const loginError = ref('')
const loggingIn = ref(false)

const registerForm = ref({ username: '', password: '', role: 'UNIVERSITY_ADMIN', scopeEntityId: null })
const registerError = ref('')
const registerSuccess = ref('')
const registering = ref(false)

const entities = ref([])
const categories = ref([])
const affiliations = ref([])
const groups = ref([])

async function handleLogin() {
  loginError.value = ''
  loggingIn.value = true
  try {
    const res = await login(loginForm.value.username, loginForm.value.password)
    token.value = res.data.token
    localStorage.setItem('token', res.data.token)
    loginForm.value = { username: '', password: '' }
    loadAll()
  } catch (e) {
    loginError.value = e.response?.data?.error || 'Login failed.'
  } finally {
    loggingIn.value = false
  }
}

async function handleRegister() {
  registerError.value = ''
  registerSuccess.value = ''
  registering.value = true
  try {
    const payload = {
      username: registerForm.value.username,
      password: registerForm.value.password,
      role: registerForm.value.role,
      scopeEntityId: registerForm.value.role === 'MINISTRY_ADMIN' ? null : registerForm.value.scopeEntityId
    }
    await registerUser(payload)
    registerSuccess.value = 'User created successfully.'
    registerForm.value = { username: '', password: '', role: 'UNIVERSITY_ADMIN', scopeEntityId: null }
  } catch (e) {
    if (e.response?.status === 401 || e.response?.status === 403) {
      registerError.value = 'Access Denied: Not authorized to create users.'
    } else {
      registerError.value = e.response?.data?.error || 'Registration failed.'
    }
  } finally {
    registering.value = false
  }
}

function handleLogout() {
  token.value = ''
  localStorage.removeItem('token')
  entities.value = []
  categories.value = []
  affiliations.value = []
  groups.value = []
}

async function loadEntities() {
  try {
    const res = await getEntities()
    entities.value = res.data
  } catch (e) {
    console.error('Failed to load entities:', e)
  }
}

async function loadGroups() {
  try {
    const res = await getGroups()
    groups.value = res.data
  } catch (e) {
    console.error('Failed to load groups:', e)
  }
}

async function loadAll() {
  try {
    const [entRes, catRes, affRes, grpRes] = await Promise.all([
      getEntities(),
      getCategories(),
      getAffiliations(),
      getGroups()
    ])
    entities.value = entRes.data
    categories.value = catRes.data
    affiliations.value = affRes.data
    groups.value = grpRes.data
  } catch (e) {
    console.error('Failed to load data:', e)
  }
}

onMounted(() => {
  if (token.value) {
    loadAll()
  }
})
</script>

<style>
/* ===== Reset & Base ===== */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background: #f0f2f5;
  color: #333;
  min-height: 100vh;
}

#osc-app {
  max-width: 1100px;
  margin: 0 auto;
  padding: 24px 16px;
}

/* ===== Header ===== */
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 28px;
  padding-bottom: 16px;
  border-bottom: 2px solid #dde1e6;
}

header h1 {
  font-size: 1.4rem;
  color: #1a3a5c;
  margin: 0;
}

.top-nav {
  display: flex;
  gap: 8px;
  background: #e9ecef;
  padding: 4px;
  border-radius: 8px;
}
.top-nav button {
  background: transparent;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  color: #555;
  transition: all 0.2s;
}
.top-nav button.active {
  background: #fff;
  color: #1a3a5c;
  font-weight: 600;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.logout-btn {
  background: #e74c3c;
  color: #fff;
  border: none;
  padding: 8px 18px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.85rem;
}
.logout-btn:hover {
  background: #c0392b;
}

/* ===== Login ===== */
.login-card {
  max-width: 380px;
  margin: 80px auto;
  background: #fff;
  padding: 32px;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}
.login-card h1 {
  font-size: 1.3rem;
  margin-bottom: 20px;
  color: #1a3a5c;
  text-align: center;
}
.auth-toggle {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 2px solid #eee;
}
.auth-toggle button {
  flex: 1;
  background: none;
  border: none;
  padding: 10px;
  font-size: 1rem;
  color: #777;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  margin-bottom: -2px;
  transition: all 0.2s;
}
.auth-toggle button.active {
  color: #1a3a5c;
  font-weight: 600;
  border-bottom-color: #1a3a5c;
}

/* ===== Forms ===== */
.forms-row {
  display: flex;
  gap: 20px;
  margin-bottom: 28px;
}
.forms-row > * {
  flex: 1;
}

.form-card {
  background: #fff;
  padding: 24px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.07);
}
.form-card h2 {
  font-size: 1.1rem;
  color: #1a3a5c;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 1px solid #eee;
}

label {
  display: block;
  font-size: 0.85rem;
  font-weight: 600;
  margin-bottom: 4px;
  margin-top: 12px;
  color: #444;
}
label .req { color: #e74c3c; }
label .opt { color: #999; font-weight: 400; }

input[type="text"],
input[type="password"],
select {
  width: 100%;
  padding: 8px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 0.9rem;
  background: #fafafa;
  transition: border-color 0.2s;
}
input:focus, select:focus {
  outline: none;
  border-color: #3498db;
  background: #fff;
}
select:disabled {
  background: #e9ecef;
  color: #999;
}

.hint {
  display: block;
  font-size: 0.75rem;
  color: #888;
  margin-top: 2px;
}

button[type="submit"] {
  display: block;
  width: 100%;
  margin-top: 18px;
  padding: 10px;
  background: #2980b9;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.2s;
}
button[type="submit"]:hover {
  background: #1f6da0;
}
button[type="submit"]:disabled {
  background: #93b8d4;
  cursor: not-allowed;
}

/* ===== Messages ===== */
.msg {
  padding: 10px 14px;
  border-radius: 6px;
  font-size: 0.85rem;
  margin-bottom: 12px;
}
.msg.success {
  background: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}
.msg.error {
  background: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}

/* ===== Table ===== */
.table-card {
  background: #fff;
  padding: 24px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.07);
}
.table-card h2 {
  font-size: 1.1rem;
  color: #1a3a5c;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 1px solid #eee;
}

.empty {
  color: #999;
  font-style: italic;
  padding: 20px 0;
  text-align: center;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.85rem;
}
thead th {
  background: #1a3a5c;
  color: #fff;
  padding: 10px 12px;
  text-align: left;
  font-weight: 600;
}
tbody tr:nth-child(even) {
  background: #f7f9fb;
}
tbody td {
  padding: 9px 12px;
  border-bottom: 1px solid #eee;
}
td.code {
  font-family: 'Courier New', monospace;
  font-size: 0.82rem;
  color: #2c3e50;
}
td.center {
  text-align: center;
}

/* ===== Responsive ===== */
@media (max-width: 768px) {
  .forms-row {
    flex-direction: column;
  }
}
</style>
