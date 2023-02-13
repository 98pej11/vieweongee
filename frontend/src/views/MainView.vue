<template>
  <div class="main">
    <div class="background-image">
      <MainCenter />
    </div>
    <div class="vieweong">
      <!-- <img src="@/assets/image/logo_mascot.png" alt="your image" /> -->
      <h3>취업을 낚아채주마 !</h3>
    </div>
    <h3>♥ 면접 걱정을 덜어주는 모의면접 플랫폼 ♥</h3>
    <SearchBar></SearchBar>

    <h3 style="margin-top: 5%">~ 최근 스터디 바로가기 ~</h3>
    <MainCardList compType="main"></MainCardList>
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
  /* background-color: rgb(238, 233, 251); */
}
.background-image {
  position: relative;
}
.vieweong {
  display: flex;
  align-items: center;
  justify-content: center;
}
.background-image::before {
  content: "";
  /* background: url(your-image-path.jpg) no-repeat center center/cover; */
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  opacity: 0.9;
  z-index: -1;
}

.background-image img {
  position: relative;
  z-index: 1;
}
</style>
