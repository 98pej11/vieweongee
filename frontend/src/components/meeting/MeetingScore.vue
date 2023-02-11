<template>
  <div id="score-main">
    <h1>당신은 면접관입니다.</h1>
    <meeting-resume></meeting-resume>
    <div v-for="(li, i) in nowScoreList" :key="i">
      <!-- 면접자 id -->
      <h1>면접자 >> {{ li.id }}</h1>
      <!-- 대분류, 항목, 채점 -->
      <div>
        <h4>회원아이디 : {{ li.id }}</h4>
        <el-container
          ><b>{{ scoreData[0].type }}</b></el-container
        >
        <el-container>{{ scoreData[0].question }}</el-container>
        <el-container>{{ scoreData[1].question }}</el-container>
        <el-rate v-model="li.ability"></el-rate>
        어빌리티 : {{ li.ability }} <br />

        <el-container
          ><b>{{ scoreData[2].type }}</b></el-container
        >
        <el-container>{{ scoreData[2].question }}</el-container>
        <el-container>{{ scoreData[3].question }}</el-container>
        <el-rate v-model="li.attitude"></el-rate>
        애티튜드 : {{ li.attitude }}<br />

        <el-container
          ><b>{{ scoreData[4].type }}</b></el-container
        >
        <el-container>{{ scoreData[4].question }}</el-container>
        <el-container>{{ scoreData[5].question }}</el-container>
        <el-rate v-model="li.loyalty"></el-rate>
        로얄티 : {{ li.loyalty }}<br />

        <el-container
          ><b>{{ scoreData[6].type }}</b></el-container
        >
        <el-container>{{ scoreData[6].question }}</el-container>
        <el-container>{{ scoreData[7].question }}</el-container>
        <el-rate v-model="li.solving"></el-rate>
        솔빙 : {{ li.solving }}<br />

        <el-container
          ><b>{{ scoreData[8].type }}</b></el-container
        >
        <el-container>{{ scoreData[8].question }}</el-container>
        <el-container>{{ scoreData[9].question }}</el-container>
        <el-rate v-model="li.teamwork"></el-rate>
        팀워크 : {{ li.teamwork }}<br />

        피드백 : {{ li.feedback }}
        <el-input type="textarea" :rows="2" placeholder="피드백을 입력해주세요..." v-model="li.feedback"> </el-input>
      </div>
      <el-button @click="saveToLocal(li)" type="success" round>저장</el-button>
      <h4>===========================</h4>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import MeetingResume from "../meeting/MeetingResume.vue";

const meetingStore = "meetingStore";

export default {
  name: "MeetingScore",
  components: {
    MeetingResume,
  },
  computed: {
    ...mapState(meetingStore, ["nowScoreList"]),
  },
  data() {
    return {
      saveName: "",
      scoreData: [
        {
          type: "태도",
          question: "자신감 있는 표정과 목소리인가?",
          score: 3,
          index: 0,
        },
        {
          type: "태도",
          question: "기본 준비 자세가 올바른가?",
          score: 3,
          index: 1,
        },
        {
          type: "직무역량",
          question: "지원한 직무에 대한 구체적인 이해도를 가졌는가?",
          score: 3,
          index: 2,
        },
        {
          type: "직무역량",
          question: "직무 수행에 필요한 역량을 갖췄는가?",
          score: 3,
          index: 3,
        },
        {
          type: "팀워크",
          question: "다양한 의견을 수렴한 경험이 있는가?",
          score: 3,
          index: 4,
        },
        {
          type: "팀워크",
          question: "적극적으로 참여하는 자세를 가졌는가?",
          score: 3,
          index: 5,
        },
        {
          type: "문제해결",
          question: "문제를 해결하고자 하는 열정, 끈기, 의지를 가졌는가?",
          score: 3,
          index: 6,
        },
        {
          type: "문제해결",
          question: "해결에 도움이 되는 방향성을 제시하였는가?",
          score: 3,
          index: 7,
        },
        {
          type: "기업이해도",
          question: "기업에 대한 이해도가 높은가?",
          score: 3,
          index: 8,
        },
        {
          type: "기업이해도",
          question: "퇴사 가능성이 있는가?",
          score: 3,
          index: 9,
        },
      ],
    };
  },
  created() {},
  methods: {
    saveToLocal(list) {
      // console.log("저장 버튼 클릭!");
      console.log(list);
      //로컬 스토리지에 저장
      // const saveScore = {
      //   id: list.id,
      //   ability: list.ability,
      //   attitude: list.attitude,
      //   loyalty: list.loyalty,
      //   solving: list.loyalty,
      //   teamwork: list.teamwork,
      //   feedback: list.feedback,
      // };
      // this.saveName = "save#" + list.id;
      // localStorage.setItem(this.saveName, JSON.stringify(saveScore));
      console.log("저장 버튼 눌렀어요");
      // console.log(JSON.parse(localStorage.getItem(this.saveName)));
    },
  },
};
</script>

<style scoped>
#score-main {
  background-color: #cddefd;
}
</style>
