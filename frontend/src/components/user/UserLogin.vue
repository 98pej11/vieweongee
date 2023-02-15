<template>
  <el-container>
    <el-main class="main-box">
      <h2>로그인</h2>
      <ValidationObserver>
        <el-form
          style="font-size: large"
          @submit.prevent="formSubmit"
          method="post"
        >
          <el-row>
            <a
              ><img
                src="@/assets/image/kakao.png"
                :width="80"
                @click="kakaologin()"
            /></a>
            <a
              ><img
                src="@/assets/image/naver.png"
                :width="90"
                @click="naverlogin()"
            /></a>
            <a
              ><img
                src="@/assets/image/google.png"
                :width="70"
                :height="70"
                style="margin-top: 8%"
            /></a>
          </el-row>
          <p class="hr-sect" style="margin-top: 10%">OR</p>

          <!-- 이메일 -->
          <!-- <ValidationProvider ref="refEmail" rules="required|email"> -->
          <el-row :gutter="20">
            <el-col
              ><p>
                <el-icon :size="20"><Message /></el-icon>이메일
              </p></el-col
            >
          </el-row>
          <el-row :gutter="20">
            <el-col
              ><el-input
                placeholder="이메일 입력"
                v-model="user.email"
                @keyup.enter="confirm"
            /></el-col>
          </el-row>
          <!-- </ValidationProvider> -->

          <!-- 비밀번호 -->
          <!-- <ValidationProvider ref="refPassword" rules="required|min:8|max:20|alpha_dash"> -->
          <el-row :gutter="20">
            <el-col>
              <p>
                <el-icon :size="20"><Lock /></el-icon>비밀번호
              </p>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col
              ><el-input
                type="password"
                placeholder="비밀번호 입력"
                v-model="user.password"
                @keyup.enter="confirm"
            /></el-col>
          </el-row>
          <!-- </ValidationProvider> -->

          <el-row :gutter="20">
            <el-col>
              <router-link to=""></router-link>
              <el-button
                color="#9DADD8"
                class="mt-10 mb-10"
                size="large"
                style="margin: 10% auto; width: 100%"
                type="submit"
                @click="confirm"
              >
                로그인
              </el-button>
            </el-col>
          </el-row>

          <el-row :gutter="20" style="font-size: medium; text-align: center">
            <el-col>
              <router-link to="/findpwd"
                ><p style="margin: 0%">비밀번호 찾기</p></router-link
              >
            </el-col>
          </el-row>

          <el-row :gutter="20" style="font-size: medium; text-align: center">
            <el-col
              ><p>
                계정이 없으신가요?
                <router-link to="/signup">회원가입</router-link>
              </p></el-col
            >
          </el-row>
        </el-form>
      </ValidationObserver>
    </el-main>
  </el-container>
</template>

<script>
import { ElMessageBox } from "element-plus";
import { Message, Lock } from "@element-plus/icons-vue";
import { mapState, mapActions } from "vuex";
// import { ValidationObserver, ValidationProvider } from 'vee-validate';
import http from "../../api/http";

const memberStore = "memberStore";

export default {
  data() {
    return {
      user: {
        email: "",
        password: "",
        name: "",
        message: "",
      },
    };
  },
  components: {
    Message,
    Lock,
    // ValidationObserver,
    // ValidationProvider,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "userJoin"]),

    async confirm() {
      await this.userConfirm(this.user);
      // let token = sessionStorage.getItem("ACCESS");
      if (this.isLogin) {
        // await this.getUserInfo(token);
        ElMessageBox.confirm("로그인이 완료되었습니다. 환영합니다.", "알림", {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning",
          draggable: true,
        });
        this.$router.push({ name: "main" });
      } else {
        ElMessageBox.confirm(
          "로그인 정보가 잘못되었습니다. 다시 확인해주세요.",
          "알림",
          {
            confirmButtonText: "OK",
            cancelButtonText: "Cancel",
            type: "warning",
            draggable: true,
          }
        );
      }
    },
    async naverlogin() {
      // const client_id='hCg3zNttO1i50cUABPON'
      // const callbackUrl='http://localhost:3000/'
      // var url = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id='+client_id+'&redirect_uri='+callbackUrl+'&state=1234';
      var url="http://localhost:8080/api/oauth2/authorization/naver"
      window.location.href=url;
      
    },
    async kakaologin() {
      var url = "http://localhost:8080/api/oauth2/authorization/kakao";
      window.location.href = url;
      // await this.socailConfirm(url);
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

a {
  margin-right: 5%;
  margin: 0 auto;
  display: inline-flex;
}
.hr-sect {
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: rgba(0, 0, 0, 0.35);
  font-size: 12px;
  margin: 8px 0px;
}
.hr-sect::before,
.hr-sect::after {
  content: "";
  flex-grow: 1;
  background: rgba(0, 0, 0, 0.35);
  height: 1px;
  font-size: 0px;
  line-height: 0px;
  margin: 0px 16px;
}
</style>
