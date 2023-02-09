<template>
  <div>
    <meeting-rate v-if="isLeader && leaderOrder == null"></meeting-rate>
    <div id="area">
      <meeting-video></meeting-video>
      <meeting-score v-if="isInterviewer"></meeting-score>
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
    return {};
  },
  computed: {
    ...mapState(meetingStore, ["isLeader", "isInterviewer", "leaderOrder"]),
  },
};
</script>

<style scoped>
#area {
  display: flex;
}
</style>
