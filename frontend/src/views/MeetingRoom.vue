<template>
  <div id="room-main">
    <!-- <h1>지금 스터디 id : {{ $route.params.studyid }}</h1> -->
    <div class="area">
      <div class="inner-area">
        <meeting-video :myStudyId="myStudyId"></meeting-video>
        <meeting-score v-if="isInterviewer"></meeting-score>
      </div>
    </div>
    <div class="footer-div">
      <meeting-footer></meeting-footer>
    </div>
  </div>
</template>

<script>
import MeetingVideo from "../components/meeting/MeetingVideo.vue";
import MeetingFooter from "../components/meeting/MeetingFooter.vue";
import MeetingScore from "../components/meeting/MeetingScore.vue";
import { mapState } from "vuex";
const meetingStore = "meetingStore";
// const location = document.querySelector(".area").offsetTop;
export default {
  name: "MeetingRoom",
  components: {
    MeetingVideo,
    MeetingFooter,
    MeetingScore,
  },
  data() {
    return {
      myStudyId: null,
    };
  },
  created() {
    this.myStudyId = this.$route.params.studyid;
  },
  mounted() {
    // window.scrollTo({ top: location }, 0);
    window.scrollTo(100, 100);
  },
  computed: {
    ...mapState(meetingStore, ["isLeader", "isInterviewer", "leaderOrder"]),
  },
};
</script>

<style scoped>
.room-main {
  /* background-color: #f5f7fe; */
  /* background: rgb(240, 239, 243); */
  min-height: 300px;
  max-height: 300px;
}
/* #rate-btn { */
/* display: flex; */
/* justify-content: space-evenly; */
/* align-items: center; */
/* justify-content: center; */
/* } */
.rate {
  display: flex;
  align-items: center;
  justify-content: center;
}
.area {
  display: block;
  /* display: flex; */
  /* align-items: flex-start; */
  /* justify-content: center;s */
  /* justify-content: space-evenly; */
}
.footer-div {
  z-index: 2;
  position: fixed;
  height: 75px;
  width: 100%;
  bottom: 0;
  border-radius: 20px;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  filter: drop-shadow(0px 4px 40px rgba(90, 90, 90, 0.25));
}
.el-button {
  height: 20px;
}
.inner-area {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
