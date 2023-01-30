<template>
  <el-container>
    <el-main class="main-box">
      <h2 class="text-h6 mb-3">싸피 비전공자 면접스터디 구해요</h2>
      <el-row>
        <el-col :span="20">
          <p>higildong | 2023.01.05</p>
        </el-col>
        <el-col>인원수</el-col>
      </el-row>
      <hr />
      <el-row>
        <el-col>기업</el-col>
        <el-col>신한은행</el-col>
      </el-row>
      <el-row>
        <el-col :span="4">직군</el-col>
        <el-col>프론트엔드</el-col>
      </el-row>
      <el-row>
        <el-col :span="4">날짜</el-col>
        <el-col>2023.01.20 18:00PM</el-col>
      </el-row>
      <el-row>
        <el-col :span="4">유형</el-col>
        <el-col>다대다</el-col>
      </el-row>
      <el-row justify="end">
        <el-button round color="#E1E6FF" class="me-2">
          자기소개서 업로드
        </el-button>
        <el-button round color="#9DADD8" class="me-2"> 수정 </el-button>
        <el-button round color="#FF5151" class="me-3">삭제 </el-button>
      </el-row>
      <hr />
      <el-row>
        <el-col class="text-black">
          <p class="font-weight-bold">소개</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="text-black">
          싸피 10기 비전공자 면접 스터디를 모집합니다.<br />자기소개서를
          기반으로 한 기본 질문 중심으로 진행 예정입니다.<br />
          PT 면접은 진행하지 않습니다.
        </el-col>
      </el-row>
    </el-main>
  </el-container>
  <StudyReply></StudyReply>
</template>

<script>
import StudyReply from "@/components/board/StudyReply.vue";

export default {
  name: "StudyDetail",
  components: { StudyReply },
};
</script>

<style scoped>
.el-container {
  margin: 0 auto;
  margin-top: 5%;
  width: 45%;
}
.el-main {
  padding: 10%;
}

.el-row > :nth-child(1) {
  color: gray;
}
.el-row > :nth-child(2) {
  font-weight: bold;
}

hr {
  color: gray;
  margin: 20px 0 20px 0;
}
button {
  color: white;
}
</style>
