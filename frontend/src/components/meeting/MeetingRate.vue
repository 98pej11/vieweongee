<template>
  <div>
    <h3>면접자 : 면접관 비율 설정</h3>
    <div v-if="studyInfo.personnel == 6">
      <input type="radio" v-model="rate" value="1" />1:5 <input type="radio" v-model="rate" value="2" />2:4
      <input type="radio" v-model="rate" value="3" />3:3 <input type="radio" v-model="rate" value="4" />4:2
    </div>
    <div v-else-if="studyInfo.personnel == 5">
      <input type="radio" v-model="rate" value="1" />1:4 <input type="radio" v-model="rate" value="2" />2:3
      <input type="radio" v-model="rate" value="3" />3:2
    </div>
    <div v-else-if="studyInfo.personnel == 4">
      <input type="radio" v-model="rate" value="1" />1:3 <input type="radio" v-model="rate" value="2" />2:2
    </div>
    <div v-else-if="studyInfo.personnel == 3"><input type="radio" v-model="intervieweeRate" value="1" />1:2</div>
    <div v-else><input type="radio" v-model="rate" value="1" />1:1</div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const meetingStore = "meetingStore";
const studyStore = "studyStore";

export default {
  name: "MeetingRate",
  data() {
    return {
      rate: 0,
    };
  },
  computed: {
    ...mapState(meetingStore, ["intervieweeRate"]),
    ...mapState(studyStore, ["studyInfo"]),
  },
  watch: {
    rate() {
      //면접자 수가 변경될때마다 설정
      // this.setIntervieweeRate(this.rate);
      this.SET_INTERVIEWEERATE(this.rate);
      // console.log("설정한 면접자 수 >>" + this.intervieweeRate);
    },
  },
  methods: {
    // ...mapActions(meetingStore, ["setIntervieweeRate"]),
    ...mapMutations(meetingStore, ["SET_INTERVIEWEERATE"]),
  },
};
</script>

<style></style>
