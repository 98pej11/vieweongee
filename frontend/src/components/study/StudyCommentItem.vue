<template>
  <div class="comment-item">
    <el-row class="comment-content">
      <el-col :span="20" align-self="start" style="color: gray"
        ><p>{{ commentItem.user_name }} 님 | {{ commentItem.datetime }}</p>
      </el-col>
      <el-col :span="4" align-self="end"
        ><p @click="replyBtn()" style="cursor: pointer">답글 달기</p>
      </el-col>
    </el-row>
    <el-row style="margin-bottom: 30px">
      <!-- 댓글 수정 -->
      <el-col v-if="this.modifying" :span="20" style="color: black">
        <el-input
          label="댓글을 입력하세요..."
          type="text"
          size="large"
          v-model="myComment"
          maxlength="200"
        ></el-input>
      </el-col>
      <el-col v-if="this.modifying" :span="4">
        <el-button
          round
          color="#9DADD8"
          class="mt-1"
          @click="modifyComment(commentItem.comment_id)"
          @keyup.enter="modifyComment(commentItem.comment_id)"
          >완료</el-button
        ></el-col
      >
      <!-- 댓글 내용 -->
      <el-col
        v-if="!this.modifying"
        :span="20"
        style="color: black; width: 100%"
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
          @click="deleteComment(commentItem.comment_id, commentItem.content)"
          style="cursor: pointer"
        >
          삭제
        </p>
      </el-col>
    </el-row>

    <!-- 대댓글 등록 -->
    <el-row v-if="this.showInput" justify="end">
      <el-col :span="2" style="color: black">
        <img src="@/assets/image/reply_icon.png"
      /></el-col>
      <el-col :span="22" class="reply-field">
        <el-input
          v-model="myReply"
          label="댓글을 입력하세요..."
          type="text"
          maxlength="200"
        ></el-input>
        <div class="reply-button">
          <el-button
            round
            color="#9DADD8"
            class="mt-1"
            @click="replySubmit(commentItem.comment_id, myReply)"
            @keypress.enter="replySubmit(commentItem.comment_id, myReply)"
            >등록</el-button
          >
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import jwtDecode from "jwt-decode";
import { mapState, mapActions } from "vuex";
import { ElMessage } from "element-plus";

const studyStore = "studyStore";
const commentStore = "commentStore";

export default {
  name: "StudyCommentItem",
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
      this.$emit("getAll");
      this.isUpdate = false;
      this.myReply = "";
    },
  },

  methods: {
    // TODO : 대댓글 등록 액션 등록하기
    ...mapActions(commentStore, [
      "getCommentList",
      "modifyCommentConfirm",
      "deleteCommentConfirm",
      "createReplyConfrim",
    ]),
    // 유저 아이디
    getMyId() {
      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;
    },
    // 댓글 수정 input 태그
    modifyShow(content) {
      this.myReply = "";
      this.myComment = content;
      this.modifying = true;
    },
    // 댓글 수정 axios
    modifyComment(idx) {
      this.modifying = false;

      const params = {
        study_ID: this.studyID,
        comment_ID: idx,
        info: { content: this.myComment },
      };
      this.modifyCommentConfirm(params);
      if (this.isComment) {
        this.isUpdate = true;
        this.myReply = "";
        this.showSuccess("수정");
      } else {
        this.showFail("수정");
      }
    },

    // 댓글 삭제
    async deleteComment(idx, content) {
      const params = {
        study_ID: this.studyID,
        comment_ID: idx,
        comment: { content: content },
      };
      await this.deleteCommentConfirm(params);
      if (this.isComment) {
        this.isUpdate = true;
        this.showSuccess("삭제");
      } else {
        this.showFail("삭제");
      }
    },

    replyBtn() {
      if (this.showInput == false) this.showInput = true;
      else this.showInput = false;
    },

    // 대댓글 등록
    async replySubmit(idx, info) {
      const params = {
        study_ID: this.studyID,
        comment_ID: idx,
        reply: { content: info },
      };
      await this.createReplyConfrim(params);
      if (this.isComment) {
        this.showInput = false;
        this.isUpdate = true;
        this.showSuccess("등록");
      } else {
        this.showFail("등록");
      }
    },

    showSuccess(val) {
      ElMessage({
        type: "success",
        message: "댓글 " + val + " 성공",
      });
    },
    showFail(val) {
      ElMessage({
        type: "warning",
        message: "댓글 " + val + " 성공",
      });
    },
  },
  data() {
    return {
      isUpdate: false,
      isAuthor: true,
      showInput: false,
      modifying: false,
      myId: 0,
      myComment: "", // 수정할 댓글 내용
      comments: [],

      // 대댓글 등록 내용
      myReply: "",
    };
  },
};
</script>

<style scoped>
.comment-item {
  font-size: large;
}
.comment-content {
  margin-top: 40px;
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
