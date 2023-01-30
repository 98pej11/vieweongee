<template>
  <el-container>
    <el-main>
      <el-form style="font-size: large">
        <el-row class="text"
          ><el-icon :size="25"><Lock /></el-icon>비밀번호 변경</el-row
        >
        <el-row :gutter="20">
          <el-col>
            <el-input placeholder="변경할 비밀번호 입력" />
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col>
            <el-input placeholder="비밀번호 재확인" />
          </el-col>
        </el-row>
        <el-row class="text"
          ><el-icon :size="25"><User /></el-icon>닉네임 변경</el-row
        >
        <el-row :gutter="20">
          <el-col>
            <el-input placeholder="변경할 닉네임 입력" />
          </el-col>
        </el-row>
        <el-row :gutter="20" style="text-align: center">
          <el-col st>
            <el-button
              color="#E1E6FF"
              class="mt-10 mb-10"
              size="large"
              style="margin: 10% auto; width: 30%"
            >
              수정 완료
            </el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Lock } from "@element-plus/icons-vue";
import { User } from "@element-plus/icons-vue";
export default defineComponent({
  name: "MyModify",
  components: {
    Lock,
    User,
  },
});
</script>
<style scoped>
.el-input {
  height: 50px;
  margin-bottom: 30px;
}
.el-container {
  margin: 0 auto;
  margin-top: 10%;
  width: 80%;
}
.el-icon {
  margin-right: 2%;
  size: large;
}
.el-button {
  font-weight: bold;
}

.text {
  margin: 30px 0 10px 0;
}
</style>
