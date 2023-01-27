<template>
  <el-container>
    <el-main class="main-box">
      <h2>회원가입</h2>
      <el-form style="font-size:large">
        <el-row :gutter="20">
          <el-col><p><el-icon :size="20"><Message /></el-icon>이메일</p></el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="18">
            <el-input placeholder="이메일 규칙" />
          </el-col>
          <el-col :span="6">
            <el-button
              color="#9DADD8"
              size="large"
              style="margin: 3% auto; width: 100%"
            >
              중복확인
            </el-button>
          </el-col>
        </el-row>

        <el-row :gutter="20" style="margin-top: 3%">
          <el-col :span="18">
            <el-input placeholder="이메일 인증번호" />
          </el-col>
          <el-col :span="6">
            <el-button
              color="#9DADD8"
              class="mt-10 mb-10"
              size="large"
              style="margin: 3% auto; width: 100%"
            >
              확인
            </el-button>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col>
            <p><el-icon :size="20"><Lock /></el-icon>비밀번호</p>
          </el-col>
          <el-col>
            <el-input placeholder="비밀번호 규칙" />
          </el-col>
          <el-col style="margin-top: 3%">
            <el-input placeholder="비밀번호 재확인" />
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col><p><el-icon :size="20"><User /></el-icon>닉네임</p></el-col>
          <el-col><el-input placeholder="닉네임 규칙" /></el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col>
            <el-button
              color="#9DADD8"
              class="mt-10 mb-10"
              size="large"
              style="margin: 10% auto; width: 100%"
            >
              완료
            </el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Message,Lock,User } from "@element-plus/icons-vue";
export default defineComponent({
  components:{
    Message,Lock,User
  }
});
</script>

<style scoped>
h2 {
  text-align: center;
}
.el-input {
  height: 50px;
}
.el-container {
  margin: 0 auto;
  margin-top: 5%;
  width: 45%;
}
.el-icon{
  margin-right: 2%;
  size: large;
}
.el-button {
  color: white;
  display: inline-flex;
}

p {
  margin: 30px 0 10px 0;
}


</style>
