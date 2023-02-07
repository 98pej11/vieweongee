<template>
  <div class="studylist">
    <h2>스터디 목록</h2>
    <el-row class="board-nav">
      <el-col :span="6">
        <el-button-group class="ml-4 type-btn" size="large">
          <el-button>
            <el-icon><Calendar /></el-icon>
          </el-button>
          <el-button>
            <el-icon><Picture /></el-icon>
          </el-button>
        </el-button-group>
      </el-col>
      <el-col :span="12">
        <SearchBar />
      </el-col>
      <el-col :span="6">
        <router-link to="/studycreate">
          <el-button size="large" class="plus-btn">
            <el-icon><CirclePlus /></el-icon>
          </el-button>
        </router-link>
      </el-col>
    </el-row>
    <div>
      <MainCardList></MainCardList>
    </div>
  </div>
</template>
<script>
import SearchBar from "@/components/common/SearchBar.vue";
import { CirclePlus } from "@element-plus/icons-vue";
import { Calendar } from "@element-plus/icons-vue";
import { Picture } from "@element-plus/icons-vue";
import MainCardList from "@/components/mainpage/MainCardList.vue";

export default {
  components: { SearchBar, CirclePlus, MainCardList, Calendar, Picture },
};
</script>
<style scoped>
.studylist {
  text-align: center;
}
.board-nav {
  display: flex;
  justify-content: space-around;
  margin: 50px 200px 50px 200px;
}

.el-button {
  background-color: #e1e6ff;
  height: 50px;
}
</style>
