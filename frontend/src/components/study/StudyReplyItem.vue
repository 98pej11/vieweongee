<template>
  <div class="comment-item">
    <el-row class="comment-content" justify="end">
      <el-col :span="4" style="color: black; float: right">
        <img src="@/assets/image/reply_icon.png"
      /></el-col>
      <el-col :span="20" align-self="start" style="color: gray"
        ><p>{{ commentItem.user_name }} 님 | {{ commentItem.datetime }}</p>
      </el-col>
    </el-row>
    <!-- 대댓글 수정 -->
    <el-row justify="end">
      <el-col v-if="this.modifying" :span="16" style="color: black">
        <el-input type="text" size="large" v-model="myReply"></el-input>
      </el-col>
      <el-col v-if="this.modifying" :span="4">
        <el-button
          round
          color="#9DADD8"
          class="mt-1"
          @click="modifyReply(commentItem.comment_id, commentItem.reply_id)"
          @keyup.enter="
            modifyReply(commentItem.comment_id, commentItem.reply_id)
          "
          >완료</el-button
        ></el-col
      >
      <el-col
        v-if="!this.modifying"
        :span="16"
        justify="end"
        style="color: black"
      >
        {{ commentItem.content }}
      </el-col>
      <el-col
        :span="4"
        style="color: gray"
        v-if="!this.modifying && commentItem.user_id == this.myId"
        ><p @click="modifyShow(commentItem.content)" style="cursor: pointer">
          수정&nbsp;&nbsp;
        </p>
        <p
          @click="deleteReply(commentItem.comment_id, commentItem.reply_id)"
          style="cursor: pointer"
        >
          삭제
        </p>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import jwtDecode from "jwt-decode";
import { mapState, mapActions } from "vuex";
// import { ElMessage } from "element-plus";

const studyStore = "studyStore";
const commentStore = "commentStore";

export default {
  name: "StudyReplyItem",
  computed: {
    ...mapState(studyStore, ["studyID"]),
    ...mapState(commentStore, ["isComment"]),
  },
  props: {
    commentItem: Object,
    reload: Function,
  },
  created() {
    this.getMyId();
  },
  watch: {
    isUpdate() {
      console.log("바껐떠`");
      this.$emit("getAll");
      this.isUpdate = false;
    },
  },

  methods: {
    // TODO : 대댓글 등록 액션 등록하기
    ...mapActions(commentStore, [
      "getCommentList",
      "modifyReplyConfirm",
      "deleteReplyConfirm",
    ]),
    // 유저 아이디
    getMyId() {
      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;
    },
    // 대댓글 수정 input 태그
    modifyShow(content) {
      this.myReply = content;
      this.modifying = true;
    },

    // 대댓글 수정 axios
    modifyReply(idx, reply_id) {
      this.modifying = false;

      const params = {
        study_id: this.studyID,
        comment_id: idx,
        reply_id: reply_id,
        info: { content: this.myReply },
      };
      this.modifyReplyConfirm(params);
      if (this.isComment) {
        this.isUpdate = true;
        this.showInput = false;
      }
    },

    // 대댓글 삭제
    async deleteReply(idx, reply_id) {
      const params = {
        study_id: this.studyID,
        comment_id: idx,
        reply_id: reply_id,
      };
      await this.deleteReplyConfirm(params);
      if (this.isComment) {
        this.isUpdate = true;
        this.myReply = "";
      }
    },
  },
  data() {
    return {
      isUpdate: false,
      isAuthor: true,
      showInput: false,
      modifying: false,
      myId: 0,
      comments: [],
      myReply: "", // 대댓글 등록 내용
    };
  },
};
</script>

<style scoped>
.comment-item {
  font-size: large;
}
.comment-content {
  margin-top: 20px;
}

p {
  display: inline-block;
}
.el-input {
  height: 40px;
}

.comment-field {
  position: relative;
  z-index: 1;
}

.comment-button,
.reply-button {
  z-index: 2;
  position: absolute;
  top: 5px;
  right: 5px;
}

button {
  color: white;
  z-index: 6;
  /* width: 100%; */
}

.reply-div {
  margin-top: 20px;
}
</style>
