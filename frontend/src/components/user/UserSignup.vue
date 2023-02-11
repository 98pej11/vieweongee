<template>
  <el-container>
    <el-main class="main-box">
      <h2>회원가입</h2>
      <el-form
        style="font-size: large"
        @submit.prevent="formSubmit"
        method="post"
      >
        <!-- 이메일 -->
        <el-row :gutter="20">
          <el-col
            ><p>
              <el-icon :size="20"><Message /></el-icon>이메일
            </p></el-col
          >
        </el-row>

        <el-row :gutter="20">
          <el-col :span="18">
            <el-input placeholder="이메일 규칙" v-model="user.email" />
          </el-col>
          <el-col :span="6">
            <el-button
              color="#9DADD8"
              size="large"
              style="margin: 3% auto; width: 100%"
              @click="emailcheck"
            >
              중복확인
            </el-button>
          </el-col>
        </el-row>

        <!-- 이메일 인증번호 어떻게 받아요? -->
        <el-row :gutter="20" style="margin-top: 3%">
          <el-col :span="18">
            <el-input placeholder="이메일 인증번호" v-model="emailCheck" />
          </el-col>
          <el-col :span="6">
            <el-button
              color="#9DADD8"
              class="mt-10 mb-10"
              size="large"
              style="margin: 3% auto; width: 100%"
              @click="codeCheck"
            >
              확인
            </el-button>
          </el-col>
        </el-row>

        <!-- 비밀번호 -->
        <el-row :gutter="20">
          <el-col>
            <p>
              <el-icon :size="20"><Lock /></el-icon>비밀번호
            </p>
          </el-col>
          <el-col>
            <el-input placeholder="비밀번호 규칙" v-model="user.password" />
          </el-col>
          <el-col style="margin-top: 3%">
            <el-input
              placeholder="비밀번호 재확인"
              v-model="user.passwordCheck"
            />
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col
            ><p>
              <el-icon :size="20"><User /></el-icon>닉네임
            </p></el-col
          >
          <el-col
            ><el-input placeholder="닉네임 규칙" v-model="user.name"
          /></el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col>
            <el-button
              color="#9DADD8"
              class="mt-10 mb-10"
              size="large"
              style="margin: 10% auto; width: 100%"
              type="submit"
              @click="join"
            >
              완료
            </el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { ElMessageBox } from "element-plus";
import { Message, Lock, User } from "@element-plus/icons-vue";
import { mapActions, mapState } from "vuex";

const memberStore = "memberStore";

export default {
  data() {
    return {
      emailCheck: "",

      user: {
        email: "",
        password: "",
        passwordCheck: "",
        name: "",
      },
    };
  },
  components: {
    Message,
    Lock,
    User,
    // ValidationObserver,
    // ValidationProvider,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "code"]),
  },

  methods: {
    ...mapActions(memberStore, ["userJoin", "checkEmail"]),

    async join() {
      console.log("vuecomponent : " + JSON.stringify(this.user));
      await this.userJoin(this.user);
      ElMessageBox.alert("회원가입이 완료되었습니다. 환영합니다.", "알림", {
        confirmButtonText: "확인",
      });
      this.$router.push({ name: "login" });
    },

    async emailcheck() {
      await this.checkEmail(this.user);
    },

    codeCheck() {
      if (this.code === this.emailCheck) {
        console.log("인증코드 확인");
      } else {
        console.log("인증 재확인 요망");
      }
    },
  },
};
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
.el-icon {
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
