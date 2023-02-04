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
        <a class="el-dropdown-link" style="display: inline-flex" v-if="!data">
          <el-dropdown>
            <el-icon :size="30"><UserFilled /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item
                  ><router-link to="/login"
                    >로그인</router-link
                  ></el-dropdown-item
                >

                <el-dropdown-item divided
                  ><router-link to="/mypage"
                    >마이페이지</router-link
                  ></el-dropdown-item
                >
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </a>

        <!-- 로그인 후 -->
        <a class="el-dropdown-link" style="display: inline-flex" v-if="data">
          <el-dropdown>
            <el-icon :size="30"><UserFilled /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click.prevent="onClickLogout"
                  >로그아웃</el-dropdown-item
                >

                <el-dropdown-item divided
                  ><router-link to="/mypage"
                    >마이페이지</router-link
                  ></el-dropdown-item
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
                <el-dropdown-item
                  ><router-link to="/studylist"
                    >스터디 게시판</router-link
                  ></el-dropdown-item
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

const memberStore = "memberStore";
export default defineComponent({
  name: "MyHeader",
  components: {
    BellFilled,
    UserFilled,
    Menu,
  },
  setup() {
    const dialogVisible = ref(false);
    const count = 5;
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
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "data"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.data.email);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.data.email);
      sessionStorage.removeItem("accessToken"); //저장된 토큰 없애기
      sessionStorage.removeItem("refreshToken"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "main" });
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
