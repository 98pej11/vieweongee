<template>
  <div>
    <!-- 헤더 -->
    <div id="session-header">
      <div class="inner-area">
        <h3 id="session-title">{{ studyInfo.company }} 면접 스터디</h3>
        <div>
          <el-dialog
            id="dialog"
            style="
              background-color: #e5e1f8;
              border-radius: 5%;
              width: 30%;
              margin: 20 auto;
              text-align: center;
              font-weight: bold;
            "
            v-model="dialogVisible"
            title="' 면접자 : 면접관 ' 비율"
            :before-close="handleClose"
          >
            <!-- 스터디 유형 선택 컴포넌트 -->
            <div class="rate">
              <meeting-rate
                v-if="isLeader && leaderOrder == null"
              ></meeting-rate>
            </div>
            <span>
              <el-button @click="dialogVisible = false" size="large" round
                >완료</el-button
              >
            </span></el-dialog
          >
          <div id="rate-btn">
            <el-button
              text
              @click="dialogVisible = true"
              v-if="isLeader && leaderOrder == null"
            >
              면접 유형 선택
            </el-button>
          </div>
        </div>
        <div v-if="totalTurn > 0">
          <h5>현재 회차 : {{ turn }} / {{ totalTurn }}</h5>
        </div>
        <!-- <div id="timer-display" class="time-box">
              <h5>남은 시간&nbsp;</h5>
            </div> -->
        <div id="timer-display" class="time-box"></div>
      </div>
    </div>

    <!-- 화상 화면 ( 채팅 X )-->
    <div class="notchat main-meeting">
      <transition name="moveInUp">
        <div class="session" v-if="!isShowChat">
          <div class="video-container" style="max-width: 1200px">
            <el-row class="row-bg">
              <el-col>
                <user-video
                  class="invideo"
                  :stream-manager="publisher"
                  @click="updateMainVideoStreamManager(publisher)"
                />
                <user-video
                  class="invideo"
                  v-for="sub in subscribers"
                  :key="sub.stream.connection.connectionId"
                  :stream-manager="sub"
                  @click="updateMainVideoStreamManager(sub)"
                />
              </el-col>
            </el-row>
          </div>
        </div>
      </transition>
    </div>

    <!-- 화상 화면 ( 채팅 O )-->
    <div class="gochat main-meeting">
      <!-- <transition name="moveInUp"> -->
      <!-- <Transition name="slide-fade"> -->
      <div class="session" v-if="isShowChat">
        <div class="video-container">
          <el-row class="row-bg" justify="space-evenly" style="max-width: 1200px">
            <el-col>
              <user-video
                class="invideo"
                :stream-manager="publisher"
                @click="updateMainVideoStreamManager(publisher)"
              />
              <user-video
                class="invideo"
                v-for="sub in subscribers"
                :key="sub.stream.connection.connectionId"
                :stream-manager="sub"
                @click="updateMainVideoStreamManager(sub)"
              />
            </el-col>
          </el-row>
        </div>
      </div>
      <!-- </Transition> -->
      <!-- </Transition> -->

      <!-- <transition name="moveInUp"> -->

      <Transition name="slide-fade">
        <div class="chat-container" v-if="isShowChat">
          <MeetingChatting :session="session" :myUserName="myUserName" />
        </div>
      </Transition>
      <!-- </transition> -->
    </div>
  </div>
</template>

<script>
import { ElMessageBox } from "element-plus";
import { OpenVidu } from "openvidu-browser";
import { mapState, mapMutations, mapActions } from "vuex";
import http from "@/api/http.js";
import UserVideo from "../meeting/UserVideo.vue";
import jwtDecode from "jwt-decode";
import MeetingChatting from "./MeetingChatting.vue";
import MeetingRate from "../meeting/MeetingRate.vue";

import { ref } from "vue";
const meetingStore = "meetingStore";
const studyStore = "studyStore";
const api = http;

export default {
  name: "MeetingVideo",
  components: {
    UserVideo,
    MeetingRate,
    MeetingChatting,
  },
  data() {
    return {
      // OpenVidu objects
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      // Join form
      // myStudyId: "2", //스터디 아이디로 사용
      myUserName: "",

      setTime: 0, //종료시간
      turn: 0,
      dialogVisible: ref(),
    };
  },
  props: {
    myStudyId: String,
  },
  computed: {
    ...mapState(meetingStore, [
      "myId",
      "isLeader",
      "interviewOrder",
      "leaderOrder",
      "interviewOrderList",
      "nowTurn",
      "isInterviewee",
      "isInterviewer",
      "leaderTurn",
      "nowScoreList",
      "totalTurn",
      "isShowChat",
      "isLeaveSession",
      "isEnd",
    ]),
    ...mapState(studyStore, ["studyInfo"]),
  },
  created() {
    this.joinSession(); //세션 참여
    this.getStudyInfo(); //스터디장 설정
    this.turn = parseInt(this.nowTurn) + 1;
  },
  mounted() {
    this.calcRemainTime(); //타이머 설정
  },
  watch: {
    leaderOrder() {
      if (this.leaderOrder != null) {
        //방장이 순서 받았으면 전부에게 시그널 처리
        this.shareInterviewOrder();
      }
    },
    async interviewOrder() {
      if (this.interviewOrder != null) {
        //채점표 불러오기
        await this.getScorecards(this.studyInfo.id);
        //면접 순서 리스트에 저장
        await this.setInterviewList(this.interviewOrder);
        this.showOrderAlert(this.nowTurn);
      }
    },
    // interviewOrderList() {
    //   //리스트가 담기면 현재 회차에서 면접관, 면접자 확인
    //   this.checkMyRole(this.nowTurn);
    // },
    nowTurn() {
      //지금 채점,자소서,총회차 초기화
      this.CLEAR_NOW_LIST();
      //회차가 바뀌는 것 감지하면 이 회차에서 면접관, 면접자 확인
      this.checkMyRole(this.nowTurn);
      //내가 볼 면접자의 채점표 확인
      this.setShowScoreList(this.nowTurn);
      this.turn = parseInt(this.nowTurn) + 1;
    },
    leaderTurn() {
      if (this.isLeader && this.leaderTurn > 0) {
        //방장이고 회차가 정해졌을때 시그널로 현재 회차 보내줌
        this.shareNowTurn(this.leaderTurn);
      }
    },
    async isLeaveSession() {
      console.log("나가기 버튼 눌렀다!!!");
      if (this.isLeaveSession) {
        //나가기 버튼이 true일때
        if (this.isEnd) {
          //면접 모든 회차가 종료됨. 그냥 나가기
          //confirm, status 변경 요청
          await this.changeConfirmAndStatus(this.myStudyId);
          //세션 연결 종료
          this.leaveSession();
          this.SET_IS_END(false);
        } else {
          //채점표 저장하고 나가기
          let isLeave = confirm(
            "면접을 나가시면 기록중인 채점표는 자동 갱신 됩니다.\n면접을 나가시겠습니까?"
          );
          if (isLeave) {
            //yes
            //나가기 버튼이 눌렸으면
            //채점표 갱신
            await this.saveScore(this.myStudyId);
            //confirm, status 변경 요청
            await this.changeConfirmAndStatus(this.myStudyId);
            //세션 연결 종료
            this.leaveSession();
          }
        }
        this.SET_IS_LEAVE_SESSION(false); //나가기 버튼 false로 변경
      }
    },
  },
  methods: {
    ...mapMutations(meetingStore, [
      "SET_INTERVIEW_ORDER",
      "SET_IS_INTERVIEWEE",
      "SET_IS_INTERVIEWER",
      "SET_NOWTURN",
      "SET_IS_LEAVE_SESSION",
      "CLEAR_NOW_LIST",
      "SET_IS_END",
    ]),
    ...mapActions(studyStore, ["getInfo"]),
    ...mapActions(meetingStore, [
      "setLeader",
      "setMyid",
      "setInterviewList",
      "getScorecards",
      "setShowScoreList",
      "saveScore",
      "changeConfirmAndStatus",
    ]),
    joinSession() {
      this.myUserName = jwtDecode(sessionStorage.getItem("ACCESS")).Name;
      console.log("내이름!!!!!!!!!1" + this.myUserName);
      // --- 1) Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- 2) Init a session ---
      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        console.log(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      this.getToken(this.myStudyId).then((token) => {
        //면접 순서 시그널 처리
        this.session.on("signal:interview-order", (event) => {
          console.log("시그널로 받은 면접 순서 >>>>>>>>> ");
          console.log(event.data);
          this.SET_INTERVIEW_ORDER(event.data);

          this.setEndTime(); //종료 시간 설정
        });

        //현재 회차 시그널 처리
        this.session.on("signal:now-turn", (event) => {
          console.log("시그널로 받은 현재 회차 >>>>>>>>> ");
          console.log(event.data);
          //3분 뒤 회차 변경 처리
          //미팅 시작 버튼 누르면 0회차가 전송됨
          //면접 종료 버튼을 누르면 +1 회차가 전송됨

          if (this.nowTurn < this.totalTurn - 1) {
            this.showAlert("3초 뒤 다음 회차 진행. 채점표는 자동 갱신 됩니다.");
            setTimeout(async () => {
              //채점표 PUT
              if (this.isInterviewer) {
                //면접관이라면 채점표 저장 요청
                await this.saveScore(this.myStudyId);
              }
              this.SET_NOWTURN(event.data);
            }, 3000); //지금은 5초
          }
          // this.showOrderAlert(turn + 1);
          else {
            ElMessageBox.confirm(
              "면접이 모두 종료됐습니다. 수고하셨습니다.",
              "🔔알림🔔",
              {
                confirmButtonText: "OK",
              }
            );
            this.SET_IS_END(true);
            setTimeout(async () => {
              //채점표 PUT
              if (this.isInterviewer) {
                //면접관이라면 채점표 저장 요청
                await this.saveScore(this.myStudyId);
              }
            }, 3000); //지금은 3초
          }
          //회차 변경 재알람
          // alert("회차가 변경되었습니다\n현재회차는 " + event.data + "입니다");
        });

        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session
          .connect(token, {
            clientData: this.myId,
            myNickname: this.myUserName,
          })
          .then(() => {
            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publisher
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- 6) Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },
    leaveSession() {
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      // Empty all properties...
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);

      this.$router.push({ name: "main" });
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    async getToken(myStudyId) {
      const sessionId = await this.createSession(myStudyId);
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      const response = await api.post(`/sessions`, {
        customSessionId: sessionId,
      });
      return response.data; // The sessionId
    },

    async createToken(sessionId) {
      api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
      const response = await api.post(`/sessions/${sessionId}/connection`);
      return response.data; // The token
    },

    setMyIdState() {
      //나의 아이디 설정
      var token = sessionStorage.getItem("ACCESS"); //토큰 가져옴
      let decodeToken = jwtDecode(token);

      this.setMyid(decodeToken.Id);
      console.log("나의 아이디 >> " + this.myId);
    },

    async getStudyInfo() {
      //나의 아이디 설정
      this.setMyIdState();

      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;

      const params = {
        study_ID: this.myStudyId,
        user_ID: this.myId,
      };

      await this.getInfo(params); //스터디 상세 정보 가져옴
      console.log("지금 스터디 아이디 >> " + this.studyInfo.id);
      if (this.myId == this.studyInfo.user_id) {
        //나의 아이디와 스터디장이 같으면 리더로 설정
        this.setLeader(true);
      }
      console.log("스터디장입니까? >> " + this.isLeader);
    },

    shareInterviewOrder() {
      //면접 순서 모두에게 전송
      this.session
        .signal({
          data: this.leaderOrder,
          to: [],
          type: "interview-order",
        })
        .then(() => {
          console.log("면접 순서 전송 성공");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    checkMyRole(turn) {
      //현재 회차에서 내가 면접자/면접관인지 확인
      let flag = false;

      for (let i = 0; i < this.interviewOrderList[turn].length; i++) {
        if (this.interviewOrderList[turn][i] == this.myId) {
          //면접자와 나의 아이디 일치
          flag = true;
          break;
        }
      }
      if (flag) {
        this.SET_IS_INTERVIEWEE(true); //면접자 true
        this.SET_IS_INTERVIEWER(false); //면접관 false
      } else {
        this.SET_IS_INTERVIEWEE(false); //면접자 false
        this.SET_IS_INTERVIEWER(true); //면접관 true
      }

      if (turn != 0) {
        if (flag) {
          this.showAlert("당신은 면접자 입니다.");
        } else {
          this.showAlert("당신은 면접관 입니다.");
        }
      }

      console.log(
        "내 역할은 면접자 >> " +
          this.isInterviewee +
          " | 면접관 >> " +
          this.isInterviewer
      );
    },
    shareNowTurn(turn) {
      //시그널로 현재 회차 보내기
      this.session
        .signal({
          data: `${turn}`,
          to: [],
          type: "now-turn",
        })
        .then(() => {
          console.log("현재 회차 전송 성공");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showOrderAlert(turn) {
      //미팅 시작시 알림 ㅇㅇㅇ,ㅇㅇㅇ님이 면접자 입니다. 30초 뒤 면접이 시작됩니다.
      //회차 진행시 1/4회 면접 완료. 3분 후에 채점이 종료됩니다. 채점 내용 기입 후 저장을 눌러주세요.
      let str = "";
      //현재 회차의 면접자, 면접관을 알려줌
      if (turn == 0) {
        if (this.isInterviewee) {
          str += "당신은 면접자 입니다.\n\n곧 면접이 시작됩니다.";
        } else {
          str += "당신은 면접관 입니다.\n\n곧 면접이 시작됩니다.";
        }
      }

      // alert(str);
      this.showAlert(str);
    },
    calcRemainTime() {
      const remainTime = document.getElementById("timer-display");

      const diffDay = () => {
        // const study_datetime = this.studyInfo.study_datetime; //스터디 시작 시간
        const datetime = new Date(this.studyInfo.study_datetime);
        // const datetime = new Date("2023-02-11 23:47");
        // console.log("스터디시간 >> " + datetime);
        //Wed Feb 08 2023 05:14:00 GMT+0900 (한국 표준시)

        const running_time = this.studyInfo.running_time;
        // console.log("진행 시간 >> " + running_time);

        const endtime = new Date(
          datetime.getTime() + running_time * 60 * 60 * 1000
        );
        // console.log("종료 시간 >> " + endtime);

        //설정해야할 시간 = 종료 시간 - 실제 시작 시간
        const now = new Date();
        // console.log("지금 시간 >> " + now);
        this.setTime = endtime.getTime() - now.getTime();
        // console.log("초기 종료시간이에요 >> " + this.setTime);

        const diffHour = String(
          Math.floor((this.setTime / (1000 * 60 * 60)) % 24)
        ).padStart(2, "0");
        const diffMin = String(
          Math.floor((this.setTime / (1000 * 60)) % 60)
        ).padStart(2, "0");
        const diffSec = String(Math.floor((this.setTime / 1000) % 60)).padStart(
          2,
          "0"
        );

        remainTime.innerHTML = `<h5>남은 시간: ${diffHour}:${diffMin}:${diffSec}</h5>`;
      };

      setInterval(diffDay, 1000);
    },
    setEndTime() {
      console.log("종료시간은 뭔가요 >> " + this.setTime);

      // setTimeout으로 강제 세션 종료 설정
      setTimeout(async () => {
        //채점표 PUT 처리
        await this.saveScore(this.myStudyId);
        //confirm, status 변경 요청
        await this.changeConfirmAndStatus(this.myStudyId);
        //연결 강제 종료
        this.leaveSession();
        ElMessageBox.confirm(
          "작성하신 채점표는 자동 갱신 되었습니다.",
          "🔔 진행 시간 종료 🔔",
          {
            confirmButtonText: "OK",
            draggable: true,
          }
        );
      }, this.setTime);
    },
    showAlert(val) {
      ElMessageBox.confirm(val, "🔔 알림 🔔", {
        confirmButtonText: "OK",
        draggable: true,
      });
    },
  },
};
</script>

<style scoped>
/* .time-box {
  background-color: #e5e1f8;
  width: auto;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 5%;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1); */
/* } */

.session {
  height: 80%;
  text-align: center;
  /* overflow-y: scroll; */
  /* border-radius: 15px; */
  display: block;
  justify-content: space-evenly;
  align-items: center;
  margin: 0 auto;
  color: black;
}
.session::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
.session::-webkit-scrollbar-track {
  /* background: #f8f9fa; */
  border-radius: 15px;
}
.session::-webkit-scrollbar-corner {
  /* background: #f8f9fa; */
  border-radius: 15px;
}
.session::-webkit-scrollbar-thumb {
  background: #c6c7e0ce;
  border-radius: 15px;
}
.session::-webkit-scrollbar-button {
  border-radius: 15px;
}
.invideo {
  margin: 10px;
}
.notchat {
  /* background-color: #f5f7fe; */
  display: flex;
  align-content: center;
  justify-content: center;
}
.gochat {
  /* background-color: #f5f7fe; */
  display: flex;
  justify-content: space-between;
}

.chat-container {
  /* margin-right: 2%; */
  z-index: 1;
}
.box {
  margin: 2%;
  width: 30%;
  height: 100px;
  background-color: #e5e1f8;
  border-radius: 5%;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
  align-items: center;
  justify-content: center;
  text-align: center;
} /* 채팅창 애니메이션 */
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}
/* .slide-fade-leave-active {
  transition: all 0.5s;
} */
.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
.rate {
  display: flex;
  align-items: center;
  justify-content: center;
}
.inner-area {
  display: block;
  margin-left: 3%;
  /* justify-content: center; */
  /* align-items: center; */
}
.main-meeting {
  /* height: 10%; */
}
</style>
