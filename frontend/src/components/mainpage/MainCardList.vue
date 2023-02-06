<template>
  <div class="card-list">
    <el-row justify="space-evenly">
      <el-col
        :xs="24"
        :md="6"
        :lg="6"
        v-for="(data, index) in cardData"
        :key="index"
        class="card-div"
      >
        <div @click="move(data.id)">
          <div class="person-div">
            <el-icon :size="17"><User /></el-icon>&nbsp;{{ data.capacity }} / 6
          </div>
          <div class="title">{{ data.title }}</div>
          <div class="card-contents">
            <div>기업</div>
            <div>{{ data.ent }}</div>
          </div>
          <div class="card-contents">
            <div>직무</div>
            <div>{{ data.dept }}</div>
          </div>
          <div class="card-contents">
            <div>기업</div>
            <div>{{ data.date }}</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
  <div class="card-list">
    <div v-for="(data, index) in studytops" :key="index" class="card-div">
      <div>{{ data.total_number_of_people }}</div>
      <div class="title">{{ data.study_title }}</div>
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
  </div>
</template>
<script>
import { User } from "@element-plus/icons-vue";
import { mapState, mapActions } from "vuex";
import http from "@/api/http.js";

const studyStore = "studyStore";

export default {
  name: "MainCardList",
  components: {
    User,
    // MainCardItem,
  },

  data() {
    return {
      type: "detail",
      allStudy: [{}],
      studytops: [],

      cardData: [
        {
          id: 1,
          title: "싸피 비전공자 면접스터디 구해요",
          ent: "SSAFY",
          dept: "프론트엔드",
          capacity: 1,
          date: "2023.01.12 10:00",
        },
        {
          id: 2,
          title: "신한은행 면접스터디",
          ent: "신한은행",
          dept: "백엔드",
          capacity: 3,
          date: "2023.01.11 19:00",
        },
        {
          id: 3,
          title: "유플러스 백엔드 면접",
          ent: "유플러스",
          dept: "백엔드",
          capacity: 5,
          date: "2023.01.10 11:00",
        },
      ],
    };
  },
  mounted() {
    // axios 통신
    this.setAllStudys();
    // json parser 테스트
    // this.setdata();
  },
  created() {
    http.get(`/study/top3`).then(({ data }) => {
      this.studytops = data;
    });
    if (this.global_isShow) {
      this.CLEAN_GLOBAL_ISSHOW();
      this.viewArticle(this.global_article);
    }
  },
  computed: {
    ...mapState(studyStore, [
      "isError",
      "studyID",
      "global_article",
      "global_isShow",
    ]),
  },
  methods: {
    ...mapActions(studyStore, ["getList"]),
    // setdata() {
    //   http.get(`/posts/1/comments`).then((data) => {
    //     console.log(data);
    //     this.allStudy = data.data;
    //     console.log(this.allStudy);
    //   });
    // },

    async setAllStudys() {
      await this.getList();
      this.allStudy = this.studyList;
    },
    move(id) {
      console.log("클릭 : " + this.studyID);
      this.$router.push({
        name: "studyview",
        params: { studyid: id },
      });
    },
    // move(id) {
    // this.$emit("move", id);
    // },
  },
};
</script>
<style scoped>
.person-div {
  padding: 5px;
  width: 65px;
  display: flex;
  justify-content: flex-start;
  border-radius: 30px;
  background-color: #d3daff;
}
.card-list {
  margin-top: 30px;
  padding: 20px;
}
.card-contents {
  display: flex;
  justify-content: space-between;
  padding: 0 30px 10px 30px;
}
.card-div {
  font-family: "nexonlv1";
  min-width: 280px;
  padding: 20px;
  margin-bottom: 20px;
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
</style>
