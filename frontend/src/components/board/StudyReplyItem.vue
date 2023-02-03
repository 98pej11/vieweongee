<template>
  <div class="reply-item">
    <el-row class="reply-content">
      <el-col :span="21" align-self="start" style="color: gray"
        ><p>higildong | 2023.01.05</p>
      </el-col>
      <el-col v-if="isAuthor == false" :span="3" align-self="end"
        ><p>답글 달기</p>
      </el-col>
      <el-col v-else :span="3" align-self="end"
        ><p>수정&nbsp;&nbsp;</p>
        <p>삭제</p>
      </el-col>
    </el-row>
    <el-row>
      <el-col> 비전공자는 안되나요? </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "StudyReplyItem",
  data() {
    return {
      isAuthor: true,
    };
  },
};
</script>

<style scoped>
.reply-content {
  margin-top: 20px;
}

p {
  display: inline-block;
}
</style>
