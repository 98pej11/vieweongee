<template>
  <div>
    <div v-for="(img, idx) in nowResumeList" :key="idx">
      <!-- <h3>면접자 아이디 : {{ img.user_ID }}</h3> -->
      <img src="@/assets/image/profile_img.png" alt="자기소개서 파일" />
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
