<template>
  <div class="comment-item">
    <el-row class="comment-content">
      <el-col :span="21" align-self="start" style="color: gray"
        ><p>higildong | 2023.01.05</p>
      </el-col>
      <el-col v-if="isAuthor" :span="3" align-self="end"
        ><div @click="showInput = true" class="reply-btn"><p>답글 달기</p></div>
      </el-col>
      <el-col v-else :span="3" align-self="end"
        ><div @click="modifyComment">
          <p>수정&nbsp;&nbsp;</p>
        </div>
        <div @click="deleteComment">
          <p>삭제</p>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col>비전공자는 안되나요? </el-col>
    </el-row>
    <!-- <el-row v-if="showInput" class="reply-div">
      <el-col :span="2">
        <img src="@/assets/image/reply_icon.png" style="width: 30px"
      /></el-col>
      <el-col :span="22" id="comment-field">
        <el-input
          v-model="myReply"
          label="댓글을 입력하세요..."
          type="text"
        ></el-input>
        <div id="comment-button">
          <el-button
            round
            color="#9DADD8"
            class="mt-1"
            @click="ReplySubmit(this.studyID, myComment)"
            >등록</el-button
          >
        </div>
      </el-col>
    </el-row> -->
  </div>
</template>

<!-- <template>
  <div class="comment-item">
    <el-row
      class="comment-content"
      v-for="(data, index) in comments"
      :key="index"
    >
      <el-col :span="21" align-self="start" style="color: gray"
        ><p>
          {{ data.user_nickname }}higildong | {{ data.datetime }}2023.01.05
        </p>
      </el-col>
      <el-col v-show="isAuthor" :span="3" align-self="end"
        ><p @click="showInput = true">답글 달기</p>
      </el-col>
      <el-col v-else :span="3" align-self="end"
        ><p>수정&nbsp;&nbsp;</p>
        <p>삭제</p>
      </el-col>
    </el-row>
    <el-row>
      <el-col> {{ data.content }}비전공자는 안되나요? </el-col>
    </el-row>
    <el-row v-if="showInput">
      <el-col id="comment-field">
        <el-input
          v-model="myComment"
          label="댓글을 입력하세요..."
          type="text"
        ></el-input>
        <div id="comment-button">
          <el-button
            round
            color="#9DADD8"
            class="mt-1"
            @click="CommentSubmit(this.studyID, myComment)"
            >등록</el-button
          >
        </div>
      </el-col>
    </el-row>
  </div>
</template> -->

<script>
import { mapState, mapActions } from "vuex";

const studyStore = "studyStore";

export default {
  name: "StudyCommentItem",
  mounted() {
    this.getAllComments();
  },
  computed: {
    ...mapState(studyStore, ["studyID", "commentList"]),
  },
  methods: {
    ...mapActions(studyStore, ["getCommentList"]),

    async getAllComments() {
      await this.getCommentList(this.studyID);
      this.comments = this.commentList;
    },
  },
  data() {
    return {
      isAuthor: true,
      showInput: false,
      comments: [
        {
          depth: 1,
          comment_id: "",
          reply_id: "",
          user_id: "",
          user_nickname: "",
          content: "",
          datetime: "",
        },
      ],
      myReply: {
        depth: 2,
        comment_id: "",
        reply_id: "",
        user_id: "",
        user_nickname: "",
        content: "",
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
