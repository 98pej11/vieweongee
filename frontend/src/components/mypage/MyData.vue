<template>
  <div>
    <el-container class="outcontainer">
      <el-main>
        <h2 class="text-h6 mb-3">스터디 내역 조회</h2>
        <el-table
          class="el-table"
          :data="mystudys"
          style="width: 100%; font-size: medium"
          :class="tableRowClasscompany"
        >
          <el-table-column prop="study_datetime" label="날짜" />
          <el-table-column prop="company" label="기업명" />
          <el-table-column prop="title" label="제목" />
          <el-table-column label="버튼">
            <template v-slot="scope">
              <el-button
                block
                color="#9DADD8"
                size="larger"
                style="margin: 2%; width: 70%"
                @click="viewStudy(scope.row)"
              >
                상세보기
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog
          class="el-dialog"
          v-model="dialogVisible"
          :data="mystudy"
          width="900px"
          style="border-radius: 5%; background-color: #eeecf8"
        >
          <h2 class="text-h6 mb-3" style="margin-bottom: 0%">
            {{ mystudy.title }}
          </h2>
          <h2
            class="text-h6 mb-3"
            style="margin-bottom: 2%; text-align: right; margin-right: 3%"
          >
            {{ mystudy.study_datetime }}
          </h2>
          <el-tabs type="border-card" class="demo-tabs">
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <span>채점표</span>
                </span>
              </template>
              <el-table
                border
                class="el-table"
                :span-method="objectSpanMethod"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                :class="tableRowClasscompany"
                :data="scoreData"
              >
                <el-table-column
                  border
                  prop="type"
                  label="대분류"
                  width="150%"
                />
                <el-table-column
                  border
                  prop="question"
                  label="항목"
                  width="400%"
                />

                <!-- 여기를 어떻게 해야할까 -->
                <!-- <el-table-column border prop="score" v-model=score label="점수" width="300%">
                  <el-rate
                    v-model = score
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value} points"
                  />
                </el-table-column> -->
                <el-table-column
                  border
                  prop="score"
                  label="점수"
                  width="300%"
                ></el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <span>피드백</span>
                </span>
              </template>
              <ul style="font-size: large">
                {{
                  this.scorecard.feedback
                }}
              </ul>
            </el-tab-pane>
          </el-tabs>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";

const config = {
  headers: {
    ACCESS: sessionStorage.getItem("ACCESS"),
  },
};

export default {
  name: "MyData",
  data() {
    return {
      start: 0,
      scorecard: null,
      mystudys: [],
      mystudy: null,

      dialogVisible: false,
      tableData: [
        {
          study_datetime: "2016-05-03",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-02",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-04",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-01",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-04",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-01",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-04",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-01",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-04",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
        {
          study_datetime: "2016-05-01",
          company: "Tom",
          title: "No. 189, Grove St, Los Angeles",
        },
      ],
      scoreData: [
        {
          type: "태도",
          question: "자신감 있는 표정과 목소리인가?",
          score: "",
          index: 0,
        },
        {
          type: "태도",
          question: "기본 준비 자세가 올바른가?",
          score: "",
          index: 1,
        },
        {
          type: "직무역량",
          question: "지원한 직무에 대한 구체적인 이해도를 가졌는가?",
          score: "",
          index: 2,
        },
        {
          type: "직무역량",
          question: "직무 수행에 필요한 역량을 갖췄는가?",
          score: "",
          index: 3,
        },
        {
          type: "팀워크",
          question: "다양한 의견을 수렴한 경험이 있는가?",
          score: "",
          index: 4,
        },
        {
          type: "팀워크",
          question: "적극적으로 참여하는 자세를 가졌는가?",
          score: "",
          index: 5,
        },
        {
          type: "문제해결",
          question: "문제를 해결하고자 하는 열정, 끈기, 의지를 가졌는가?",
          score: "",
          index: 6,
        },
        {
          type: "문제해결",
          question: "해결에 도움이 되는 방향성을 제시하였는가?",
          score: "",
          index: 7,
        },
        {
          type: "기업이해도",
          question: "기업에 대한 이해도가 높은가?",
          score: "",
          index: 8,
        },
        {
          type: "기업이해도",
          question: "퇴사 가능성이 있는가?",
          score: "",
          index: 9,
        },
      ],
    };
  },
  // 마이페이지 전체 글 받아오기
  created() {
    http.get(`/users/mystudy`, config).then(({ data }) => {
      console.log("전체 글 받아옵니다");
      console.log(data.data);
      this.mystudys = data.data;

      this.mystudys.forEach((el) => {
        el.study_datetime = el.study_datetime.substr(0, 16).replace("T", " ");
      });
    });
    if (this.global_isShow) {
      this.CLEAN_GLOBAL_ISSHOW();
      this.viewArticle(this.global_article);
    }
  },
  computed: {
    ...mapState(["global_article", "global_isShow"]),
  },
  methods: {
    // 나의 스터디 1개 조회
    viewStudy(row) {
      console.log("검색하려는 스터디 데이터 >> ");

      console.log(row);
      http.get(`/users/mystudy/${row.id}`, config).then(({ data }) => {
        // if(data.message==="SUCCESS")
        // console.log("글 1개 조회 성공" + this.dialogVisible);
        console.log(data.data);
        this.scorecard = data.data;
        console.log(this.scorecard.attitude_average);
        this.scoreData[0].score = this.scorecard.attitude_average;
        this.scoreData[1].score = this.scorecard.attitude_average;
        this.scoreData[2].score = this.scorecard.ability_average;
        this.scoreData[3].score = this.scorecard.ability_average;
        this.scoreData[4].score = this.scorecard.teamwork_average;
        this.scoreData[5].score = this.scorecard.teamwork_average;
        this.scoreData[6].score = this.scorecard.solving_average;
        this.scoreData[7].score = this.scorecard.solving_average;
        this.scoreData[8].score = this.scorecard.loyalty_average;
        this.scoreData[9].score = this.scorecard.loyalty_average;
        this.mystudy = row;
        this.dialogVisible = true;
      });
    },

    // 채점표 템플릿 el-table 행열 병합
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 2) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1,
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0,
          };
        }
      }
    },
    // 채점표 템플릿 Style 적용 ( 구현 미완료 )
    tableRowClasscompany({ rowIndex, columnIndex }) {
      if (columnIndex === 1 || columnIndex === 2 || rowIndex === 1) {
        console.log(rowIndex);
        return "warning-row";
      }
      return "";
    },
  },
};
</script>
<style scoped>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
  color: black;
}
.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}

h2 {
  text-align: center;
  margin-bottom: 6%;
}
.el-container {
  margin: 0 auto;
  margin-top: 2%;
  margin-bottom: 2%;
  width: 80%;
}

p {
  /* float: left; */
  margin: 30px 0 10px 0;
}
.el-dialog {
  display: flex;
  color: #99a9bf;
}
.el-table {
  display: flex;
  width: 100%;
  table-layout: fixed;
}
.el-table .el-table-column {
  width: 100%;
}
.confirm-btn {
  color: white;
  width: 30%;
}
.el-button:not(.confirm-btn) {
  width: 96%;
  margin-top: 30px;
  color: white;
}
.el-select {
  width: 100%;
}

.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.outcontainer {
  height: 300px;
  border: 3px solid #d3daff;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
  border-radius: 35px;
  height: 300px;
}
</style>
