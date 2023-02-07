<template>
  <div>
    <div class="container">
      <div class="main main-box">
        <h2 class="text-h6 mb-3">싸피 비전공자 면접스터디 구해요</h2>
        <el-row>
          <el-col :span="20">
            <div>higildong | 2023.01.05</div>
          </el-col>
          <el-col :span="4">
            <div>인원수</div>
          </el-col>
        </el-row>
        <hr />
        <el-row>
          <el-col :span="4"><p>기업</p> </el-col>
          <el-col :span="20"><p>신한은행</p> </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>직군</p> </el-col>
          <el-col :span="20"><p>프론트엔드</p> </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>날짜</p> </el-col>
          <el-col :span="20"><p>2023.01.20 18:00PM</p> </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>유형</p> </el-col>
          <el-col :span="20"><p>다대다</p> </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>진행시간</p> </el-col>
          <el-col :span="20"><p>3시간</p> </el-col>
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
          <el-col style="color: black; font-weight: bold; margin-bottom: 20px"
            >소개</el-col
          >
        </el-row>
        <el-row>
          <el-col style="color: black">
            싸피 10기 비전공자 면접 스터디를 모집합니다.<br />자기소개서를
            기반으로 한 기본 질문 중심으로 진행 예정입니다.<br />
            PT 면접은 진행하지 않습니다.
          </el-col>
        </el-row>
      </div>
    </div>
    <StudyReply></StudyReply>
  </div>
</template>

<script>
import StudyReply from "@/components/board/StudyReply.vue";

export default {
  name: "StudyDetail",
  components: { StudyReply },
};
</script>

<style scoped>
.container {
  margin: 0 auto;
  margin-top: 5%;
  width: 45%;
}
.main {
  padding: 10%;
  margin-bottom: 5%;
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
