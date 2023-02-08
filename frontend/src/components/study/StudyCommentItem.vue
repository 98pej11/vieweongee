<template>
  <div class="comment-item">
    <el-row
      class="comment-content"
      v-for="(data, index) in commentList"
      :key="index"
    >
      <el-col :span="21" align-self="start" style="color: gray"
        ><p>
          {{ data.user_nickname }}higildong | {{ data.datetime }}2023.01.05
        </p>
      </el-col>
      <el-col :span="3" align-self="end"
        ><p @click="showInput = true">답글 달기</p>
      </el-col>
      <el-col :span="3" align-self="end"
        ><p>수정&nbsp;&nbsp;</p>
        <p>삭제</p>
      </el-col>
    </el-row>
    <el-row>
      <el-col> {{ data.content }}비전공자는 안되나요? </el-col>
    </el-row>
    <el-row>
      <el-col id="comment-field">
        <el-input
          v-model="myComment.content"
          label="댓글을 입력하세요..."
          type="text"
        ></el-input>
        <div id="comment-button">
          <el-button round color="#9DADD8" class="mt-1" @click="CommentSubmit()"
            >등록</el-button
          >
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const studyStore = "studyStore";

export default {
  name: "StudyCommentItem",
  mounted() {
    this.getAll();
  },
  computed: {
    ...mapState(studyStore, ["studyID", "commentList"]),
  },
  methods: {
    ...mapActions(studyStore, ["getCommentList", "createCommentConfirm"]),

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

      // 새로 등록할 댓글
      myComment: {
        // 깊이 구분해주어야 함
        depth: 1,
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
