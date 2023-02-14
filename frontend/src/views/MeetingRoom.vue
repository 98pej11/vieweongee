<template>
  <div>
    <!-- <h1>지금 스터디 id : {{ $route.params.studyid }}</h1> -->
    <div class="rate">
      <meeting-rate v-if="isLeader && leaderOrder == null"></meeting-rate>
    </div>
    <div class="area">
      <meeting-video :myStudyId="myStudyId"></meeting-video>
      <meeting-score v-if="isInterviewer" :width="30"></meeting-score>
    </div>
    <meeting-footer></meeting-footer>
  </div>
</template>

<script>
import MeetingVideo from "../components/meeting/MeetingVideo.vue";
import MeetingFooter from "../components/meeting/MeetingFooter.vue";
import MeetingRate from "../components/meeting/MeetingRate.vue";
import MeetingScore from "../components/meeting/MeetingScore.vue";
import { mapState } from "vuex";

const meetingStore = "meetingStore";

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
    };
  },
  created() {
    this.myStudyId = this.$route.params.studyid;
  },
  computed: {
    ...mapState(meetingStore, ["isLeader", "isInterviewer", "leaderOrder"]),
  },
};
</script>

<style scoped>
.rate {
  display: flex;
  align-items: center;
  justify-content: center;
}
.area {
  display: flex;
  align-items: flex-start;
  justify-content: center;
}
</style>
