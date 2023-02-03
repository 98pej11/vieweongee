<template>
  <div class="card-list">
    <div v-for="(data, index) in cardData" :key="index" class="card-div">
      <div>(인원수)</div>
      <div class="title">{{ data.title }}</div>
      <div class="card-contents">
        <div>기업</div>
        <div>{{ data.ent }}</div>
      </div>
      <div class="card-contents">
        <div>직무</div>
        <div>{{ data.dept }}</div>
      </div>
      <div class="card-contents">
        <div>기업</div>
        <div>{{ data.date }}</div>
      </div>
    </div>
  </div>
</template>
<script  >
import { defineComponent } from "vue";
// import MainCardItem from "@/components/mainpage/MainCardItem.vue";
export default defineComponent({
  name: "MainCardList",
  components: {
    // MainCardItem,
  },
  setup() {
    const cardData = [
      {
        title: "싸피 비전공자 면접스터디 구해요",
        ent: "SSAFY",
        dept: "프론트엔드",
        date: "2023.01.12 10:00",
      },
      {
        title: "신한은행 면접스터디",
        ent: "신한은행",
        dept: "백엔드",
        date: "2023.01.11 19:00",
      },
      {
        title: "유플러스 백엔드 면접",
        ent: "유플러스",
        dept: "백엔드",
        date: "2023.01.10 11:00",
      },
    ];
    return { cardData };
  },
});
</script>
<style scoped>
.card-list {
  display: flex;
  justify-content: center;
  padding: 20px;
}
.card-contents {
  display: flex;
  justify-content: space-between;
  padding: 0 30px 10px 30px;
}
.card-div {
  font-family: "nexonlv1";
  min-width: 280px;
  margin: 30px;
  padding: 20px;
  border: 4px solid rgba(227, 232, 252, 0.5);
  border-radius: 10px;
  box-shadow: 1px 10px 15px 1px rgb(221, 221, 221);
}

.card-div:hover {
  background: rgba(210, 218, 255, 0.6);
  transition: all 200ms 0s ease-in;
  transform: scale(1.04);
  border: 4px solid rgba(227, 232, 252, 0.2);
}
.card-contents :nth-child(1) {
  color: #555454;
}

.title {
  font-weight: bold;
  font-size: large;
  text-align: center;
  margin: 3% 0 10% 0;
}
</style>
