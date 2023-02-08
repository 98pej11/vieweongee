<template>
  <div>
    <el-button round>채팅</el-button>
    <el-button v-if="!isLeader" type="info" round disabled>미팅시작</el-button>
    <el-button v-if="isLeader && nowTurn == null" type="success" round @click="startMeeting">미팅시작</el-button>
    <el-button
      id="endBtn"
      v-if="isLeader && nowTurn != null"
      type="success"
      round
      @click="nextTurn"
      v-bind:disabled="nowTurn == totalTurn - 1"
      >면접종료</el-button
    >
    <el-button type="danger" round>나가기</el-button>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const meetingStore = "meetingStore";
const studyStore = "studyStore";

export default {
  name: "MeetingFooter",
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
    ]),
    ...mapState(studyStore, ["studyInfo"]),
  },
  created() {
    //방장이면 미팅 시작 버튼 보임
    //면접자면 면접종료 버튼 보임
  },
  methods: {
    ...mapMutations(meetingStore, ["SET_LEADER_ORDER", "SET_NOWTURN", "SET_LEADER_TURN"]),
    ...mapActions(meetingStore, ["getOrder"]),
    async startMeeting() {
      if (this.intervieweeRate == 0) {
        alert("면접자 : 면접관 비율을 선택해주세요!");
      } else {
        console.log("면접자의 수 >> " + this.intervieweeRate);
        //방장 할 일
        //- 면접 순서 받아오기
        // await this.getOrder(this.studyInfo.id, this.intervieweeRate);
        const params = {
          study_ID: this.studyInfo.id,
          data: {
            interviewee: this.intervieweeRate,
          },
        };
        await this.getOrder(params);
        console.log("받아온 스터디 순서 >> " + this.leaderOrder);
        //- 면접 순서 시그널로 쏘기 >> 방장의 면접순서가 null이 아니라면 MeetingVideo에서 시그널로 전송
        //- 채점표 생성하기

        //모두가 면접 순서 받으면
        //- 면접 리스트 저장
        //- 채점표 불러오기
      }
    },
    async nextTurn() {
      //클릭 횟수 제한
      // if (this.nowTurn == this.totalTurn - 1) {
      //   //마지막 회차일때 면접 종료 버튼 비활성화
      //   const target = document.getElementById("endBtn");
      //   target.disabled = true;
      // } else {
      // }
      console.log("총 회차 >> " + this.totalTurn);
      console.log("현재 회차 >> " + this.nowTurn);

      //리더가 면접 종료를 누를 때마다 회차 올려주고 시그널로 보냄
      this.SET_LEADER_TURN(this.leaderTurn + 1);
      //받은 사용자들은 자신의 현재 회차 갱신
      //회차가 업데이트 되면 면접자, 면접관 확인

      //면접 종료를 누를 때마다 다음 회차로 넘어감
      // if (this.nowTurn < this.totalTurn - 1) {
      //   //3분 뒤 다음 회차로 바꿔주기
      //   setTimeout(() => {
      //     this.SET_NOWTURN(this.nowTurn + 1);
      //   }, 10000); //10초 뒤에 회차 변경 >> 추후 수정!!!!!!!!!!!!!!
      //   //회차 바뀌었는데 내가 면접자/면접관인지 확인 -> trun만 바꿔주고 MeetingVideo에서 nowTurn watch
      //   console.log("회차가 변경됐습니다 >> " + this.nowTurn);
      // }
    },
  },
};
</script>

<style></style>
