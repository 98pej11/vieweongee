<template>
  <div class="comment-item" v-for="(data, index) in commentList" :key="index">
    <el-row class="comment-content">
      <el-col :span="21" align-self="start" style="color: gray"
        ><p>{{ data.user_nickname }} 님 | {{ data.datetime }}</p>
      </el-col>
      <el-col :span="3" align-self="end"
        ><p @click="showInput = true">답글 달기</p>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20"> {{ data.content }} </el-col>
      <el-col :span="4" v-if="data.user_id == this.myId"
        ><p>수정&nbsp;&nbsp;</p>
        <p>삭제</p>
      </el-col>
    </el-row>
    <!-- <el-row>
      <el-col id="comment-field">
        <el-input
          v-model="myReply.content"
          label="댓글을 입력하세요..."
          type="text"
        ></el-input>
        <div id="comment-button">
          <el-button round color="#9DADD8" class="mt-1" @click="CommentSubmit()"
            >등록</el-button
          >
        </div>
      </el-col>
    </el-row> -->
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import jwtDecode from "jwt-decode";
// import { ElMessage } from "element-plus";

const studyStore = "studyStore";

export default {
  name: "StudyCommentItem",
  created() {
    this.getAll();
  },
  computed: {
    ...mapState(studyStore, ["studyID", "isCreated", "commentList"]),
  },
  methods: {
    // TODO : 대댓글 등록 액션 등록하기
    ...mapActions(studyStore, ["getCommentList"]),

    async getAll() {
      await this.getCommentList(this.studyID);
      console.log(this.commentList[0].content);

      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;

      // if (this.isCreated) {
      //   ElMessage({
      //     type: "success",
      //     message: "댓글 작성 성공",
      //   });
      // } else {
      //   ElMessage({
      //     type: "error",
      //     message: "댓글 작성 오류",
      //   });
      // }
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
      isAuthor: true,
      showInput: false,
      myId: 0,

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
.comment-content {
  margin-top: 20px;
}

p {
  display: inline-block;
}
.el-input {
  height: 40px;
}

#comment-field {
  position: relative;
  z-index: 1;
}

#comment-button {
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
