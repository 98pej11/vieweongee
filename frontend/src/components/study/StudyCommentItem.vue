<template>
  <div class="comment-item">
    <!-- <div v-for="(data, index) in comments" :key="index"> -->
    <el-row class="comment-content">
      <el-col :span="20" align-self="start" style="color: gray"
        ><p>{{ commentItem.user_nickname }} 님 | {{ commentItem.datetime }}</p>
      </el-col>
      <el-col :span="4" align-self="end"
        ><p @click="replyBtn()">답글 달기</p>
      </el-col>
    </el-row>

    <el-row>
      <el-col v-if="this.modifying" :span="20" style="color: black">
        <el-input label="댓글을 입력하세요..." type="text"></el-input>
      </el-col>
      <el-col v-if="this.modifying" :span="4">
        <el-button
          round
          color="#9DADD8"
          class="mt-1"
          @click="modifyComment(commentItem.comment_id)"
          >완료</el-button
        ></el-col
      >

      <el-col
        v-if="!this.modifying"
        :span="20"
        style="color: black"
        v-model="modifyCotent"
      >
        {{ commentItem.content }}
      </el-col>
      <el-col
        :span="4"
        style="color: gray"
        v-if="!this.modifying && commentItem.user_id == this.myId"
        ><p @click="modifyShow(commentItem.content)">수정&nbsp;&nbsp;</p>
        <p @click="deleteComment(commentItem.comment_id, commentItem.content)">
          삭제
        </p>
      </el-col>
    </el-row>

    <el-row v-if="this.showInput" justify="end">
      <el-col :span="2" style="color: black">
        <img src="@/assets/image/reply_icon.png"
      /></el-col>
      <el-col :span="22" class="reply-field">
        <el-input
          v-model="myReply.content"
          label="댓글을 입력하세요..."
          type="text"
        ></el-input>
        <div class="reply-button">
          <el-button
            round
            color="#9DADD8"
            class="mt-1"
            @click="replySubmit(commentItem.comment_id, myReply.content)"
            @keypress.enter="
              replySubmit(commentItem.comment_id, myReply.content)
            "
            >등록</el-button
          >
        </div>
      </el-col>
    </el-row>
  </div>
  <!-- </div> -->
</template>

<script>
import jwtDecode from "jwt-decode";
import { mapState, mapActions } from "vuex";
// import { ElMessage } from "element-plus";

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
    this.myComment = this.commentItem;
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
      "modifyCommentConfirm",
      "deleteCommentConfirm",
      "createReplyConfrim",
    ]),
    getMyId() {
      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;
    },
    // 댓글 수정
    modifyShow(content) {
      this.modifyCotent = content;
      this.modifying = true;
    },
    modifyComment(idx) {
      this.modifying = false;

      const params = {
        study_ID: this.studyID,
        comment_ID: idx,
        info: { content: this.modifyCotent },
      };

      this.modifyCommentConfirm(params);
      if (this.isComment) {
        console.log("수정 성공");
        this.isUpdate = true;
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
      }
    },

    replyBtn() {
      if (this.showInput == false) this.showInput = true;
      else this.showInput = true;
    },

    // 대댓글 등록
    async replySubmit(idx, info) {
      const params = {
        study_ID: this.studyID,
        comment_ID: idx,
        reply: { content: info },
      };
      await this.createReplyConfrim(params);

      this.isUpdate = true;
    },

    // 대댓글 등록하기
    // async CommentSubmit() {
    //   console.log("브이모델?" + this.myComment.content);
    //   await this.createCommentConfirm(this.myComment.content);
    //   // 댓글이냐, 대댓글이냐, 판단 -> depth
    // },
  },
  data() {
    return {
      isUpdate: false,
      isAuthor: true,
      showInput: false,
      modifying: false,
      modifyCotent: "", // 수정한 댓글 내용
      myId: 0,

      comments: [],

      myComment: {},

      // 새로 등록할 댓글
      myReply: {
        // 깊이 구분해주어야 함
        depth: 2,
        comment_id: "",
        reply_id: "",
        user_id: "",
        user_nickname: "",
        content: "예시댓글",
        datetime: "",
      },
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
