<template>
  <div>
    <h1>자기소개서</h1>
    <div v-for="(img, idx) in nowResumeList" :key="idx">
      <h4>면접자 아이디 : {{ img.user_ID }}</h4>
      <img :src="img.imgURL" alt="몰라요" />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

const meetingStore = "meetingStore";

export default {
  name: "MeetingResume",
  computed: {
    ...mapState(meetingStore, ["nowResumeList"]),
  },
  methods: {},
};
</script>

<style scoped>
img {
  width: 300px;
}
</style>
