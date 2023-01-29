<template>
  <div>
    <p>MyPage</p>
    <img :src="img" />
    <div>
      <el-button
        ><el-icon><Document /></el-icon>Main</el-button
      >
      <!-- <el-button :icon="Document">Main</el-button>
        <el-button :icon="Document">Data</el-button>
        <el-button :icon="Document">Modify Info</el-button>
        <el-button :icon="Document">Delete User</el-button> -->
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Document } from "@element-plus/icons-vue";

export default defineComponent({
  name: "MySidebar",
  data: () => ({
    img: require("@/assets/image/profile_img.png"),
  }),
  components: { Document },
});
</script>
<style scoped></style>
