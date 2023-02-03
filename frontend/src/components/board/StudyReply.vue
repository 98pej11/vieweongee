<template>
  <div class="container">
    <div class="main main-box">
      <el-row>
        <el-col style="color: black; font-weight: bold; margin-bottom: 20px"
          >댓글</el-col
        >
      </el-row>
      <el-row>
        <el-col id="reply-field">
          <el-input
            v-model="reply"
            label="댓글을 입력하세요..."
            type="text"
          ></el-input>
          <div id="reply-button">
            <el-button round color="#9DADD8" class="mt-1">등록</el-button>
          </div>
        </el-col>
      </el-row>
      <StudyReplyItem></StudyReplyItem>
    </div>
  </div>
</template>

<script>
import StudyReplyItem from "@/components/board/StudyReplyItem.vue";
export default { name: "StudyReply", components: { StudyReplyItem } };
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

#reply-field {
  position: relative;
  z-index: 1;
}

#reply-button {
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
