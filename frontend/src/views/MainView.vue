<template>
  <div class="main" style="margin-top: 5%">
    <MainCenter />
    <h3>면접 걱정을 덜어주는 모의면접 플랫폼</h3>
    <h3 style="margin-top: 5%">최근 스터디</h3>
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
