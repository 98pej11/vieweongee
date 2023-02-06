<template>
  <div>
    <div class="container">
      <div class="main main-box">
        <h2 class="text-h6 mb-3">
          {{ details.title }}싸피 비전공자 면접스터디 구해요
        </h2>
        <el-row>
          <el-col :span="20">
            <div>
              {{ details.user_id }}higildong | 2023.01.05{{
                details.regist_datetime
              }}
            </div>
          </el-col>
          <el-col :span="4">
            <div>인원수 {{ details.personnel }}</div>
          </el-col>
        </el-row>
        <hr />
        <el-row>
          <el-col :span="4"><p>기업</p> </el-col>
          <el-col :span="20"
            ><p>신한은행 {{ details.company }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>직군</p> </el-col>
          <el-col :span="20"
            ><p>프론트엔드 {{ details.job }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>날짜</p> </el-col>
          <el-col :span="20"
            ><p>2023.01.20 18:00PM {{ details.study_datetime }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>유형</p> </el-col>
          <el-col :span="20"
            ><p>다대다{{ details.type }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>진행시간</p> </el-col>
          <el-col :span="20"
            ><p>3시간{{ details.running_time }}</p>
          </el-col>
        </el-row>

        <el-row justify="end">
          <el-button round color="#E1E6FF" class="me-2">
            자기소개서 업로드
          </el-button>
          <!-- v-if 글 작성자 !== 로그인 유저 -->
          <el-button @click="registStudy" round color="#9DADD8" class="me-2">
            신청
          </el-button>
          <!-- v-if 글 작성자 === 로그인 유저 -->
          <el-button @click="modifyStudy" round color="#9DADD8" class="me-2">
            수정
          </el-button>
          <el-button @click="deleteStudy" round color="#FF5151" class="me-3"
            >삭제
          </el-button>
        </el-row>
        <hr />
        <el-row>
          <el-col style="color: black; font-weight: bold; margin-bottom: 20px"
            >소개</el-col
          >
        </el-row>
        <el-row>
          <el-col style="color: black"
            >{{ details.content }} 싸피 10기 비전공자 면접 스터디를
            모집합니다.<br />자기소개서를 기반으로 한 기본 질문 중심으로 진행
            예정입니다.<br />
            PT 면접은 진행하지 않습니다.
          </el-col>
        </el-row>
      </div>
    </div>
    <StudyComment></StudyComment>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import StudyComment from "@/components/study/StudyComment.vue";

const studyStore = "studyStore";

export default {
  name: "StudyDetail",
  components: { StudyComment },
  computed: {
    ...mapState(studyStore, ["isError", "studyID", "studyInfo"]),
  },
  mounted() {
    // this.getDetails();
  },
  methods: {
    ...mapActions(studyStore, ["getInfo"]),

    // 컴포넌트 전환
    modifyStudy() {
      this.$router.push({
        name: "studymodify",
        params: { studyid: this.studyID },
      });
    },
    deleteStudy() {},
    // 스터디 신청
    registStudy() {},

    // 스터디 글 정보 불러오기
    async getDetails() {
      await this.getInfo(this.studyID);
      this.details = this.studyInfo;
    },
  },
  data() {
    return {
      details: {},
    };
  },
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
