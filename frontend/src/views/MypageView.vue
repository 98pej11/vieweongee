<template>
  <div class="mypage">
    <el-container>
      <el-aside width="300px" class="side-bar content-center">
        <p id="title">MyPage</p>
        <img :src="img" />
        <p>{{ this.myName }}님, 반갑습니다</p>
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
      <!-- 마이페이지 SideBar 메뉴 선택에 따른 컴포넌트 교체 -->
      <el-main class="main"
        ><component v-bind:is="currentTab"></component
      ></el-main>
    </el-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import jwtDecode from "jwt-decode";
import http from "@/api/http";
import MyMain from "@/components/mypage/MyMain.vue";
import MyData from "@/components/mypage/MyData.vue";
import MyModify from "@/components/mypage/MyModify.vue";
import MyDelete from "@/components/mypage/MyDelete.vue";

const studyStore = "studyStore";

const config = {
  headers: {
    ACCESS: sessionStorage.getItem("ACCESS"),
  },
};
export default {
  components: {
    MyMain,
    MyData,
    MyModify,
    MyDelete,
  },
  data: () => ({
    img: require("@/assets/image/profile_img.png"),
    currentTab: "MyMain",
    myName: "",
    myType: null,
  }),
  created() {
    this.getMyId();
    http.get(`/users/type`, config).then(({ data }) => {
      this.myType = data.data;

      this.mystudys.forEach((el) => {
        el.study_datetime = el.study_datetime.substr(0, 16).replace("T", " ");
      });
    });

    this.getMyDataConfrim();

    // // 마이페이지 데이터 탭 axios
    // http.get(`/users/mystudy`, config).then(({ data }) => {
    //   console.log("전체 글 받아옵니다");
    //   console.log(data.data);
    //   this.mystudys = data.data;

    //   this.mystudys.forEach((el) => {
    //     el.study_datetime = el.study_datetime.substr(0, 16).replace("T", " ");
    //   });
    // });
  },
  methods: {
    ...mapActions(studyStore, ["getMyDataConfrim"]),
    changeComp: function (compName) {
      this.currentTab = compName;
      console.log(this.currentTab);
    },
    getMyId() {
      if (sessionStorage.getItem("ACCESS") != null)
        this.myName = jwtDecode(sessionStorage.getItem("ACCESS")).Name;
    },
  },
};
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
