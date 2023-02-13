<template>
  <div class="card-list">
    <el-row justify="space-evenly">
      <el-col
        :xs="24"
        :md="6"
        :lg="6"
        v-for="(data, index) in studyList"
        :key="index"
        class="card-div"
      >
        <transition name="moveInUp">
          <div @click="move(data.id)">
            <div class="person-div">
              &nbsp; <el-icon :size="17"><User /></el-icon>&nbsp;{{
                this.currentList[index]
              }}
              /
              {{ data.personnel }}&nbsp;
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
        </transition>
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
    ...mapState(studyStore, [
      "studyList",
      "studyID",
      "currentList",
      "getCurrentPerson",
    ]),
  },
  watch: {
    currentList() {
      console.log(this.studyList);
      // this.getSearchList();
    },
  },
  created() {
    if (this.$route.params.type !== "result") {
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
    ...mapActions(studyStore, ["getTopList", "getAllList", "getPersonnel"]),
    ...mapMutations(studyStore, ["CLEAR_LIST", "SET_STUDY_ID"]),

    async maininit() {
      await this.getTopList();
      // 현재 참가자 수 받아오기
      for (let idx = 0; idx < this.studyList.length; idx++) {
        await this.getPersonnel(this.studyList[idx].id);
      }
      console.log(this.currentList);
    },
    async studyinit() {
      await this.getAllList();

      // 현재 참가자 수 받아오기
      for (let idx = 0; idx < this.studyList.length; idx++) {
        await this.getPersonnel(this.studyList[idx].id);
      }
      console.log(this.currentList);
    },
    async getSearchList() {
      // 현재 참가자 수 받아오기
      for (let idx = 0; idx < this.studyList.length; idx++) {
        await this.getPersonnel(this.studyList[idx].id);
      }
      console.log(this.currentList);
    },

    // 세부 페이지로 이동
    move(id) {
      this.SET_STUDY_ID(id);
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
  background-color: white;
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
  cursor: pointer;
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
  /* padding: 5px; */
  margin-right: auto;
  width: 80px;
  display: flex;
  padding: 5px 0 5px 5px;
  border-radius: 30px;
  background-color: #d3daff;
}
</style>
