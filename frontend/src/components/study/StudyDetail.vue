<template>
  <div>
    <div class="container">
      <div class="main main-box">
        <h2 class="text-h6 mb-3">{{ studyInfo.title }}</h2>
        <el-row justify="space-between">
          <el-col :span="20">
            <div>
              {{ studyInfo.user_nickname }} 님 |
              {{ studyInfo.regist_datetime }}
            </div>
          </el-col>
          <el-col :span="4">
            <div class="person-div">
              &nbsp; <el-icon :size="17"><User /></el-icon>&nbsp;{{
                this.current
              }}&nbsp;/
              {{ studyInfo.personnel }}
            </div>
          </el-col>
        </el-row>
        <hr />
        <el-row>
          <el-col :span="4"><p>기업</p> </el-col>
          <el-col :span="20"
            ><p>{{ studyInfo.company }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>직군</p> </el-col>
          <el-col :span="20"
            ><p>{{ studyInfo.job }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>날짜</p> </el-col>
          <el-col :span="20"
            ><p>{{ studyInfo.study_datetime }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>유형</p> </el-col>
          <el-col :span="20"
            ><p>{{ studyInfo.type }} 스터디</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4"><p>진행시간</p> </el-col>
          <el-col :span="20"
            ><p>{{ studyInfo.running_time }} 시간</p>
          </el-col>
        </el-row>

        <el-row justify="end">
          <el-button v-if="isApplied" round color="#E1E6FF" class="me-2">
            자기소개서 업로드
          </el-button>
          <el-button
            v-if="!isApplied && isPossible && !isAuthor"
            @click="applyStudy"
            round
            color="#9DADD8"
            class="me-2"
            style="color: white"
          >
            신청
          </el-button>
          <el-button
            v-if="isApplied && !isPossible"
            @click="cancleStudy"
            round
            color="#FFCD9F"
            class="me-2"
          >
            신청취소
          </el-button>
          <el-button
            v-if="!isPossible"
            round
            disabled
            color="#555454"
            class="me-2 done"
          >
            마감
          </el-button>
          <el-button
            v-if="isOpened && isApplied"
            @click="enterMeeting"
            round
            color="#FFCD9F"
            class="me-2"
          >
            입장
          </el-button>
          <el-button
            v-if="!isOpened"
            round
            disabled
            color="#555454"
            class="me-2 done"
          >
            종료
          </el-button>
        </el-row>
        <el-row justify="end" style="margin-top: 10px">
          <div v-if="isAuthor && isPossible">
            <el-button @click="modifyStudy" round color="#9DADD8" class="me-2">
              수정
            </el-button>
            <el-button @click="deleteOpen" round color="#FF5151" class="me-3"
              >삭제
            </el-button>
          </div>
        </el-row>
        <hr />
        <el-row>
          <el-col style="color: black; font-weight: bold; margin-bottom: 20px"
            >소개</el-col
          >
        </el-row>
        <el-row>
          <el-col style="color: black">{{ studyInfo.content }} </el-col>
        </el-row>
      </div>
    </div>
    <StudyComment></StudyComment>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { User } from "@element-plus/icons-vue";
import StudyComment from "@/components/study/StudyComment.vue";
import moment from "moment";

const studyStore = "studyStore";

export default {
  name: "StudyDetail",
  components: { StudyComment, User },
  computed: {
    ...mapState(studyStore, [
      "isError",
      "isApplied",
      "studyID",
      "current",
      "token_id",
      "studyInfo",
      "commentList",
    ]),
  },
  mounted() {
    this.init();
  },
  data() {
    return {
      isOpened: false,
      isPossible: false,
      isAuthor: false,
    };
  },
  methods: {
    ...mapActions(studyStore, [
      "getInfo",
      "getCommentList",
      "applyStudyConfirm",
      "cancleStudyConfirm",
      "getPersonnel",
      "deleteConfirm",
    ]),
    ...mapMutations(studyStore, ["SET_APPLY_SUCCESS"]),

    // 스터디 글 정보 조회
    async init() {
      this.SET_APPLY_SUCCESS(false);
      await this.getInfo(this.studyID);
      await this.getPersonnel(this.studyID);
      await this.getCommentList(this.studyID);
      await this.checkPossible();
      await this.checkOpened();

      if (this.token_id == this.studyInfo.user_id) {
        this.isAuthor = true;
      }
    },

    checkPossible() {
      // 미팅 시작시간 24시간 전이면 신청 가능
      const startTime = moment(this.studyInfo.study_datetime);
      const now = moment();
      let diff = moment.duration(startTime.diff(now)).asHours();

      if (diff > 24) this.isPossible = true;
      else if (diff >= 0 && diff < 24) this.isPossible = false;
      console.log(diff);
    },

    checkOpened() {
      // 미팅 시작시간 24시간 전이면 신청 가능
      const startTime = moment(this.studyInfo.study_datetime);
      const now = moment();
      let diff = moment.duration(startTime.diff(now)).asHours();

      if (diff >= 0 && diff < 24) this.isOpened = true;
      else if (diff < 0) this.isOpened = false;
    },

    enterMeeting() {
      // 미팅 참여 !!!! 새 창으로 슈슉
      // this.$router.push();
    },

    // 컴포넌트 전환
    modifyStudy() {
      this.$router.push({
        name: "studymodify",
        params: { studyid: this.studyID },
      });
    },

    // 스터디 참가 신청하기
    async applyStudy() {
      await this.applyStudyConfirm(this.studyID);
      await this.getPersonnel(this.studyID);
    },

    // 스터디 참가 신청 취소
    async cancleStudy() {
      await this.cancleStudyConfirm(this.studyID);
      await this.getPersonnel(this.studyID);
    },

    async deleteOpen() {
      console.log("삭제할게요");
      await this.deleteConfirm(this.studyID);
      this.$router.push("main");
    },
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

button {
  color: white;
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
.person-div {
  /* padding: 5px; */
  width: 75px;
  display: flex;
  padding: 5px 0 5px 0;
  margin: 0 auto;
  border-radius: 30px;
  background-color: #d3daff;
}
</style>
