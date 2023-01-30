<template>
    <div class="signup">
      <UserSignup />
      <!--<SearchBar /> -->
    </div>
  </template>
  
  <script lang="ts">
  import {defineComponent} from 'vue';

  import UserSignup from '@/components/user/UserSignup.vue';
  
  export default defineComponent({
    name: 'MainView',
    components: {
      UserSignup
    },
    
  }) 
  </script>
  <style scoped>
  .MainCenter{
    text-align: center;
  }
  </style>