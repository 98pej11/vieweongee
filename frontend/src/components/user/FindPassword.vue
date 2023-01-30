<template>
    <el-container>
      <el-main class="main-box">
        <h2>비밀번호 찾기</h2>
        <el-form style="font-size:large">
          <el-row :gutter="20">
            <el-col><p><el-icon :size="20"><Message /></el-icon>이메일</p></el-col>
          </el-row>
  
          <el-row :gutter="20">
            <el-col>
              <el-input placeholder="사용중인 이메일 계정을 입력하세요." />
            </el-col>
          </el-row>
  
          <el-row :gutter="20" style="text-align: center;">
           
            <el-col st>
              <el-button
                color="#9DADD8"
                class="mt-10 mb-10"
                size="large"
                style="margin: 10% auto; width: 30%;"
              >
                확인
              </el-button>
            </el-col>
          </el-row>

        </el-form>
      </el-main>
    </el-container>
  </template>
  
  <script lang="ts">
  import { defineComponent } from "vue";
  import { Message} from "@element-plus/icons-vue";
  export default defineComponent({
    components:{
      Message
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
  }
  
  p {
    margin: 30px 0 10px 0;
  }
  
  
  </style>
  