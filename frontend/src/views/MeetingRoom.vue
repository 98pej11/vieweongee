<template>
  <div>
    <meeting-rate v-if="isLeader"></meeting-rate>
    <meeting-video></meeting-video>
    <meeting-footer></meeting-footer>
  </div>
</template>

<script>
import MeetingVideo from "../components/meeting/MeetingVideo.vue";
import MeetingFooter from "../components/meeting/MeetingFooter.vue";
import MeetingRate from "../components/meeting/MeetingRate.vue";
import { mapState } from "vuex";

const meetingStore = "meetingStore";

export default {
  name: "MeetingRoom",
  components: {
    MeetingVideo,
    MeetingFooter,
    MeetingRate,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(meetingStore, ["isLeader"]),
  },
};
</script>

<style></style>
