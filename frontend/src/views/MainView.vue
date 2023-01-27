<template>
  <div class="main">
    <MyHeader></MyHeader>
    <MainCenter />
    <!--<SearchBar /> -->
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src
import MainCenter from '@/components/mainpage/MainCenter.vue';
import SearchBar from '@/components/common/SearchBar.vue';
import MyHeader from '@/components/common/MyHeader.vue';

export default defineComponent({
  name: 'MainView',
  components: {
    MyHeader,
    MainCenter
  },
  
}) 
</script>
<style scoped>
.MainCenter{
  text-align: center;
}
</style>