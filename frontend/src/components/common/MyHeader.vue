<template>
  <div class="Header">
    <router-link to="/">
      <div @click="gomain">
        <img alt="logo" src="@/assets/image/logo.png" class="Header-logo" />
      </div>
    </router-link>
    <nav v-if="!isLogin">
      <ul>
        <li @click="gologin">
          <el-icon color="#5f5a83" :size="25"><Avatar /></el-icon>
          <b>로그인</b>
        </li>
        <li @click="open">
          <el-icon color="#5f5a83" :size="25"><Histogram /></el-icon
          ><b>마이페이지</b>
        </li>
        <li @click="goboard">
          <el-icon color="#5f5a83" :size="25"><List /></el-icon
          ><b>모집게시판</b>
        </li>
      </ul>
    </nav>

    <nav v-else>
      <ul>
        <li @click="onClickLogout">
          <el-icon color="#5f5a83" :size="25"><Avatar /></el-icon
          ><b>로그아웃</b>
        </li>
        <li @click="gomypage">
          <el-icon color="#5f5a83" :size="25"><Histogram /></el-icon
          ><b>마이페이지</b>
        </li>
        <li @click="goboard">
          <el-icon color="#5f5a83" :size="25"><List /></el-icon
          ><b>스터디게시판</b>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import { Avatar, Histogram, List } from "@element-plus/icons-vue";
import { defineComponent, ref } from "vue";
import { mapState, mapGetters, mapActions } from "vuex";
import { ElMessageBox } from "element-plus";

const memberStore = "memberStore";
export default defineComponent({
  name: "MyHeader",
  components: {
    Histogram,
    Avatar,
    List,
  },
  // mounted(){
  //   this.getIs();
  // },
  setup() {
    const dialogVisible = ref(false);
    const count = 5;
    // const start = true;

    function load() {
      this.count += 1;
      // console.log(this.count);
    }
    return {
      load,
      Histogram,
      Avatar,
      List,
      dialogVisible,
      count,
      // start,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "data"]),
    ...mapGetters(["checkUserInfo", "checkIsLogin"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      this.userLogout(sessionStorage.getItem("ACCESS"));
      // this.userLogout(this.data.email); // state에 저장된 유저 정보
      sessionStorage.removeItem("ACCESS"); //저장된 토큰 없애기
      sessionStorage.removeItem("REFRESH"); //저장된 토큰 없애기

      ElMessageBox.alert("로그아웃이 완료되었습니다. 감사합니다.", "알림", {
        confirmButtonText: "확인",
      });

      this.$router.go({ name: "main" });
    },
    gomain() {
      this.$router.push({ name: "main" });
    },

    gologin() {
      this.$router.push({ name: "login" });
    },

    gomypage() {
      this.$router.push({ name: "mypage" });
    },

    goboard() {
      this.$router.push({ name: "studylist" });
    },

    open() {
      ElMessageBox.alert("로그인 후 이용 부탁드립니다.", "알림", {
        confirmButtonText: "확인",
      });
      this.gologin();
    },
  },
});
</script>

<style scoped>
.infinite-list {
  height: 400px;
  padding: 0;
  margin: 0;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  background: var(--el-color-primary-light-9);
  margin: 10px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
.el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}

.Header {
  /* position: fixed; */
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
}
.Header-logo {
  flex: 1;
  width: 80%;
  margin-left: 50%;
}
nav {
  flex: 1;
  align-items: right;
  margin-right: 10%;
}

nav ul {
  float: right;
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
}

nav li {
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: 30px;
  font-size: large;
  /* color: #a59ed1; */
  color: #5f5a83;
}
nav li :hover {
  color: #b3c4f7;
}
b {
  margin-left: 7px;
}
</style>
