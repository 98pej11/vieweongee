<template>
  <div>
    <h2>스터디 목록</h2>
    <span>
      <SearchBar />
      <router-link to="/studycreate">
        <el-button>
          <el-icon><CirclePlus /></el-icon>
        </el-button>
      </router-link>
    </span>
    <MainCardList></MainCardList>
  </div>
</template>
<script>
import SearchBar from "@/components/common/SearchBar.vue";
import { CirclePlus } from "@element-plus/icons-vue";
import MainCardList from "@/components/mainpage/MainCardList.vue";

export default {
  components: { SearchBar, CirclePlus, MainCardList },
};
</script>
<style scoped>
/* span {
  display: inline-block;
}

span el-button {
  float: right;
} */
</style>
