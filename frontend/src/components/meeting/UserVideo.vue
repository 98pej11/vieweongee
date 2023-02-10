<template>
  <div class="userVideo" v-if="streamManager">
    <div>
      <ov-video :stream-manager="streamManager" />
      <div>내 닉네임 좀 넣어주세요</div>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
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
</style>
