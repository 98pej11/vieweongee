<template>
  <div class="mypage">
    <el-container>
      <el-aside width="300px" class="side-bar content-center">
        <p id="title">MyPage</p>
        <img :src="img" />
        <p>뷰엉님, 반갑습니다</p>
        <div class="buttons">
          <!-- <el-button       ><el-icon><Document /></el-icon>Main</el-button > -->
          <!-- <el-button>Main <Document style="width: 1em; height: 1em; margin-right: 5px" /></el-button> -->
          <el-button size="large" @click="changeComp('MyMain')">Main</el-button>
          <el-button size="large" @click="changeComp('MyData')">Data</el-button>
          <el-button size="large" @click="changeComp('MyModify')"
            >Modify Info</el-button
          >
          <el-button size="large" @click="changeComp('MyDelete')"
            >Delete User</el-button
          >
        </div>
      </el-aside>
      <!-- <el-main><MyMain></MyMain></el-main> -->
      <el-main class="main"
        ><component v-bind:is="currentTab"></component
      ></el-main>
    </el-container>
  </div>
</template>

<script>
import { defineComponent } from "vue";
import MyMain from "@/components/mypage/MyMain.vue";
import MyData from "@/components/mypage/MyData.vue";
import MyModify from "@/components/mypage/MyModify.vue";
import MyDelete from "@/components/mypage/MyDelete.vue";

export default defineComponent({
  components: {
    MyMain,
    MyData,
    MyModify,
    MyDelete,
  },
  data: () => ({
    img: require("@/assets/image/profile_img.png"),
    currentTab: "MyMain",
  }),
  methods: {
    changeComp: function (compName) {
      this.currentTab = compName;
      console.log(this.currentTab);
    },
  },
});
</script>

<style scoped>
.el-container {
  margin-top: 50px;
}
#title {
  font-family: "tenada";
  font-size: xx-large;
  color: #4b587a;
}
.side-bar {
  min-width: 250px;
  min-height: 70%;
  margin-left: 200px;
}
.side-bar > * {
  text-align: center;
}
.main {
  margin-left: 100px;
  margin-right: 200px;
}
/* .icon {
  width: 15%;
  margin-right: 10px;
} */
.buttons {
  display: flex;

  /*위에서 아래로 수직 배치*/
  flex-direction: column;

  /*중앙정렬*/
  justify-content: center;
  align-items: center;
}

.el-button {
  /*  display: table-cell;
  vertical-align: middle; */
  width: 70%;
  margin: 15px;
}
</style>
