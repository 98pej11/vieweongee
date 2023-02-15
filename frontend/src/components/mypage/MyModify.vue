<template>
  <el-container>
    <el-main v-if="myShow">
      <el-form style="font-size: large">
        <el-row class="text"
          ><el-icon :size="25"><Lock /></el-icon>소셜 로그인 회원은 회원 수정이
          불가능합니다.</el-row
        >
      </el-form>
    </el-main>
    <el-main v-else>
      <el-form style="font-size: large">
        <el-row class="text"
          ><el-icon :size="25"><Lock /></el-icon>비밀번호 변경</el-row
        >
        <el-row :gutter="20">
          <el-col>
            <el-input
              type="password"
              placeholder="변경할 비밀번호 입력"
              id="password"
              v-model="user.password"
            />
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col>
            <el-input
              type="password"
              placeholder="비밀번호 재확인"
              id="passwordCheck"
              v-model="user.passwordCheck"
            />
          </el-col>
        </el-row>
        <el-row class="text"
          ><el-icon :size="25"><User /></el-icon>닉네임 변경</el-row
        >
        <el-row :gutter="20">
          <el-col>
            <el-input
              placeholder="변경할 닉네임 입력"
              v-model="user.name"
              id="name"
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
              @click="confirm"
            >
              수정 완료
            </el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { defineComponent } from "vue";
import { Lock } from "@element-plus/icons-vue";
import { User } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { mapState, mapActions } from "vuex";
import http from "@/api/http";
const memberStore = "memberStore";
var pattern1 = /[0-9]/;
var pattern2 = /[a-z]/;
var pattern3 = /[A-Z]/;
const config = {
  headers: {
    ACCESS: sessionStorage.getItem("ACCESS"),
  },
};
export default defineComponent({
  name: "MyModify",
  components: {
    Lock,
    User,
  },
  data() {
    return {
      user: {
        password: "",
        passwordCheck: "",
        name: "",
        provider: "",
      },
      myType: null,
      myShow: null,
    };
  },
  created() {
    http.get(`/users/type`, config).then(({ data }) => {
      this.myType = data.data;

      if (this.myType === "global") this.myShow = false;
      else this.myShow = true;
    });
  },
  computed: {
    ...mapState(memberStore, ["data", "isLogin"]),
  },
  methods: {
    ...mapActions(memberStore, ["userUpdate", "userDelete"]),

    async confirm() {
      // alert("confirm");

      let password = document.getElementById("password");
      let passwordCheck = document.getElementById("passwordCheck");
      let name = document.getElementById("name");
      if (
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
        if (this.user.password != this.user.passwordCheck) {
          alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        } else {
          await this.userUpdate(this.user);
          // let token = sessionStorage.getItem("ACCESS");
          if (this.isLogin) {
            // await this.getUserInfo(token);
            ElMessage({
              type: "success",
              message: "회원 정보 수정완료",
            });
            this.$router.push({ name: "main" });
          }
        }
      }
    },
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
