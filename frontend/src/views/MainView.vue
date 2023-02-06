<template>
  <div class="main">
    <MainCenter />
    <SearchBar></SearchBar>
    <MainCardList @move="move"></MainCardList>
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
  data() {
    return { studyId: 1 };
  },
  methods: {
    // move(id) {
    //   this.studyId = id;
    //   console.log(this.studyId);
    // },
  },
});
</script>
<style scoped>
.main {
  text-align: center;
}
</style>
