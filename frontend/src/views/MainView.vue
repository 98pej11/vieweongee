<template>
  <div class="main">
    <MainCenter />
    <SearchBar></SearchBar>
    <MainCardList></MainCardList>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HelloWorld from "@/components/HelloWorld.vue"; // @ is an alias to /src
import MainCenter from "@/components/mainpage/MainCenter.vue";
import MainCardList from "@/components/mainpage/MainCardList.vue";
import SearchBar from "@/components/common/SearchBar.vue";

export default defineComponent({
  name: "MainView",
  components: {
    MainCenter,
    MainCardList,
    SearchBar,
  },
});
</script>
<style scoped>
.main {
  text-align: center;
}
</style>
