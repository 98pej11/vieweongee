<template>
    <div class="signup">
      <MyHeader></MyHeader>
      <UserSignup />
      <!--<SearchBar /> -->
    </div>
  </template>
  
  <script lang="ts">
  import {defineComponent} from 'vue';

  import UserSignup from '@/components/user/UserSignup.vue';
  import MyHeader from '@/components/common/MyHeader.vue';
  
  export default defineComponent({
    name: 'MainView',
    components: {
      MyHeader,
      UserSignup
    },
    
  }) 
  </script>
  <style scoped>
  .MainCenter{
    text-align: center;
  }
  </style>