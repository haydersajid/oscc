<template>
  <div class="modal-overlay" v-if="show">
    <div class="modal-content">
      <h2>Edit Entity</h2>
      <form @submit.prevent="submitForm">
        
        <label>Name <span class="req">*</span></label>
        <input v-model="form.name" type="text" required />

        <!-- For simplicity, allowing to update Category, Affiliation, Group -->
        <label>Category <span class="opt">(Optional)</span></label>
        <select v-model="form.categoryId">
          <option :value="null">No Category</option>
          <option v-for="cat in categories" :key="cat.id" :value="cat.id">
            {{ cat.name }} ({{ cat.code }})
          </option>
        </select>

        <label>Affiliation <span class="opt">(Optional)</span></label>
        <select v-model="form.affiliationId">
          <option :value="null">No Affiliation</option>
          <option v-for="aff in affiliations" :key="aff.id" :value="aff.id">
             {{ aff.name }} ({{ aff.code }})
          </option>
        </select>

        <label>Group <span class="opt">(Optional)</span></label>
        <select v-model="form.groupId">
          <option :value="null">No Group</option>
          <option v-for="grp in groups" :key="grp.id" :value="grp.id">
            {{ grp.name }} ({{ grp.code }})
          </option>
        </select>

        <div class="modal-actions">
          <button type="button" class="btn-cancel" @click="close">Cancel</button>
          <button type="submit" class="btn-save" :disabled="saving">
            {{ saving ? 'Saving...' : 'Save' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  show: { type: Boolean, default: false },
  entity: { type: Object, default: null },
  categories: { type: Array, default: () => [] },
  affiliations: { type: Array, default: () => [] },
  groups: { type: Array, default: () => [] }
})

const emit = defineEmits(['close', 'save'])

const form = ref({
  name: '',
  categoryId: null,
  affiliationId: null,
  groupId: null
})

const saving = ref(false)

watch(() => props.entity, (newEntity) => {
  if (newEntity) {
    form.value.name = newEntity.name || ''
    form.value.categoryId = newEntity.categoryId || null
    form.value.affiliationId = newEntity.affiliationId || null
    form.value.groupId = newEntity.groupId || null
  }
}, { immediate: true })

function close() {
  emit('close')
}

function submitForm() {
  saving.value = true
  const updatedData = {
    name: form.value.name,
    categoryId: form.value.categoryId,
    affiliationId: form.value.affiliationId,
    groupId: form.value.groupId,
    // Parent ID and Level are typically kept unchanged in simple edits unless logic allows
    parentId: props.entity.parentId, 
    level: props.entity.level
  }
  emit('save', { id: props.entity.id, data: updatedData })
  saving.value = false
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}
.modal-content {
  background: #fff;
  padding: 24px;
  border-radius: 8px;
  width: 400px;
  max-width: 90%;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}
.modal-content h2 {
  margin-bottom: 16px;
  font-size: 1.2rem;
  color: #1a3a5c;
  border-bottom: 1px solid #ebebeb;
  padding-bottom: 8px;
}
.modal-actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
.btn-cancel {
  background: #e0e0e0;
  color: #333;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.btn-cancel:hover { background: #d0d0d0; }
.btn-save {
  background: #3498db;
  color: #fff;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.btn-save:hover { background: #2980b9; }
.btn-save:disabled { background: #95a5a6; cursor: not-allowed; }
</style>
