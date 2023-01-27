<template>
  <div class="Header">
    <router-link to="/">
      <img alt="logo" src="@/assets/image/logo.png" class="Header-logo" />
    </router-link>
    <nav>
      <div>
      <a
        ><router-link to="/study"
          ><el-icon :size="30"><BellFilled /></el-icon></router-link
      ></a>
      <a
        ><router-link to="/login"
          ><el-icon :size="30"><UserFilled /></el-icon></router-link
      ></a>
      <a class="el-dropdown-link" style="display:inline-flex">
        <el-dropdown>
          <el-icon :size="30"><Menu /></el-icon>
          <template #dropdown>
            <el-dropdown-menu>
              
              <el-dropdown-item><router-link to="/study">스터디 게시판</router-link></el-dropdown-item>

              <el-dropdown-item divided>공지사항</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </a>
    </div>
    </nav>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

import { BellFilled, UserFilled, Menu } from "@element-plus/icons-vue";
export default defineComponent({
  name: "MyHeader",
  components: {
    BellFilled,
    UserFilled,
    Menu,
  },
});
</script>

<style scoped>
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
  margin-right: 200px;
  display: inline-block;
  float: right;
}

.logo-mascot {
  width: 8%;
}

.logo-text {
  width: 18%;
}

img {
  display: inline-block;
}

span {
  align-items: right;
}

a {
  align-items: right;
  margin-right: 2rem;
  color: rgb(49, 49, 49);
}

.search-bar {
  display: flex;
  justify-content: center;
  align-items: center;
}
.search-field {
  text-align: center;
  width: 50%;
}
</style>
