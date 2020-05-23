<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import userDashboard from './user'
import adminDashboard from './admin'
import anonymousDashboard from './anonymous'

export default {
  name: 'Dashboard',
  components: { adminDashboard, anonymousDashboard, userDashboard },
  data() {
    return {
      currentRole: 'anonymousDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    if (this.roles.includes('ADMIN')) {
      this.currentRole = 'adminDashboard'
    } else if (this.roles.includes('USER')) {
      this.currentRole = 'userDashboard'
    }
  }
}
</script>
