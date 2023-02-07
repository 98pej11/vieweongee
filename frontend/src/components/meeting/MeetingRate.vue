<template>
  <div>
    <h3>면접자 : 면접관 비율 설정</h3>
    <div v-if="studyParticipant == 6">
      <input type="radio" v-model="intervieweeRate" value="1" />1:5
      <input type="radio" v-model="intervieweeRate" value="2" />2:4
      <input type="radio" v-model="intervieweeRate" value="3" />3:3
      <input type="radio" v-model="intervieweeRate" value="4" />4:2
    </div>
    <div v-else-if="studyParticipant == 5">
      <input type="radio" v-model="intervieweeRate" value="1" />1:4
      <input type="radio" v-model="intervieweeRate" value="2" />2:3
      <input type="radio" v-model="intervieweeRate" value="3" />3:2
    </div>
    <div v-else-if="studyParticipant == 4">
      <input type="radio" v-model="intervieweeRate" value="1" />1:3
      <input type="radio" v-model="intervieweeRate" value="2" />2:2
    </div>
    <div v-else-if="studyParticipant == 3"><input type="radio" v-model="intervieweeRate" value="1" />1:2</div>
    <div v-else><input type="radio" v-model="intervieweeRate" value="1" />1:1</div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const meetingStore = "meetingStore";
export default {
  name: "MeetingRate",
  computed: {
    ...mapState(meetingStore, ["intervieweeRate"]),
  },
};
</script>

<style></style>
