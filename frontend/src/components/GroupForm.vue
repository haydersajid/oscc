<template>
  <div class="form-card">
    <h2>Create Group</h2>

    <div v-if="successMsg" class="msg success">{{ successMsg }}</div>
    <div v-if="errorMsg" class="msg error">{{ errorMsg }}</div>

    <form @submit.prevent="handleSubmit">
      <!-- Name -->
      <label>Name <span class="req">*</span></label>
      <input v-model="form.name" type="text" placeholder="Group name" />

      <!-- Category -->
      <label>Category <span class="req">*</span></label>
      <select v-model="form.categoryId">
        <option :value="null" disabled>-- Select Category --</option>
        <option v-for="c in categories" :key="c.id" :value="c.id">
          {{ c.name }} (Level {{ c.level }})
        </option>
      </select>

      <!-- Level Code -->
      <label>Level Code <span class="req">*</span> <small>(exactly 2 characters)</small></label>
      <input v-model="form.levelCode" type="text" maxlength="2" placeholder="e.g. 01" />

      <button type="submit" :disabled="submitting">
        {{ submitting ? 'Creating...' : 'Create Group' }}
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { createGroup } from '../services/api.js'

defineProps({
  categories: { type: Array, default: () => [] }
})

const emit = defineEmits(['group-created'])

const form = ref({
  name: '',
  categoryId: null,
  levelCode: ''
})

const submitting = ref(false)
const successMsg = ref('')
const errorMsg = ref('')

function validate() {
  if (!form.value.name || !form.value.name.trim()) return 'Name is required.'
  if (form.value.categoryId == null) return 'Category is required.'
  if (!form.value.levelCode || form.value.levelCode.length !== 2) return 'Level Code must be exactly 2 characters.'
  return null
}

async function handleSubmit() {
  successMsg.value = ''
  errorMsg.value = ''

  const err = validate()
  if (err) {
    errorMsg.value = err
    return
  }

  submitting.value = true
  try {
    const payload = {
      name: form.value.name.trim(),
      categoryId: form.value.categoryId,
      levelCode: form.value.levelCode.toUpperCase()
    }
    await createGroup(payload)
    successMsg.value = 'Group created successfully!'
    form.value = { name: '', categoryId: null, levelCode: '' }
    emit('group-created')
  } catch (e) {
    errorMsg.value = e.response?.data?.error || e.message || 'Failed to create group.'
  } finally {
    submitting.value = false
  }
}
</script>
