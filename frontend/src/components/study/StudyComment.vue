<template>
  <div class="container">
    <div class="main main-box">
      <el-row>
        <el-col :span="4"><p>댓글</p> </el-col>
        <el-col :span="20"><p></p> </el-col>
      </el-row>
      <el-row>
        <el-col id="comment-field">
          <el-input
            v-model="myComment.content"
            type="text"
            @keyup.enter="CommentSubmit"
            maxlength="200"
          >
          </el-input>
          <div id="comment-button">
            <el-button class="sub-btn" @click="CommentSubmit()">등록</el-button>
          </div>
        </el-col>
      </el-row>
      <div v-for="(data, index) in comments" :key="index">
        <!-- 댓글 -->
        <div v-if="data.depth == 1" class="comment-div">
          <StudyCommentItem
            :commentItem="data"
            :key="isUpdate"
            @getAll="getAll"
          ></StudyCommentItem>
        </div>
        <!-- 대댓글 -->
        <div v-if="data.depth == 2" class="comment-div">
          <StudyReplyItem
            :commentItem="data"
            :key="isUpdate"
            @getAll="getAll"
          ></StudyReplyItem>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { ElMessage } from "element-plus";
import StudyCommentItem from "@/components/study/StudyCommentItem.vue";
import StudyReplyItem from "@/components/study/StudyReplyItem.vue";
import jwtDecode from "jwt-decode";

const studyStore = "studyStore";
const commentStore = "commentStore";

export default {
  name: "StudyComment",
  components: {
    StudyCommentItem,
    StudyReplyItem,
  },
  computed: {
    ...mapState(studyStore, ["studyID"]),
    ...mapState(commentStore, ["isComment", "commentList"]),
  },
  created() {
    this.init();
    this.getAll();
  },
  props: {
    commentId: Number,
  },
  data() {
    return {
      isUpdate: false,
      listLen: 0,
      comments: [],
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
    ...mapActions(commentStore, ["getCommentList", "createCommentConfirm"]),

    init() {
      if (sessionStorage.getItem("ACCESS") != null)
        this.myId = jwtDecode(sessionStorage.getItem("ACCESS")).Id;
    },
    async getAll() {
      await this.getCommentList(this.studyID);

      // 댓글 목록이 있을 때
      if (this.isComment) {
        this.comments = [...this.commentList];
      }
      // 마지막 댓글 삭제 후 리렌더링
      else if (!this.isComment && this.commentList.length == 1) {
        this.comments = [];
      }
    },

    // 댓글 등록
    async CommentSubmit() {
      if (this.myComment.content == "") {
        ElMessage({
          type: "warning",
          message: " 입력해주세요",
        });
      }
      // 로그인 유저만 댓글 작성 가능
      else if (sessionStorage.getItem("ACCESS") == null) {
        ElMessage({
          type: "warning",
          message: " 로그인 후 이용해주세요",
        });
        this.$router.push({ name: "login" });
      } else {
        this.params.study_ID = this.studyID;
        this.params.info = this.myComment;
        await this.createCommentConfirm(this.params);

        if (this.isComment) {
          await this.getAll();
          this.myComment.content = "";
          ElMessage({
            type: "success",
            message: "댓글 등록 성공",
          });
        }
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
  height: 50px;
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
.sub-btn {
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
}
</style>
