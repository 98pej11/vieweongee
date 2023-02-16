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
            <el-input
              placeholder="이메일 규칙"
              id="email"
              v-model="user.email"
            />
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
            <el-input
              placeholder="이메일 인증번호"
              id="emailCheck"
              v-model="emailCheck"
            />
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
            <el-input
              placeholder="비밀번호 규칙"
              id="password"
              v-model="user.password"
              type="password"
            />
          </el-col>
          <el-col style="margin-top: 3%">
            <el-input
              type="password"
              id="passwordCheck"
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
            ><el-input placeholder="닉네임 규칙" id="name" v-model="user.name"
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
import { mapActions, mapState, mapMutations } from "vuex";
// import { application } from "express";
// import http from "@/api/http";

// const api = http;
const memberStore = "memberStore";
var pattern1 = /[0-9]/;
var pattern2 = /[a-z]/;
var pattern3 = /[A-Z]/;

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
    ...mapState(memberStore, [
      "isLogin",
      "isLoginError",
      "code",
      "isValidEmail",
      "isValidName",
    ]),
  },

  methods: {
    ...mapActions(memberStore, ["userJoin", "checkEmail"]),
    ...mapMutations(memberStore, ["SET_IS_VALID_EMAIL", "SET_IS_VALID_NAME"]),

    async join() {
      // console.log("vuecomponent : " + JSON.stringify(this.user));
      // console.log("가입버튼 눌렀는데 이메일 인증 확인 >> " + this.isValidEmail);
      let email = document.getElementById("email");
      let emailCheck = document.getElementById("emailCheck");
      let password = document.getElementById("password");
      let passwordCheck = document.getElementById("passwordCheck");
      let name = document.getElementById("name");
      if (email.value.length == 0) {
        alert("이메일을 입력하세요.");
      } else if (emailCheck.value.length == 0) {
        alert("이메일을 인증하세요.");
      } else if (
        !pattern1.test(password.value) ||
        !pattern2.test(password.value) ||
        !pattern3.test(password.value) ||
        password.value.length < 8 ||
        password.value.length > 16
      ) {
        alert(
          "비밀번호는 8자 이상 16자 이하 문자+영대문자+영소문자로 구성하여야 합니다."
        );
      } else if (name.value.length < 2 || name.value.length > 10) {
        alert("닉네임은 2자 이상 10자 이하 구성하여야 합니다.");
      } else if (passwordCheck.value.length == 0) {
        alert("비밀번호 재확인을 입력하세요.");
      } else if (name.value.length == 0) {
        alert("닉네임을 입력하세요.");
      } else {
        if (!this.isValidEmail) {
          alert("이메일 중복 확인을 해주세요.");
          // this.$router.go(0);
        } else if (this.user.password != this.user.passwordCheck) {
          alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        } else {
          await this.userJoin(this.user);
          ElMessageBox.alert("회원가입이 완료되었습니다. 환영합니다.", "알림", {
            confirmButtonText: "확인",
          });
          this.$router.push({ name: "login" });
        }
      }
    },

    async emailcheck() {
      let email = document.getElementById("email");
      if (!email.value.includes("@")) {
        alert("이메일 형식을 다시 확인해주세요.");
      } else {
        await this.checkEmail(this.user);
      }
    },

    codeCheck() {
      if (this.code === this.emailCheck) {
        console.log("인증코드 확인");
        alert("확인되었습니다.");
        //이메일 확인 완료 처리
        this.SET_IS_VALID_EMAIL(true);

        // console.log("이메일 인증 확인 >> " + this.isValidEmail);
      } else {
        console.log("인증 재확인 요망");
        alert("인증번호가 일치하지 않습니다.");
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
