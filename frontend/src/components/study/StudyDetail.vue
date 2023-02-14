<template>
  <div>
    <div class="container">
      <div class="main-box">
        <div class="main">
          <el-button onclick="window.history.back()" class="backbtn">
            <!-- <el-icon><Back /></el-icon> -->
            <el-icon style="margin-right: 7px"><DocumentCopy /></el-icon>
            목록
          </el-button>
          <h1 class="text-h6 mb-3">{{ studyInfo.title }}</h1>
          <!-- 스터디 정보 -->
          <el-row>
            <el-col :span="20">
              <div style="float: right">
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
          <!-- 자기소개서 업로드 모달창 -->
          <el-dialog
            class="el-dialog"
            v-model="dialogVisible"
            width="600px"
            style="border-radius: 5%"
          >
            <el-upload
              drag
              class="upload-demo"
              accept=".jpg,.jpeg,.png"
              :multiple="false"
              :limit="1"
              :auto-upload="false"
              :file-list="fileList"
              ref="upload"
              :on-change="submitImage"
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text">
                이미지를 끌어오거나 <em>클릭해서 업로드</em>
              </div>
              <template #tip>
                <div class="el-upload__tip">
                  500kb이하의 JPG/PNG 이미지를 업로드해주세요
                </div>
              </template>
            </el-upload>
            <!-- <el-button
            block
            color="#9DADD8"
            size="large"
            style="display: block; margin: auto; width: 25%"
            @click="submitImage"
          >
            완료
          </el-button> -->
          </el-dialog>

          <!-- 스터디 신청, 취소, 입장-->
          <div style="margin-left: 70%">
            <el-row>
              <el-button
                v-if="isApplied || isAuthor"
                @click="showDialog"
                round
                color="#E1E6FF"
                class="me-2"
              >
                자기소개서 업로드
              </el-button>
              <div v-if="!isAuthor">
                <el-button
                  v-if="!isApplied && isPossible"
                  @click="applyStudy"
                  round
                  color="#9DADD8"
                  class="me-2"
                  style="color: white"
                >
                  신청
                </el-button>
                <el-button
                  v-if="isApplied && (!isOpened || isPossible)"
                  @click="cancleStudy"
                  round
                  color="#FFCD9F"
                  class="me-2"
                >
                  신청취소
                </el-button>
              </div>
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
                v-if="isOpened && (isApplied || isAuthor)"
                @click="enterMeeting(this.studyID)"
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
            <!-- 스터디 수정 및 삭제 -->
            <el-row justify="end" style="margin-top: 10px">
              <div v-if="isAuthor && isPossible">
                <el-button
                  @click="modifyStudy"
                  round
                  color="#9DADD8"
                  class="me-2"
                >
                  수정
                </el-button>
                <el-button
                  @click="deleteOpen"
                  round
                  color="#FF5151"
                  class="me-3"
                  >삭제
                </el-button>
              </div>
            </el-row>
          </div>

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
    </div>
    <StudyComment></StudyComment>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  User,
  UploadFilled,
  // Back,
  DocumentCopy,
} from "@element-plus/icons-vue";
import axios from "axios";
import jwtDecode from "jwt-decode";
import moment from "moment";
import StudyComment from "@/components/study/StudyComment.vue";

const studyStore = "studyStore";
const commentStore = "commentStore";

export default {
  name: "StudyDetail",
  components: {
    StudyComment,
    User,
    UploadFilled,
    // Back,
    DocumentCopy,
  },
  computed: {
    ...mapState(studyStore, [
      "isCreated",
      "isApplied",
      "studyID",
      "current",
      "studyInfo",
      "appliedList",
    ]),
  },
  mounted() {
    this.init();
  },
  data() {
    return {
      fileList: [],
      myImage: "",
      isOpened: false, // 화상 세션 입장 가능
      isPossible: false, // 스터디 신청 마감 여부
      isDone: false,
      isAuthor: false,
      dialogVisible: false,
      dialogImageUrl: "",
      myId: 0,
    };
  },
  methods: {
    ...mapMutations(studyStore, ["SET_APPLY_SUCCESS"]),
    ...mapActions(studyStore, [
      "getInfo",
      "applyStudyConfirm",
      "cancleStudyConfirm",
      "getPersonnel",
      "deleteConfirm",
      "uploagConfirm",
      "getAppliy",
    ]),
    ...mapActions(commentStore, ["getCommentList"]),

    // 스터디 글 정보 조회
    async init() {
      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;

      const params = {
        study_ID: this.studyID,
        user_ID: this.myId,
      };

      await this.getInfo(params);
      await this.setButton();

      await this.getPersonnel(this.studyID);
      await this.checkPossible();
      await this.checkOpened();
      await this.getAppliy(this.studyID);
      await this.getCommentList(this.studyID);

      console.log(this.isPossible + " 수정 삭제 가능 ? ");
      console.log(this.isAuthor + " 글작성자인가 ? ?");
    },

    // 버튼 활성화 여부
    setButton() {
      // 비로그인 유저
      if (this.myId == 0) {
        this.isAuthor = false;
      }
      // 로그인 유저 == 글 작성자
      else if (this.myId == this.studyInfo.user_id) {
        this.isAuthor = true;
      }
      // console.log(this.myId + " : 토큰아이디");
      // console.log(this.studyInfo.user_id + " : 글작성자");
    },

    // 신청 가능 여부
    checkPossible() {
      let now = moment(); // 현재 시간
      let startTime = moment(this.studyInfo.study_datetime); // 스터디 시작 시간
      let diff = moment.duration(startTime.diff(now)).asHours(); // 시간 차이

      // 신청자 수 마감 시 신청 불가
      if (this.current == this.studyInfo.personnel) {
        this.isPossible = false;
      }
      // 24시간 이후이면 신청 불가
      else if (diff >= 0 && diff < 24) {
        this.isPossible = false;
      }
      // 미팅 시작시간 24시간 전이면 신청 가능
      else if (diff > 24) {
        this.isPossible = true;
      }
    },

    // 화상회의 입장 가능 여부
    checkOpened() {
      let now = moment(); // 현재 시간
      let startTime = moment(this.studyInfo.study_datetime); // 화상회의 시작 시간
      let endTime = moment(this.studyInfo.study_datetime).add(
        this.studyInfo.running_time,
        "h"
      ); // 화상회의 종료 시간
      let diff = moment.duration(startTime.diff(now)).asHours();
      let closed = moment.duration(endTime.diff(now)).asHours();

      console.log("이미 화상미팅 종료됐나요? ? ? : " + closed);

      // 시작시간 24시간 전부터 화상회의 입장 가능
      if (diff >= 0 && diff < 24) {
        this.isOpened = true;
      }
      // 24시간 이내일 때부터 입장 불가
      else if (diff < 0) {
        this.isOpened = false;
      }
      // 화상미팅 종료 이후
      else if (closed <= 0) {
        this.isDone = true;
      }
    },

    // 화상회의 참여
    enterMeeting(id) {
      this.$router.push({ name: "meeting", params: { studyid: id } });
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
        ElMessage({
          type: "error",
          message: "로그인 후 이용 부탁드립니다",
        });
        this.$router.push({ name: "login" });
      } else {
        await this.applyStudyConfirm(this.studyID);
        await this.getPersonnel(this.studyID);
      }
    },

    // 스터디 참가 신청 취소
    async cancleStudy() {
      await this.cancleStudyConfirm(this.studyID);
      await this.getPersonnel(this.studyID);
    },

    showDialog() {
      this.dialogVisible = true;
    },

    // 자기소개서 첨부
    submitImage(file) {
      // 기본 input 태그 방식
      // this.myImage = this.$refs.myimage.files[0];
      // let formData = new FormData();
      // formData.append("file", this.myImage);

      let formData = new FormData();
      this.myImage = file.raw;
      formData.append("file", this.myImage);

      axios.defaults.headers.put["Content-Type"] = "multipart/form-data";
      axios.defaults.headers.put["ACCESS"] = sessionStorage.getItem("ACCESS");
      axios({
        method: "put",
        url: `http://localhost:8080/api/study/${this.studyID}/resume`,
        data: formData,
      })
        .then((data) => {
          if (data.data.message == "SUCCESS") {
            this.dialogVisible = false;
            this.myImage = "";
            ElMessage({
              type: "success",
              message: "자기소개서 업로드 완료",
            });
          }
        })
        .catch((error) => {
          console.log(error);
          ElMessage({
            type: "warning",
            message: "이미지 용량 초과",
          });
        });

      console.log(this.studyID + " 번에 자기소개서 첨부하기");
    },

    // 스터디 삭제
    async deleteOpen() {
      await this.deleteConfirm(this.studyID);
      if (this.isCreated) {
        this.showAlert();
      }
    },
    showAlert() {
      ElMessageBox.confirm("스터디를 삭제하시겠습니까?", "Warning", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "warning",
        draggable: true,
      })
        .then(() => {
          ElMessage({
            type: "success",
            message: "스터디 삭제 성공",
          });
          this.$router.push({ name: "studylist" });
        })
        .catch(() => {
          ElMessage({
            type: "info",
            message: "삭제 중 오류 발생",
          });
        });
    },
  },
};
</script>

<style scoped>
.container {
  margin: 0 auto;
  margin-top: 5%;
  width: 60%;
}
.main {
  padding: 7%;
  margin-bottom: 5%;
}

button {
  color: white;
}
.el-row {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: larger;
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

.backbtn {
  width: 10%;
  height: 40px;
  border-radius: 30px;
  font-size: large;
  background-color: #d3daff;
  color: black;
  margin-bottom: 3%;
}

.person-div {
  /* padding: 5px; */
  width: 60%;
  height: 40px;
  margin: 0 auto;
  border-radius: 30px;
  background-color: #d3daff;
  color: rgb(62, 62, 62);
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
