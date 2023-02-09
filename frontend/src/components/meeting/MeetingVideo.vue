<template>
  <div>
    <!-- 화상 화면 -->
    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">스터디명 >> {{ myStudyId }}</h1>
        <h3>현재 회차 : {{ nowTurn }}</h3>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <!-- <div id="main-video">
        <h3>스트림매니저</h3>
        <user-video :stream-manager="mainStreamManager" />
      </div> -->
      <h2>--- 참가자 목록 ---</h2>
      <div id="video-container">
        <user-video :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)" />
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
    </div>

    <div class="chat-container">
      <MeetingChatting :session="session" :myUserName="myUserName" />
    </div>
  </div>
</template>

<script>
import UserVideo from "../meeting/UserVideo.vue";
import { OpenVidu } from "openvidu-browser";
import { mapState, mapMutations, mapActions } from "vuex";
import http from "../../api/http.js";
import jwtDecode from "jwt-decode";
import MeetingChatting from "./MeetingChatting.vue";

const meetingStore = "meetingStore";
const studyStore = "studyStore";
const api = http;

export default {
  name: "MeetingVideo",
  components: {
    UserVideo,
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
      myStudyId: "2", //스터디 아이디로 사용
      myUserName: "두리두두",
    };
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
    ]),
    ...mapState(studyStore, ["studyInfo"]),
  },
  created() {
    this.joinSession(); //세션 참여
    this.getStudyInfo(); //스터디장 설정
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
      //회차가 바뀌는 것 감지하면 이 회차에서 면접관, 면접자 확인
      this.checkMyRole(this.nowTurn);
      //내가 볼 면접자의 채점표 확인
      this.setShowScoreList(this.nowTurn);
    },
    leaderTurn() {
      if (this.isLeader && this.leaderTurn > 0) {
        //방장이고 회차가 정해졌을때 시그널로 현재 회차 보내줌
        this.shareNowTurn(this.leaderTurn);
      }
    },
  },
  methods: {
    ...mapMutations(meetingStore, [
      "SET_INTERVIEW_ORDER",
      "SET_LEADER_ORDER",
      "SET_IS_INTERVIEWEE",
      "SET_IS_INTERVIEWER",
      "SET_LEADER_TURN",
      "SET_NOWTURN",
    ]),
    ...mapActions(studyStore, ["getInfo"]),
    ...mapActions(meetingStore, ["setLeader", "setMyid", "setInterviewList", "getScorecards", "setShowScoreList"]),
    joinSession() {
      // --- 1) Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- 2) Init a session ---
      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        // console.log(stream);
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
          // //채점표 불러오기
          // console.log("저 채점표 주세요");
          // this.getScorecards(this.myStudyId);

          // //면접 강제 종료 설정
          // let endTime;
          // //종료초 = 러닝시간 - (현재시각 - 스터디시간)

          // setTimeout(() => {
          //   //채점표 PUT 처리
          //   //연결 강제 종료
          // }, endTime);
        });

        //현재 회차 시그널 처리
        this.session.on("signal:now-turn", (event) => {
          console.log("시그널로 받은 현재 회차 >>>>>>>>> ");
          console.log(event.data);
          //3분 뒤 회차 변경 처리
          //미팅 시작 버튼 누르면 0회차가 전송됨
          //면접 종료 버튼을 누르면 +1 회차가 전송됨

          // let turn = this.nowTurn;
          // this.showOrderAlert(turn + 1);
          setTimeout(() => {
            this.SET_NOWTURN(event.data);
          }, 3000); //지금은 5초
          //회차 변경 재알람
          // alert("회차가 변경되었습니다\n현재회차는 " + event.data + "입니다");
        });

        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session
          .connect(token, { clientData: this.myId, myNickname: this.myUserName })
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
            console.log("There was an error connecting to the session:", error.code, error.message);
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

      if (sessionStorage.getItem("ACCESS") != null) this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;

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

      console.log("내 역할은 면접자 >> " + this.isInterviewee + " | 면접관 >> " + this.isInterviewer);
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
      let str = "🔔알림🔔\n\n";
      //현재 회차의 면접자, 면접관을 알려줌
      if (turn == 0) {
        this.nowScoreList.forEach((el) => {
          str += el.id + " ";
        });
        str += "님이 면접자 입니다.\n\n곧 면접이 시작됩니다.";
      } else {
        str += "3분 후에 채점이 종료됩니다.\n면접관은 채점 내용 기입 후 저장을 눌러주세요.\n\n다음 회차는 ";
        this.interviewOrderList[turn].forEach((el) => {
          str += el + " ";
        });
        str += "님이 면접자 입니다.";
      }
      alert(str);
    },
  },
};
</script>

<style></style>
