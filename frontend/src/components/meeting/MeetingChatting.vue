<template>
  <div class="chat">
    <div class="chat-area">
      <div v-for="val in chat" v-bind:key="val.id">
        <div v-if="val.user === myUserName" class="mychat">
          {{ val.text }}
        </div>

        <div v-else class="otherchat">{{ val.user }} : {{ val.text }}</div>
      </div>
      <div class="chat-input">
        <input
          v-model="chattings"
          @keyup.enter="sendMessage"
          placeholder=" 메세지 입력"
          type="text"
          class="message-input"
        />
        <button :disabled="!chattings" @click="sendMessage" type="submit" class="message-submit">보내기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MeetingChatting",
  data() {
    return {
      chattings: "",
      chat: [],
      myUserNick: "",
    };
  },
  props: {
    session: Object,
    myUserName: String,
  },
  watch: {
    chat() {
      setTimeout(() => {
        var chatDiv = document.getElementById("chat-area");
        chatDiv.scrollTo({
          top: chatDiv.scrollHeight - chatDiv.clientHeight,
          behavior: "smooth",
        });
      }, 50);
    },
  },
  created: function () {
    // 방에 들어와 있는 모든 사람이 받는거
    this.session.on("signal:my-chat", (event) => {
      console.log(event);
      const content = event.data.slice(1, -1); // Message
      const temp = event.from.data.split("myNickname");
      const temp2 = temp[temp.length - 1];
      const chatting_user = temp2.substring(3, temp2.length - 2);
      this.chat.push({
        user: chatting_user,
        text: content,
      });
    });
  },
  methods: {
    sendMessage() {
      // post 같은 느낌 = signal
      this.session
        .signal({
          data: JSON.stringify(this.chattings),
          type: "my-chat",
        })
        .then(() => {
          this.chattings = "";
        })
        .catch(() => {});
    },
  },
};
</script>

<style scoped>
.chat {
  width: 30%;
}
.chat-area {
  padding: 1%;
  overflow-y: scroll;
  border: 1px solid #acaeff;
  border-radius: 15px;
  align-items: center;
  height: 75vh;
  width: 55vh;
  /* margin-right: 2.2vw; */
  color: white;
}
.chat-area::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
.chat-area::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 15px;
}
.chat-area::-webkit-scrollbar-corner {
  background: #f8f9fa;
  border-radius: 15px;
}
.chat-area::-webkit-scrollbar-thumb {
  background: #acaeff;
  border-radius: 15px;
}
.chat-area::-webkit-scrollbar-button {
  border-radius: 15px;
}
.mychat {
  position: relative;
  float: right;
  display: block;
  color: #ffffff;
  text-align: right;
  background-color: #797bf8;
  border-radius: 15px 15px 0 15px;
  margin-bottom: 2%;
  margin-left: 4%;
  margin-right: 4%;
  padding: 2%;
  /* 한 줄씩 */
  clear: both;
}
.otherchat {
  position: relative;
  float: left;
  display: block;
  color: #ffffff;
  text-align: left;
  background-color: #797bf8;
  border-radius: 15px 15px 0 15px;
  margin-bottom: 2%;
  margin-left: 4%;
  margin-right: 4%;
  padding: 2%;
  /* 한 줄씩 */
  clear: both;
}
.chat-input {
  margin-top: 125%;
  border: 1px solid #acaeff;
  border-radius: 15px;
  background-color: #f8f9fa;
  padding: 3%;
  position: relative;
  height: 30px;
}
.message-input {
  background: none;
  border: 1px solid #bcbcc0;
  border-radius: 15px;
  outline: none !important;
  resize: none;
  color: black;
  font-size: 1.1em;
  height: 2.5vh;
  padding-right: 2%;
  width: 83%;
  height: 30px;
}
.message-submit {
  position: absolute;
  z-index: 1;
  right: 3%;
  color: #fff;
  border: none;
  background-color: #acaeff;
  font-size: 1em;
  line-height: 1;
  padding: 1.5%;
  border-radius: 15px;
  outline: none !important;
}
.message-submit:hover {
  background: #797bf8;
}
</style>
