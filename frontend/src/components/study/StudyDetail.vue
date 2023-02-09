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
            v-if="isApplied && !isOpened && !isAuthor"
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
            v-if="isDone"
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
import { ElMessageBox } from "element-plus";
import { User } from "@element-plus/icons-vue";
import jwtDecode from "jwt-decode";
import moment from "moment";
import StudyComment from "@/components/study/StudyComment.vue";

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
      "studyInfo",
      "commentList",
      "appliedList",
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
      isDone: false,
      myId: 0,
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
      console.log("미로그인? 로그인? : ");

      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;

      const params = {
        study_ID: this.studyID,
        user_ID: this.myId,
      };

      // this.SET_APPLY_SUCCESS(false);
      // await this.getInfo(this.studyID);
      await this.getInfo(params); // 객체를 줘야함
      await this.getPersonnel(this.studyID);
      await this.getCommentList(this.studyID);
      await this.checkPossible();
      await this.checkOpened();

      // 로그인 유저 == 글 작성자
      if (this.myId == 0) this.isAuthor = false;
      else if (this.myId == this.studyInfo.user_id) {
        this.isAuthor = true;
      }
    },

    // 신청 가능 여부
    checkPossible() {
      const startTime = moment(this.studyInfo.study_datetime);
      const now = moment();
      let diff = moment.duration(startTime.diff(now)).asHours();

      // 미팅 시작시간 24시간 전이면 신청 가능
      if (diff > 24) this.isPossible = true;
      // 아니면 신청 불가
      else if (diff >= 0 && diff < 24) this.isPossible = false;
      console.log(diff);
    },

    // 화상회의 참여 가능 여부
    checkOpened() {
      // 시작시간 24시간 전부터 화상회의 참여 가능
      const startTime = moment(this.studyInfo.study_datetime);
      const now = moment();
      let diff = moment.duration(startTime.diff(now)).asHours();

      if (diff >= 0 && diff < 24) this.isOpened = true;
      else if (diff < 0) this.isOpened = false;
      else if (diff < -28) this.isDone = true;
    },

    // 화상회의 참여
    enterMeeting() {
      // 화상회의 참여 !!!! 새 창으로 슈슉
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
      if (this.myId == 0) {
        this.alertOpen();
        // ElMessageBox.alert("로그인 후 이용 부탁드립니다.", "알림", {
        //   confirmButtonText: "확인",
        // });
      } else {
        await this.applyStudyConfirm(this.studyID);
        await this.getPersonnel(this.studyID);
      }
    },
    alertOpen() {
      ElMessageBox.confirm("로그인 후 이용 부탁드립니다", {
        confirmButtonText: "OK",
        type: "warning",
      }).then(() => {
        this.$router.push({ name: "login" });
      });
    },

    // 스터디 참가 신청 취소
    async cancleStudy() {
      await this.cancleStudyConfirm(this.studyID);
      await this.getPersonnel(this.studyID);
    },

    // 스터디 삭제
    async deleteOpen() {
      await this.deleteConfirm(this.studyID);
      this.$router.push({ name: "studylist" });
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
