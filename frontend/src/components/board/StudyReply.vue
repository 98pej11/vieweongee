<template>
  <el-container>
    <el-main class="main-box">
      <el-row>
        <el-col class="font-weight-bold">댓글</el-col>
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
    </el-main>
  </el-container>
</template>

<script>
import StudyReplyItem from "@/components/board/StudyReplyItem.vue";
export default { name: "StudyReply", components: { StudyReplyItem } };
</script>

<style scoped>
.el-container {
  margin: 0 auto;
  width: 45%;
}

#reply-field {
  position: relative;
  z-index: 1;
}

#reply-button {
  z-index: 2;
  position: absolute;
  top: 14%;
  right: 5%;
}

button {
  color: white;
  z-index: 6;
  /* width: 100%; */
}
</style>
