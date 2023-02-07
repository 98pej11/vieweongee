<template>
  <div>
    <!-- 화상 화면 -->
    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">스터디명 >> {{ myStudyId }}</h1>
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
  </div>
</template>

<script>
import UserVideo from "../meeting/UserVideo.vue";
import { OpenVidu } from "openvidu-browser";
import { mapState, mapMutations } from "vuex";
import http from "../../api/http.js";

const meetingStore = "meetingStore";
const api = http;

export default {
  name: "MeetingVideo",
  components: {
    UserVideo,
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
      myStudyId: "11", //스터디 아이디로 사용
    };
  },
  computed: {
    ...mapState(meetingStore, ["myId", "storePublisher"]),
  },
  created() {
    this.joinSession(); //세션 참여
  },
  methods: {
    ...mapMutations(meetingStore, ["SET_STORE_PUBLISHER"]),
    joinSession() {
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
        // this.session.on("signal:interview-order", (event) => {
        //   // console.log("시그널로 받은 면접 순서 >>>>>>>>> ");
        //   // console.log(event.data);
        //   this.SET_INTERVIEW_ORDER(event.data);
        // });

        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session
          .connect(token, { clientData: this.myId })
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

            //store에도 퍼블리셔 등록
            this.SET_STORE_PUBLISHER(this.publisher);
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

      //store에도 퍼블리셔 등록 해제
      this.SET_STORE_PUBLISHER(undefined);

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
      const response = await api.post(`api/sessions`, { customSessionId: sessionId });

      //   const response = await axios.post(
      //     APPLICATION_SERVER_URL + "api/sessions",
      //     { customSessionId: sessionId },
      //     {
      //       headers: { "Content-Type": "application/json" },
      //     }
      //   );
      return response.data; // The sessionId
    },

    async createToken(sessionId) {
      const response = await api.post(`api/sessions/${sessionId}/connection`);

      //   const response = await axios.post(
      //     APPLICATION_SERVER_URL + "api/sessions/" + sessionId + "/connection",
      //     {},
      //     {
      //       headers: { "Content-Type": "application/json" },
      //     }
      //   );
      //   console.log(response.data);
      return response.data; // The token
    },
  },
};
</script>

<style></style>
