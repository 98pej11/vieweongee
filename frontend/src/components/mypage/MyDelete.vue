<template>
  <el-container>
    <el-main>
      <el-form style="font-size: medium">
        <div class="text">
          <div>
            <el-icon :size="23"><User /></el-icon>회원 탈퇴 시, 회원님의 모든
            정보가 삭제됩니다.<br />
          </div>
          <div>
            <p style="color: #ff5151">정말 탈퇴하시겠습니까? ㅠ_ㅠ?</p>
          </div>
        </div>
        <el-row class="text"
          ><el-icon :size="23"><Lock /></el-icon>비밀번호 재확인</el-row
        >
        <el-row :gutter="20">
          <el-col>
            <el-input
              placeholder="본인 비밀번호 확인"
              v-model="user.password"
            />
          </el-col>
        </el-row>

        <el-row :gutter="20" style="text-align: center">
          <el-col st>
            <el-button
              color="#E1E6FF"
              class="mt-10 mb-10"
              size="large"
              style="margin: 10% auto; width: 30%"
              @click="withdraw"
            >
              회원 탈퇴
            </el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { defineComponent } from "vue";
import { mapState, mapActions } from "vuex";
import { Lock } from "@element-plus/icons-vue";
import { User } from "@element-plus/icons-vue";

const memberStore = "memberStore";

export default defineComponent({
  name: "MyDelete",
  components: {
    Lock,
    User,
  },
  data(){
    return{
      user: {
        id: "",
        password: "",
      }
    }
  },
  computed: {
    ...mapState(memberStore, ["data", "isLogin"]),
  },
  methods: {
    ...mapActions(memberStore, ["userUpdate", "userDelete", "getUserInfo"]),

    async withdraw() {
      await this.userDelete(this.user);
      alert("회원탈퇴 완료");
      this.$router.push({ name: "main" });
    },
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

.text > div {
  text-align: center;
  margin: 0 auto;
  padding-bottom: 10px;
  font-size: large;
  font-weight: bold;
}
</style>
