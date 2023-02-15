<template>
  <div class="userVideo" v-if="streamManager">
    <div id="video-container">
      <p>{{ myNickname }}님</p>
      <ov-video id="video" :stream-manager="streamManager" />
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";
import { mapState, mapMutations } from "vuex";

const meetingStore = "meetingStore";

export default {
  name: "UserVideo",
  data() {
    return {
      nickname: null,
    };
  },

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },

  computed: {
    ...mapState(meetingStore, ["nicknameList"]),
    myNickname() {
      const { myNickname } = this.getConnectionData();
      const { clientData } = this.getConnectionData();
      let obj = {
        id: clientData,
        nickname: myNickname,
      };
      this.SET_NICKNAME_LIST(obj);
      return myNickname;
    },
  },

  methods: {
    ...mapMutations(meetingStore, ["SET_NICKNAME_LIST"]),
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>
<style scoped>
.userVideo {
  display: inline-flex;
}
#video-container {
  background-color: white;
  border-radius: 10%;
  padding: 10px;
}
#video {
  border-radius: 10%;
}
</style>
