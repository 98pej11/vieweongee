<template>
  <el-container>
    <el-main>
      <el-form style="font-size: large">
        <el-row class="text"
          ><el-icon :size="25"><Lock /></el-icon>비밀번호 입력</el-row
        >
        <el-row :gutter="20">
          <el-col>
            <el-input placeholder="본인 비밀번호 확인" />
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
import { Lock } from "@element-plus/icons-vue";

export default defineComponent({
  name: "MyPwdCheck",
  components: {
    Lock,
  },
});
</script>
<style scoped>
.el-input {
  height: 50px;
}
.el-container {
  margin: 0 auto;
  margin-top: 10%;
  width: 50%;
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
