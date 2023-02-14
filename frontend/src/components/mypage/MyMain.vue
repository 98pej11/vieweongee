<template>
  <div>
    <h3 class="text-h6 mb-3">참여예정 스터디 목록</h3>
    <div class="block text-center" style="margin-bottom: 10%">
      <el-carousel :interval="4000" type="card" height="250px">
        <el-carousel-item
          class="carousel-item"
          v-for="item in myStudyCards"
          :key="item"
          @click="itemClick(item.id)"
        >
          <!-- <img src="@/assets/image/interview2.png" /> -->
          <h2>{{ item.title }}</h2>
          <!-- <h4 text="2xl">{{ item.study_datetime }}</h4> -->
        </el-carousel-item>
      </el-carousel>
    </div>
    <h3 class="text-h6 mb-3">나의 스터디 전체 통계</h3>
    <el-row>
      <el-col :span="12">
        <div class="demo-progress" style="" v-if="turnScore != null">
          <h4>회차별 통계자료</h4>
          <el-progress
            v-if="this.turnScore[0] != null"
            :percentage="this.turnScore[0].total_average * 20"
            :format="format"
          ></el-progress>
          <el-progress
            v-if="this.turnScore[1] != null"
            :percentage="this.turnScore[1].total_average * 20"
            :format="format"
          ></el-progress>
          <el-progress
            v-if="this.turnScore[2] != null"
            :percentage="this.turnScore[2].total_average * 20"
            :format="format"
            status="success"
          ></el-progress>
          <el-progress
            v-if="this.turnScore[3] != null"
            :percentage="this.turnScore[3].total_average * 20"
            :format="format"
            status="warning"
          ></el-progress>
          <el-progress
            v-if="this.turnScore[4] != null"
            :percentage="this.turnScore[4].total_average * 20"
            :format="format"
            status="exception"
          ></el-progress>
        </div>

        <div class="demo-progress" style="" v-else>
          <h4>회차별 통계자료</h4>
          지난 스터디 기록이 없습니다.
        </div>
      </el-col>

      <el-col :span="12">
        <div class="demo-progress" v-if="abilScore != null">
          <h4>역량별 통계자료</h4>
          <el-progress
            :percentage="this.abilScore.attitude_average * 20"
          ></el-progress>
          <el-progress
            :percentage="this.abilScore.ability_average * 20"
            :format="format"
          />
          <el-progress
            :percentage="this.abilScore.teamwork_average * 20"
            status="success"
          />
          <el-progress
            :percentage="this.abilScore.solving_average * 20"
            status="warning"
          />
          <el-progress
            :percentage="this.abilScore.loyalty_average * 20"
            status="exception"
          />
        </div>

        <div class="demo-progress" style="" v-else>
          <h4>역량별 통계자료</h4>
          지난 스터디 기록이 없습니다.
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import { defineComponent } from "vue";
import http from "@/api/http";
const api = http;
const studyStore = "studyStore";

export default defineComponent({
  name: "MyMain",
  data() {
    return {
      format: (percentage) => (percentage === 100 ? "Full" : `${percentage}%`),
      // upcomings: null,
      upcomings: null,
      turnScore: null,
      abilScore: null,
      myStudyCards: [],
    };
  },
  created() {
    api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
    api.get(`/users/mystudy`).then(({ data }) => {
      if (data.message === "SUCCESS") {
        console.log("곧 참 스 ~");
        console.log(data.data);
        this.myStudyCards = [];
        this.myStudyCards = data.data;
      } else {
        console.log("아직 열린 스터디 없음");
        this.myStudyCards = [{ title: "시작된 스터디가 아직 없습니다" }];
      }
    });

    // this.getTurns();
    this.getAbil();
  },
  methods: {
    ...mapMutations(studyStore, ["SET_STUDY_ID"]),
    async getTurns() {
      api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");

      await api.get(`/users/turn`).then(({ data }) => {
        if (data.message === "SUCCESS") {
          this.turnScore = data.data;
          // console.log(this.turnScore[0].total_average);
          console.log(data.data);
          // percentage.value = this.turnScore[0].total_average *10;
        }
      });
    },
    async getAbil() {
      api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
      await api.get(`/users/graph`).then(({ data }) => {
        console.log("역량별>>");
        console.log(data);
        if (data.message === "SUCCESS") {
          console.log(data.data);
          this.abilScore = data.data;
        }
      });
    },
    itemClick(id) {
      console.log(id + " 눌렀떠");
      this.SET_STUDY_ID(id);
      this.$router.push({ name: "studyview" });
    },
  },
});
</script>

<style scoped>
h3 {
  text-align: center;
  margin-bottom: 6%;
}

.demo-progress {
  margin-top: 10%;
  margin-left: 10%;
  justify-content: center;
  align-content: center;
}
.demo-progress .el-progress--line {
  margin-bottom: 15px;
  width: 90%;
}
.carousel-item {
  background-image: url("https://newsimg.sedaily.com/2020/09/10/1Z7SVDHXBZ_1.jpg");
  background-size: cover;
  opacity: 0.7;
  /* width: 100%; */
  height: 100%; /* adjust to your preferred height */
  /* display: flex;
  align-items: center;
  justify-content: center; */
}
.el-carousel__item h2 {
  background-color: #cadffb;
  border-radius: 10%;
  color: black;
  opacity: 1;
  margin: 0 auto;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

/* .el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
} */
</style>
