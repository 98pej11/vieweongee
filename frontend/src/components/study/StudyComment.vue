<template>
  <div class="container">
    <div class="main main-box">
      <el-row>
        <el-col style="color: black; font-weight: bold; margin-bottom: 20px"
          >댓글</el-col
        >
      </el-row>
      <el-row>
        <el-col id="comment-field">
          <el-input
            v-model="myComment.content"
            label="댓글을 입력하세요..."
            type="text"
          ></el-input>
          <div id="comment-button">
            <el-button
              round
              color="#9DADD8"
              class="mt-1"
              @click="CommentSubmit()"
              >등록</el-button
            >
          </div>
        </el-col>
      </el-row>
      <!-- <StudyCommentItem></StudyCommentItem> -->
    </div>
  </div>
</template>

<script>
// import StudyCommentItem from "@/components/study/StudyCommentItem.vue";
import { mapState, mapActions } from "vuex";

const studyStore = "studyStore";

export default {
  name: "StudyComment",
  components: {
    // StudyCommentItem,
  },
  computed: {
    ...mapState(studyStore, ["isError", "studyID", "commentList"]),
  },
  data() {
    return {
      myComment: {
        // depth: 1,
        // user_id: 0,
        // user_nickname: "",
        // comment_id: 0,
        // reply_id: 0,
        content: "",
      },
    };
  },
  methods: {
    ...mapActions(studyStore, ["createCommentConfirm"]),

    // 유저 닉네임 얻어와야함

    // 댓글 작성
    async CommentSubmit() {
      console.log("vuecomponent : " + JSON.stringify(this.myComment));
      await this.createCommentConfirm(this.studyID, this.myComment);
    },
  },
};
</script>

<style scoped>
.container {
  margin: 0 auto;
  margin-top: 5%;
  width: 45%;
}
.main {
  padding: 10%;
  margin-bottom: 5%;
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
</style>
