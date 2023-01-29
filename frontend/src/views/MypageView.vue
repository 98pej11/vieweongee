<template>
  <div class="mypage">
    <el-container>
      <el-aside width="300px"><MySidebar></MySidebar></el-aside>
      <el-main><MyMain></MyMain></el-main>
    </el-container>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import MySidebar from "@/components/mypage/MySidebar.vue";
import MyMain from "@/components/mypage/MyMain.vue";

export default defineComponent({
  components: {
    MySidebar,
    MyMain,
  },
});
</script>
<style scoped>
.SideBar {
  padding-top: 5rem;
  min-width: 250px;
  min-height: 70vh;
}
.SideBar > * {
  padding: 10px 30px 30px 0;
  text-align: center;
}
.type {
  display: flex;
  justify-content: center;
  align-items: center;
}
.icon {
  width: 15%;
  margin-right: 10px;
}
</style>
