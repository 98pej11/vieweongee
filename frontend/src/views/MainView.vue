<template>
  <div class="main">
    <MainCenter />
    <SearchBar></SearchBar>
    <MainCardList></MainCardList>
  </div>
</template>

<script>
import { defineComponent } from "vue";
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
.search-bar {
  margin: 2% 25% 2% 25%;
}
</style>
