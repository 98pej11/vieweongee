<template>
    <div class="findpwd">
      <MyHeader></MyHeader>
      <FindPassword />
      <!--<SearchBar /> -->
    </div>
  </template>
  
  <script lang="ts">
  import {defineComponent} from 'vue';
  import MyHeader from '@/components/common/MyHeader.vue';
  import FindPassword from '@/components/user/FindPassword.vue';
  
  export default defineComponent({
    components: {
    MyHeader,
    FindPassword
},
    
  }) 
  </script>
  <style scoped>
  .MainCenter{
    text-align: center;
  }
  </style>