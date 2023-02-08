<template>
  <div class="Header">
    <router-link to="/">
      <img alt="logo" src="@/assets/image/logo.png" class="Header-logo" />
    </router-link>
    <nav>
      <div style="display: inline-flex">
        <a>
          <el-badge :value="12" class="item">
            <el-icon
              :size="33"
              @click="dialogVisible = true"
              style="cursor: pointer"
              ><BellFilled
            /></el-icon>
          </el-badge>
          <el-dialog
            class="el-dialog"
            v-model="dialogVisible"
            width="600px"
            style="border-radius: 5%"
          >
            <ul
              v-infinite-scroll="load"
              class="infinite-list"
              style="overflow: auto"
            >
              <li v-for="i in count" :key="i" class="infinite-list-item">
                {{ i }}
              </li>
            </ul>
          </el-dialog></a
        >

        <!-- 로그인 전 -->
        <!-- <a class="el-dropdown-link" style="display: inline-flex" v-if="data === null"> -->
        <a
          class="el-dropdown-link"
          style="display: inline-flex"
          v-if="!isLogin"
        >
          <el-dropdown>
            <el-icon :size="30"><UserFilled /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <!-- <router-link to="/login"> -->
                <el-dropdown-item @click="gologin"> 로그인 </el-dropdown-item>
                <!-- </router-link> -->
                <el-dropdown-item divided @click="open"
                  >마이페이지
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </a>

        <!-- 로그인 후 -->
        <a class="el-dropdown-link" style="display: inline-flex" v-else>
          <el-dropdown>
            <el-icon :size="30"><UserFilled /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click.prevent="onClickLogout"
                  >로그아웃</el-dropdown-item
                >

                <el-dropdown-item divided @click="gomypage"
                  >마이페이지</el-dropdown-item
                >
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </a>

        <a class="el-dropdown-link" style="display: inline-flex">
          <el-dropdown>
            <el-icon :size="30"><Menu /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goboard"
                  >스터디 게시판</el-dropdown-item
                >

                <el-dropdown-item divided>공지사항</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </a>
      </div>
    </nav>
  </div>
</template>

<script>
import { BellFilled, UserFilled, Menu } from "@element-plus/icons-vue";
import { defineComponent, ref } from "vue";
import { mapState, mapGetters, mapActions } from "vuex";
import { ElMessageBox } from "element-plus";

const memberStore = "memberStore";
export default defineComponent({
  name: "MyHeader",
  components: {
    BellFilled,
    UserFilled,
    Menu,
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
      BellFilled,
      UserFilled,
      Menu,
      dialogVisible,
      count,
      // start,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "data"]),
    ...mapGetters(["checkUserInfo","checkIsLogin"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      this.userLogout(sessionStorage.getItem("ACCESS"));
      // this.userLogout(this.data.email); // state에 저장된 유저 정보
      sessionStorage.removeItem("ACCESS"); //저장된 토큰 없애기
      sessionStorage.removeItem("REFRESH"); //저장된 토큰 없애기
      this.$router.go({ name: "main" });
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
  left: 0;
  top: 0;
  width: 100%;
  height: 100px;
  background-color: white;
  display: inline-block;
}
.HeaderMain {
  width: 100%;
  height: 100px;
  background-color: white;
}

.Header-logo {
  margin-top: 20px;
  margin-left: 200px;
  height: 80px;
}

nav {
  margin-top: 50px;
  margin-right: 300px;
  /* display: inline-flex; */
  float: right;
}

img {
  display: inline-block;
}

span {
  align-items: right;
}

a {
  align-items: right;
  margin-right: 2.8rem;
  color: rgb(49, 49, 49);
}
</style>
