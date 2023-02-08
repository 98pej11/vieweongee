<template>
  <div class="card-list" v-if="this.studyList.length !== 0">
    <el-row justify="space-evenly">
      <el-col
        :xs="24"
        :md="6"
        :lg="6"
        v-for="(data, index) in studyList"
        :key="index"
        class="card-div"
      >
        <div @click="move(data.id)">
          <div class="person-div">
            <el-icon :size="17"><User /></el-icon>&nbsp;{{ data.personnel }} / 6
          </div>
          <div class="title">{{ data.title }}</div>
          <div class="card-contents">
            <div>기업</div>
            <div>{{ data.company }}</div>
          </div>
          <div class="card-contents">
            <div>직무</div>
            <div>{{ data.job }}</div>
          </div>
          <div class="card-contents">
            <div>날짜</div>
            <div>{{ data.study_datetime }}</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import http from "@/api/http";
import { mapState, mapActions, mapMutations } from "vuex";
import { User } from "@element-plus/icons-vue";
const studyStore = "studyStore";

export default {
  name: "MainCardList",
  components: {
    User,
  },
  props: {
    compType: String,
  },
  computed: {
    ...mapState(studyStore, ["studyList", "studyID"]),
  },
  created() {
    console.log("타입", this.compType);
    if (this.$route.params.type !== "search") {
      if (this.compType == "main") {
        this.CLEAR_LIST();
        this.maininit();
      } else if (this.compType == "study") {
        this.CLEAR_LIST();
        this.studyinit();
      }
    }
  },
  methods: {
    ...mapActions(studyStore, ["getTopList", "getAllList"]),
    ...mapMutations(studyStore, ["CLEAR_LIST", "SET_STUDY_ID"]),

    async maininit() {
      await this.getTopList();
    },
    async studyinit() {
      await this.getAllList();
    },
    move(id) {
      // 스테이트 변경
      this.SET_STUDY_ID(id);
      console.log("스테이트 변경 완료" + this.studyID);
      this.$router.push({ name: "studyview" });
    },
  },
};
</script>
<style scoped>
.card-list {
  max-width: 70%;
  margin: 0 auto;
  padding: 20px;
}
.card-contents {
  display: flex;
  justify-content: space-between;
  padding: 0 30px 10px 30px;
}
.card-div {
  font-family: "nexonlv1";
  /* min-width: 280px; */
  padding: 20px;
  margin: 10px 10px 50px 10px;
  border: 4px solid rgba(227, 232, 252, 0.5);
  border-radius: 10px;
  box-shadow: 1px 10px 15px 1px rgb(221, 221, 221);
}

.card-div:hover {
  background: rgba(210, 218, 255, 0.6);
  transition: all 200ms 0s ease-in;
  transform: scale(1.04);
  border: 4px solid rgba(227, 232, 252, 0.2);
}
.card-contents :nth-child(1) {
  color: #555454;
}

.title {
  font-weight: bold;
  font-size: large;
  text-align: center;
  margin: 3% 0 10% 0;
}
.person-div {
  padding: 5px;
  width: 75px;
  display: flex;
  margin: 0 auto;
  border-radius: 30px;
  background-color: #d3daff;
}
</style>
