<template>
  <div class="signup">
    <UserSignup />
    <!--<SearchBar /> -->
  </div>
</template>

<script   >
  

import UserSignup from '@/components/user/UserSignup.vue';

export default  {
  name: 'MainView',
  components: {
    UserSignup
  },
  
}
</script>
<style scoped>
.MainCenter{
  text-align: center;
}
</style>