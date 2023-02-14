<template>
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
          @click="replySubmit(data.comment_id, myReply.content)"
          @keypress.ctrl.enter="replySubmit(data.comment_id, myReply.content)"
          >등록</el-button
        >
      </div>
    </el-col>
  </el-row>
</template>

<script>
export default {};
</script>

<style></style>
