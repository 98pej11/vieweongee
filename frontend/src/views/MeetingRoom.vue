<template>
  <div id="room-main">
    <!-- <h1>지금 스터디 id : {{ $route.params.studyid }}</h1> -->
    <div id="room-header">
      <el-dialog
        id="dialog"
        style="
          background-color: #e5e1f8;
          border-radius: 5%;
          width: 30%;
          margin: 20 auto;
          text-align: center;
          font-weight: bold;
        "
        v-model="dialogVisible"
        title="' 면접자 : 면접관 ' 비율"
        :before-close="handleClose"
      >
        <!-- 스터디 유형 선택 컴포넌트 -->
        <div class="rate">
          <meeting-rate v-if="isLeader && leaderOrder == null"></meeting-rate>
        </div>
        <span>
          <el-button @click="dialogVisible = false" size="large" round
            >완료</el-button
          >
        </span></el-dialog
      >
      <el-button
        text
        @click="dialogVisible = true"
        v-if="isLeader && leaderOrder == null"
      >
        면접 유형 선택
      </el-button>
    </div>
    <div class="area">
      <meeting-video :myStudyId="myStudyId"></meeting-video>
      <meeting-score v-if="isInterviewer"></meeting-score>
    </div>
    <div class="footer-div">
      <meeting-footer></meeting-footer>
    </div>
  </div>
</template>

<script>
import MeetingVideo from "../components/meeting/MeetingVideo.vue";
import MeetingFooter from "../components/meeting/MeetingFooter.vue";
import MeetingRate from "../components/meeting/MeetingRate.vue";
import MeetingScore from "../components/meeting/MeetingScore.vue";
import { mapState } from "vuex";
import { ref } from "vue";
const meetingStore = "meetingStore";
// const location = document.querySelector(".area").offsetTop;
export default {
  name: "MeetingRoom",
  components: {
    MeetingVideo,
    MeetingFooter,
    MeetingRate,
    MeetingScore,
  },
  data() {
    return {
      myStudyId: null,
      dialogVisible: ref(),
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
#room-header {
  display: flex;
  align-items: center;
  justify-content: center;
}
.rate {
  display: flex;
  align-items: center;
  justify-content: center;
}
.area {
  display: flex;
  /* align-items: flex-start; */
  /* justify-content: center; */
  justify-content: space-evenly;
}
.footer-div {
  z-index: 2;
  position: fixed;
  height: 100px;
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
</style>
