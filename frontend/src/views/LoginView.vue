<template>
    <div class="login">
      <MyHeader></MyHeader>
      <UserLogin />
      <!--<SearchBar /> -->
    </div>
  </template>
  
  <script lang="ts">
  import {defineComponent} from 'vue';
  import UserLogin from '@/components/user/UserLogin.vue';
  import MyHeader from '@/components/common/MyHeader.vue';
  
  export default defineComponent({
    components: {
      MyHeader,
      UserLogin
    },
    
  }) 
  </script>
  <style scoped>
  .MainCenter{
    text-align: center;
  }
  </style>