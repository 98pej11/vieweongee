<template>
  <div class="footer">
    <el-button round @click="showChat" size="large">채팅</el-button>

    <el-button v-if="!isLeader" type="info" round disabled>미팅시작</el-button>
    <el-button
      v-if="isLeader && nowTurn == null"
      type="success"
      size="large"
      round
      @click="startMeeting"
      >미팅시작</el-button
    >
    <el-button
      id="endBtn"
      v-if="isLeader && nowTurn != null"
      type="success"
      size="large"
      round
      @click="nextTurn"
      v-bind:disabled="leaderTurn >= totalTurn"
      >면접종료</el-button
    >
    <el-button type="danger" round @click="goLeaveSession" size="large"
      >나가기</el-button
    >
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
import { mapState, mapActions, mapMutations } from "vuex";
const meetingStore = "meetingStore";
const studyStore = "studyStore";

export default {
  name: "MeetingFooter",
  data() {
    return {
      message: "",
    };
  },
  computed: {
    ...mapState(meetingStore, [
      "isLeader",
      "isInterviewee",
      "intervieweeRate",
      "interviewOrder",
      "leaderOrder",
      "nowTurn",
      "totalTurn",
      "leaderTurn",
      "isShowChat",
      "isLeaveSession",
    ]),
    ...mapState(studyStore, ["studyInfo"]),
  },
  created() {},
  methods: {
    ...mapMutations(meetingStore, [
      "SET_LEADER_ORDER",
      "SET_NOWTURN",
      "SET_LEADER_TURN",
      "SET_IS_CHAT",
      "SET_IS_LEAVE_SESSION",
    ]),
    ...mapActions(meetingStore, ["makeScoreAndGetOrder", "makeScorecards"]),
    async startMeeting() {
      if (this.intervieweeRate == 0) {
        ElMessage({
          message: "면접 유형을 선택해주세요.",
          type: "error",
        });
      } else {
        console.log("면접자의 수 >> " + this.intervieweeRate);
        const params = {
          study_ID: this.studyInfo.id,
          data: {
            interviewee: this.intervieweeRate,
          },
        };
        //채점표를 생성한다
        //면접순서를 받아온다
        //방장의 면접 순서가 감지되면 시그널 전송
        await this.makeScoreAndGetOrder(params);
        console.log("받아온 스터디 순서 >> " + this.leaderOrder);
      }
    },
    async nextTurn() {
      console.log("총 회차 >> " + this.totalTurn);
      console.log("현재 회차 >> " + this.nowTurn);

      //리더가 면접 종료를 누를 때마다 회차 올려주고 시그널로 보냄
      this.SET_LEADER_TURN(this.leaderTurn + 1);
      //받은 사용자들은 자신의 현재 회차 갱신
      //회차가 업데이트 되면 면접자, 면접관 확인
    },
    async showChat() {
      console.log("채팅을 보여드립니댜");
      if (this.isShowChat) {
        this.SET_IS_CHAT(false);
      } else {
        this.SET_IS_CHAT(true);
      }
    },
    goLeaveSession() {
      this.SET_IS_LEAVE_SESSION(true);
    },
  },
};
</script>

<style scoped>
.footer {
  background-color: white;
  margin: 3%;
  float: left;
}
</style>
