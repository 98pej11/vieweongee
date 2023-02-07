<template>
  <div>
    <el-button round>채팅</el-button>
    <el-button v-if="!isLeader" type="info" round>미팅시작</el-button>
    <el-button v-else type="success" round disabled>미팅시작</el-button>
    <el-button v-if="isInterviewee" type="success" round>면접종료</el-button>
    <el-button type="danger" round>나가기</el-button>
  </div>
</template>

<script>
import { mapState } from "vuex";

const meetingStore = "meetingStore";

export default {
  name: "MeetingFooter",
  computed: {
    ...mapState(meetingStore, ["isLeader", "isInterviewee"]),
  },
  created() {
    //방장이면 미팅 시작 버튼 보임
    //면접자면 면접종료 버튼 보임
  },
};
</script>

<style></style>
