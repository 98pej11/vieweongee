<template>
  <div class="container">
    <div class="main main-box">
      <el-row>
        <el-col :span="4"><p>댓글</p> </el-col>
        <el-col :span="20"><p></p> </el-col>
      </el-row>
      <el-row>
        <el-col id="comment-field">
          <el-input v-model="myComment.content" type="text"></el-input>
          <div id="comment-button">
            <el-button class="subbtn" @click="CommentSubmit()">등록</el-button>
          </div>
        </el-col>
      </el-row>
      <StudyCommentItem></StudyCommentItem>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { ElMessage } from "element-plus";
import StudyCommentItem from "@/components/study/StudyCommentItem.vue";

const studyStore = "studyStore";
const commentStore = "commentStore";

export default {
  name: "StudyComment",
  components: {
    StudyCommentItem,
  },
  computed: {
    ...mapState(studyStore, ["isCreated", "studyID"]),
  },
  data() {
    return {
      myComment: {
        content: "",
      },
      params: {
        study_ID: 0,
        info: {},
      },
    };
  },
  methods: {
    ...mapActions(commentStore, ["createCommentConfirm"]),

    // 댓글 등록
    async CommentSubmit() {
      if (this.myComment.content == "") {
        ElMessage({
          type: "warning",
          message: " 입력해주세요",
        });
      } else {
        this.params.study_ID = this.studyID;
        this.params.info = this.myComment;
        await this.createCommentConfirm(this.params);

        // if (!this.isCreated) {
        //   ElMessage({
        //     type: "error",
        //     message: "로그인 후 이용해주세요",
        //   });
        //   this.$router.push({ name: "login" });
        // }
      }
    },
  },
};
</script>

<style scoped>
.container {
  margin: 0 auto;
  margin-top: 5%;
  width: 60%;
}
.el-row {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: larger;
}
.el-row > :nth-child(1) {
  color: gray;
}
.main {
  padding: 7%;
  margin-bottom: 5%;
}

.el-input {
  height: 60px;
  font-size: large;
  border-radius: 10%;
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
.subbtn {
  width: 100%;
  height: 40px;
  border-radius: 10%;
  font-size: large;
  background-color: #d3daff;
  color: gray;
  margin-bottom: 3%;
}
button {
  color: white;
  z-index: 6;
  /* width: 100%; */
}
</style>
