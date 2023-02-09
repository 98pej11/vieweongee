<template>
  <div>
    <h1>당신은 면접관입니다.</h1>
    <div v-for="(li, i) in scoreList" :key="i">
      <div>
        <h4>회원아이디 : {{ li.id }}</h4>
        어빌리티 : {{ li.ability }}<br />
        애티튜드 : {{ li.attitude }}<br />
        로얄티 : {{ li.loyalty }}<br />
        솔빙 : {{ li.solving }}<br />
        팀워크 : {{ li.teamwork }}<br />
        피드백 : {{ li.id }}<br />
        ==============================================
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

const meetingStore = "meetingStore";

export default {
  name: "MeetingScore",
  data() {
    return {
      list: [],
    };
  },
  computed: {
    ...mapState(meetingStore, ["scoreList", "interviewOrderList", "nowTurn"]),
  },
  created() {
    // this.list = [];
    // this.setShowScoreList();
    // console.log("보여줄 채점표 보여줄게요");
    // console.log(this.list);
  },
  // watch: {
  //   nowTurn() {
  //     //회차가 변경될때마다
  //     //보여줄 리스트 초기화
  //     this.list = [];
  //     this.setShowScoreList();
  //     console.log("보여줄 채점표 보여줄게요");
  //     console.log(this.list);
  //   },
  // },
  methods: {
    // setShowScoreList() {
    //   //현재 회차의 면접자 아이디, 채점표의 아이디 비교하여 해당하는 것만 보여줌
    //   for (let i = 0; i < this.interviewOrderList[this.nowTurn].length; i++) {
    //     //현재 회차의 면접자수만큼
    //     for (let j = 0; j < this.scoreList.length; j++) {
    //       //스터디 참가자 수만큼
    //       if (this.interviewOrderList[this.nowTurn][i] == this.scoreList[j].id) {
    //         //현재회차아이디와 채점표 아이디가 같으면
    //         this.list.push(this.scoreList[j]);
    //         break;
    //       }
    //     }
    //   }
    //   // if(this.interviewOrderList[this.nowTurn])
    // },
  },
};
</script>

<style scoped>
div {
  background-color: yellow;
}
</style>
