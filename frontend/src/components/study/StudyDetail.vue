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
          <!-- 스터디 정보 시작-->
          <el-row>
            <el-col :span="20" :xs="24">
              <div>
                {{ studyInfo.user_nickname }} 님 |
                {{ studyInfo.regist_datetime }}
              </div>
            </el-col>
            <el-col :span="4" :xs="24">
              <div class="person-div">
                <el-icon :size="17"><User /></el-icon>&nbsp;{{
                  this.current
                }}&nbsp;/&nbsp;{{ studyInfo.personnel }}
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
          <!-- 스터디 정보 끝 -->
          <!-- 자기소개서 업로드 모달창 시작 -->
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
          </el-dialog>
          <!-- 모달창 끝 -->

          <!-- 스터디 신청, 취소, 입장-->
          <el-row justify="end">
            <el-col :span="4">
              <!-- 미팅 종료 전 && ( 신청자 || 글 작성자 ) -->
              <el-button
                v-if="!isDone && !isOpened && (isApplied || isAuthor)"
                @click="showDialog"
                round
                color="#E1E6FF"
                class="me-2"
              >
                자기소개서 업로드
              </el-button>
            </el-col>
          </el-row>
          <!-- 신청 / 신청취소 -->
          <el-row justify="end">
            <!-- <e-col :span="2" v-if="!isApplied && isPossible && !isAuthor"> -->
            <!-- 신청 가능 시간 && 남은 자리 있음 && 미신청자 && 글 작성자가 아닌 유저 -->
            <e-col
              :span="2"
              v-if="isPossible && isRemain && !isApplied && !isAuthor"
            >
              <el-button
                @click="applyStudy"
                round
                color="#9DADD8"
                class="me-2"
                style="color: white"
              >
                신청
              </el-button>
            </e-col>
            <!--  신청자&& !글작성자 && 신청 가능 시간  && 세션 생성 전 -->
            <e-col
              :span="6"
              v-if="!isAuthor && isApplied && isPossible && !isOpend"
            >
              <el-button
                @click="cancleStudy"
                round
                color="#FFCD9F"
                class="me-2"
              >
                신청취소
              </el-button>
            </e-col>
            <!-- </el-row> -->

            <!-- 마감 -->
            <!-- <el-row justify="end"> -->
            <!-- 인원마감 && 신청 가능 시간 && 미팅 종료 이전 -->
            <el-col :span="2" v-if="!isRemain && !isPossible && !isDone">
              <!-- 신청 마감 전 -->
              <el-button round disabled color="#555454" class="me-2 done">
                마감
              </el-button>
            </el-col>
            <!-- </el-row> -->
            <!-- 입장 / 종료 -->
            <!-- <el-row justify="end"> -->
            <!-- ( 신청자 || 작성자 ) && 세션 생성 이후 && 미팅 종료 이전-->
            <el-col :span="2" v-if="(isApplied || isAuthor) && isOpened">
              <el-button
                @click="enterMeeting(this.studyID)"
                round
                color="#FFCD9F"
                class="me-2"
              >
                입장
              </el-button>
            </el-col>
            <el-col v-if="isDone" :span="2">
              <el-button round disabled color="#555454" class="me-2 done">
                종료
              </el-button>
            </el-col>
          </el-row>
          <!-- 스터디 수정 및 삭제 -->
          <el-row
            justify="end"
            :gutter="40"
            style="margin-right: 10px"
            v-if="isAuthor && !isOpened && !isDone"
          >
            <!-- 작성자 && 신청 가능 시간 -->
            <el-col :span="2">
              <el-button
                @click="modifyStudy"
                round
                color="#9DADD8"
                class="me-2"
              >
                수정
              </el-button>
            </el-col>
            <el-col :span="2">
              <el-button @click="deleteOpen" round color="#FF5151" class="me-3"
                >삭제
              </el-button>
            </el-col>
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
  created() {
    this.init();
  },
  data() {
    return {
      fileList: [],
      myImage: "",
      isRemain: true, // 현재 신청자 6명 미만
      isOpened: false, // 화상 세션 입장 가능
      isPossible: false, // 스터디 신청 가능 시간 여부
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
      if (sessionStorage.getItem("ACCESS") != null) {
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;
        this.isApplied = false;
      }

      const params = {
        study_ID: this.studyID,
        user_ID: this.myId,
      };

      await this.getInfo(params);

      await this.getPersonnel(this.studyID);
      await this.getAppliy(this.studyID);
      await this.getCommentList(this.studyID);
      // await this.checkPossible();
      await this.setButton();
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

      let now = moment(); // 현재 시간
      let startTime = moment(this.studyInfo.study_datetime); // 스터디 시작 시간
      let endTime = moment(this.studyInfo.study_datetime).add(
        this.studyInfo.running_time,
        "h"
      ); // 화상회의 종료 시간

      let diff = moment.duration(startTime.diff(now)).asHours(); // 시간 차이
      let closed = moment.duration(endTime.diff(now)).asHours(); // 미팅 종료 판단 변수

      // 신청자 수 마감 시 신청 불가
      if (this.current == this.studyInfo.personnel) {
        // this.isPossible = false;
        this.isRemain = false;
      }

      // 미팅 종료 이후 시각
      console.log(closed + " / " + this.studyInfo.running_time);
      if (closed + this.studyInfo.running_time <= 0) {
        this.isDone = true; // 미팅 종료
        this.isOpened = false; // 입장 불가
        this.isPossible = false; // 신청 불가
      }

      // 미팅 시작 하루 전까지
      if (diff > 24) {
        this.isPossible = true; // 신청 가능
        this.isOpened = false; // 세션 생성 이전
      }
      // 스터디 시작 24시간 이전부터
      else if (diff >= 0 && diff < 24) {
        this.isPossible = false; // 신청 불가
        this.isOpened = true; // 세션 입장 가능
      }
      // 스터디 시작 시간 이후
      else if (diff < 0) {
        this.isPossible = false; // 신청 불가
        this.isOpened = false; // 입장 불가
      }

      console.log(" 글작성자 ? >> " + this.isAuthor);
      console.log(" 스터디 시작 하루 이전인가 >> " + this.isPossible);
      console.log(" 현재 신청자 6명 미만인가 >> " + this.isRemain);
      console.log(" 세션 생성됨 >> " + this.isOpened);
      console.log(" 미팅 종료 >> " + this.isDone);
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
        url: `https://vieweongee.kro.kr:8080/api/study/${this.studyID}/resume`,
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
        .catch(() => {
          // console.log(error);
          ElMessage({
            type: "warning",
            message: "이미지 용량 초과",
          });
        });
      // console.log(this.studyID + " 번에 자기소개서 첨부");
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
  color: black;
}
.el-row {
  /* display: flex; */
  /* align-items: center; */
  /* justify-content: center; */
  font-size: larger;
  margin-bottom: 5px;
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
  width: 100px;
  height: 40px;
  border-radius: 30px;
  font-size: large;
  background-color: #d3daff;
  color: black;
  margin-bottom: 3%;
}

.person-div {
  /* padding: 5px; */
  width: 110px;
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
