<template>
  <div class="form-card">
    <h2>Create Entity</h2>

    <div v-if="successMsg" class="msg success">{{ successMsg }}</div>
    <div v-if="errorMsg" class="msg error">{{ errorMsg }}</div>

    <form @submit.prevent="handleSubmit">
      <!-- Name -->
      <label>Name <span class="req">*</span></label>
      <input v-model="form.name" type="text" placeholder="Entity name" />

      <!-- Level -->
      <label>Level <span class="req">*</span></label>
      <select v-model="form.level">
        <option :value="null" disabled>-- Select Level --</option>
        <option v-for="l in 5" :key="l" :value="l">Level {{ l }}</option>
      </select>

      <!-- Parent -->
      <label>Parent <span class="req" v-if="form.level && form.level > 1">*</span></label>
      <select v-model="form.parentId">
        <option :value="null">-- No Parent (Level 1) --</option>
        <option v-for="e in flatEntities" :key="e.id" :value="e.id">
          {{ e.name }} ({{ e.fullCode }})
        </option>
      </select>

      <!-- Group (optional) -->
      <label>Group <span class="opt">(optional)</span></label>
      <select v-model="form.groupId">
        <option :value="null">-- No Group --</option>
        <option v-for="g in groups" :key="g.id" :value="g.id">
          {{ g.name }} ({{ g.levelCode }})
        </option>
      </select>

      <!-- Category -->
      <label>Category <span class="req">*</span></label>
      <select v-model="form.categoryId" :disabled="form.groupId != null">
        <option :value="null" disabled>-- Select Category --</option>
        <option v-for="c in categories" :key="c.id" :value="c.id">
          {{ c.name }} (Level {{ c.level }})
        </option>
      </select>
      <small v-if="form.groupId != null" class="hint">Category is set by the selected group.</small>

      <!-- Affiliation -->
      <label>Affiliation <span class="req">*</span></label>
      <select v-model="form.affiliationId">
        <option :value="null" disabled>-- Select Affiliation --</option>
        <option v-for="a in affiliations" :key="a.id" :value="a.id">
          {{ a.name }}
        </option>
      </select>

      <button type="submit" :disabled="submitting">
        {{ submitting ? 'Creating...' : 'Create Entity' }}
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { createEntity } from '../services/api.js'

const props = defineProps({
  flatEntities: { type: Array, default: () => [] },
  categories: { type: Array, default: () => [] },
  affiliations: { type: Array, default: () => [] },
  groups: { type: Array, default: () => [] }
})

const emit = defineEmits(['entity-created'])

const form = ref({
  name: '',
  level: null,
  parentId: null,
  categoryId: null,
  affiliationId: null,
  groupId: null
})

const submitting = ref(false)
const successMsg = ref('')
const errorMsg = ref('')

// When group is selected, override categoryId from the group's category
watch(() => form.value.groupId, (newGroupId) => {
  if (newGroupId != null) {
    const group = props.groups.find(g => g.id === newGroupId)
    if (group) {
      form.value.categoryId = group.categoryId
    }
  }
})

function validate() {
  if (!form.value.name || !form.value.name.trim()) return 'Name is required.'
  if (!form.value.level) return 'Level is required.'
  if (form.value.level > 1 && form.value.parentId == null) return 'Parent is required for levels 2-5.'
  if (form.value.categoryId == null) return 'Category is required.'
  if (form.value.affiliationId == null) return 'Affiliation is required.'
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
      level: form.value.level,
      parentId: form.value.parentId,
      categoryId: form.value.categoryId,
      affiliationId: form.value.affiliationId,
      groupId: form.value.groupId
    }
    await createEntity(payload)
    successMsg.value = 'Entity created successfully!'
    // Reset form
    form.value = { name: '', level: null, parentId: null, categoryId: null, affiliationId: null, groupId: null }
    emit('entity-created')
  } catch (e) {
    errorMsg.value = e.response?.data?.error || e.message || 'Failed to create entity.'
  } finally {
    submitting.value = false
  }
}
</script>
