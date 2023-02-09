<template>
  <div>
    <h1>당신은 면접관입니다.</h1>
    <div v-for="(li, i) in nowScoreList" :key="i">
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

  computed: {
    ...mapState(meetingStore, ["nowScoreList"]),
  },
};
</script>

<style scoped>
div {
  background-color: yellow;
}
</style>
