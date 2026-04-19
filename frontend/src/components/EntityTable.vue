<template>
  <div class="table-card">
    <div class="table-header">
      <h2>Entities</h2>
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="Search by name..." 
        class="search-input" 
      />
    </div>

    <p v-if="filteredEntities.length === 0" class="empty">No entities found.</p>

    <table v-else>
      <thead>
        <tr>
          <th>Name</th>
          <th>Full Code</th>
          <th>Level</th>
          <th>Level Code</th>
          <th>Category</th>
          <th>Affiliation</th>
          <th>Group</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="entity in filteredEntities" :key="entity.id">
          <td>{{ entity.name }}</td>
          <td class="code">{{ entity.fullCode }}</td>
          <td class="center">{{ entity.level }}</td>
          <td class="center">{{ entity.levelCode || '-' }}</td>
          <td>{{ entity.categoryName || '-' }}</td>
          <td>{{ entity.affiliationName || '-' }}</td>
          <td>{{ getGroupName(entity.groupId) }}</td>
          <td class="actions">
            <button class="btn-edit" @click="editEntity(entity)">Edit</button>
            <button class="btn-delete" @click="handleDelete(entity.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <EditEntityModal
      :show="showEditModal"
      :entity="editingEntity"
      :categories="categories"
      :affiliations="affiliations"
      :groups="groups"
      @close="showEditModal = false"
      @save="handleSaveEdit"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { deleteEntity, updateEntity } from '../services/api.js'
import EditEntityModal from './EditEntityModal.vue'

const emit = defineEmits(['entity-deleted', 'entity-updated'])

const props = defineProps({
  entities: { type: Array, default: () => [] },
  groups: { type: Array, default: () => [] },
  categories: { type: Array, default: () => [] },
  affiliations: { type: Array, default: () => [] }
})

const editingEntity = ref(null)
const showEditModal = ref(false)
const searchQuery = ref('')

const filteredEntities = computed(() => {
  if (!searchQuery.value) return props.entities
  const lowerQuery = searchQuery.value.toLowerCase()
  return props.entities.filter(entity => 
    entity.name.toLowerCase().includes(lowerQuery)
  )
})

function getGroupName(groupId) {
  if (groupId == null) return '-'
  const group = props.groups.find(g => g.id === groupId)
  return group ? group.name : '-'
}

function editEntity(entity) {
  editingEntity.value = entity
  showEditModal.value = true
}

async function handleDelete(id) {
  if (window.confirm('Are you sure you want to delete this entity?')) {
    try {
      await deleteEntity(id)
      emit('entity-deleted')
    } catch (e) {
      alert('Failed to delete entity: ' + (e.response?.data?.error || e.message))
    }
  }
}

async function handleSaveEdit({ id, data }) {
  try {
    await updateEntity(id, data)
    showEditModal.value = false
    emit('entity-updated')
  } catch (e) {
    alert('Failed to update entity: ' + (e.response?.data?.error || e.message))
  }
}
</script>

<style scoped>
.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  border-bottom: 1px solid #eee;
  padding-bottom: 8px;
}
.table-header h2 {
  margin: 0;
  border-bottom: none;
  padding-bottom: 0;
}
.search-input {
  width: 250px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 0.9rem;
}
.actions {
  display: flex;
  gap: 8px;
}
.btn-edit {
  background: #f1c40f;
  color: #fff;
  border: none;
  padding: 4px 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.8rem;
}
.btn-edit:hover { background: #f39c12; }

.btn-delete {
  background: #e74c3c;
  color: #fff;
  border: none;
  padding: 4px 8px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.8rem;
}
.btn-delete:hover { background: #c0392b; }
</style>
