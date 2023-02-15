<template>
  <div class="studylist">
    <h2>스터디 목록</h2>
    <el-row class="board-nav">
      <el-col :span="6">
        <el-button-group class="ml-4 type-btn" size="large">
          <el-button>
            <el-icon :size="25"><Calendar /></el-icon>
          </el-button>
          <el-button>
            <el-icon :size="25"><Picture /></el-icon>
          </el-button>
        </el-button-group>
      </el-col>
      <el-col :span="12">
        <SearchBar />
      </el-col>
      <el-col :span="6">
        <el-button @click="onClick" size="large" class="plus-btn">
          <el-icon :size="25"><CirclePlus /></el-icon>
        </el-button>
      </el-col>
    </el-row>
    <div>
      <MainCardList compType="study"></MainCardList>
    </div>
  </div>
</template>
<script>
import SearchBar from "@/components/common/SearchBar.vue";
import MainCardList from "@/components/mainpage/MainCardList.vue";
import { CirclePlus, Calendar, Picture } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";

export default {
  name: "StudyListCard",
  components: {
    SearchBar,
    MainCardList,
    CirclePlus,
    Calendar,
    Picture,
  },

  methods: {
    onClick() {
      if (sessionStorage.getItem("ACCESS") == null) {
        ElMessage({
          type: "error",
          message: "로그인 후 이용해주세요",
        });
        this.$router.push({ name: "login" });
      } else {
        this.$router.push({ name: "studycreate" });
      }
    },
  },
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
