<template>
  <div>
    <!-- <div>
      <el-calendar :range="[new Date(2019, 2, 4), new Date(2019, 2, 24)]" />
    </div> -->
    <el-container>
      <el-main class="outline-box">
        <h2 class="text-h6 mb-3">스터디 내역 조회</h2>
        <el-table
          class="el-table"
          :data="tableData"
          style="width: 100%; font-size:medium"
          :class="tableRowClassName"
        >
          <el-table-column prop="date" label="날짜" width="150%" />
          <el-table-column prop="name" label="기업명" width="200%" />
          <el-table-column prop="address" label="항목" width="350%" />
          <!-- 버튼 -->
          <el-table-column prop="type" label="버튼" width="150%">
            <el-button
              block
              color="#9DADD8"
              size="larger"
              style="margin: 2%; width: 70%"
              @click="moveWrite()"
            >
              상세보기
            </el-button>
          </el-table-column>
        </el-table>
        <el-dialog
          class="el-dialog"
          v-model="dialogVisible"
          width="900px"
          style="border-radius: 5%; background-color: #eeecf8"
        >
          <h2 class="text-h6 mb-3" style="margin-bottom:0%">삼성 SDS 면접 스터디</h2>
          <h2 class="text-h6 mb-3" style="margin-bottom:2%; text-align: right; margin-right: 3%;">2023.02.03</h2>
          <el-tabs type="border-card" class="demo-tabs">
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <el-icon><calendar /></el-icon>
                  <span>채점표</span>
                </span>
              </template>
              <el-table
                border 
                class="el-table"
                :span-method="objectSpanMethod"
                :data="scoreData"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                :class="tableRowClassName"
              >
                <!-- <el-table-column type="selection" width="35"></el-table-column> -->
                <el-table-column border prop="type" label="대분류" width="150%" />
                <el-table-column border prop="name1" label="항목" width="400%" />
                <el-table-column border label="점수" width="300%">
                  <el-rate
                    v-model="value"
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value} points"
                  />
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane>
              <template #label>
                <span class="custom-tabs-label">
                  <el-icon><calendar /></el-icon>
                  <span>피드백</span>
                </span>
              </template>
              <ul style="font-size:large">
                <li> 아주 잘해떠요 </li>
                <li> 목소리가 너무 작았어요 </li>
                <li> 회사에 대한 이해도가 높아 보였어용 </li>
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
export default {
  name: "MyData",
  data() {
    return {
      articles: [],
      // fields: [
      //   { key: "date", label: "", tdClass: "tdClass" },
      //   // { key: "articleno", label: "글번호", tdClass: "tdClass" },
      //   { key: "company", label: "제목", tdClass: "tdSubject" },
      //   { key: "userid", label: "작성자", tdClass: "tdClass" },
      // ],
      article: null,
      value: 3.7,
      dialogVisible: false,
      // 기본 채점템플릿
      tableData: [
        {
          date: "2016-05-03",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-02",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-04",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-01",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-04",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-01",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-04",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-01",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-04",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          date: "2016-05-01",
          name: "Tom",
          address: "No. 189, Grove St, Los Angeles",
        },
      ],
      scoreData: [
        {
          type: "태도",
          name1: "자신감 있는 표정과 목소리인가?",
        },
        {
          type: "태도",
          name1: "기본 준비 자세가 올바른가?",
        },
        {
          type: "직무역량",
          name1: "지원한 직무에 대한 구체적인 이해도를 가졌는가?",
        },
        {
          type: "직무역량",
          name1: "직무 수행에 필요한 역량을 갖췄는가?",
        },
        {
          type: "팀워크",
          name1: "다양한 의견을 수렴한 경험이 있는가?",
        },
        {
          type: "팀워크",
          name1: "적극적으로 참여하는 자세를 가졌는가?",
        },
        {
          type: "문제해결",
          name1: "문제를 해결하고자 하는 열정, 끈기, 의지를 가졌는가?",
        },
        {
          type: "문제해결",
          name1: "해결에 도움이 되는 방향성을 제시하였는가?",
        },
        {
          type: "기업이해도",
          name1: "기업에 대한 이해도가 높은가?",
        },
        {
          type: "기업이해도",
          name1: "퇴사 가능성이 있는가?",
        },
      ],
    };
  },
  created() {
        http.get(`/board/users/mystudy`).then(({ data }) => {
            this.articles = data;
        });
        if(this.global_isShow){
          this.CLEAN_GLOBAL_ISSHOW();
          this.viewArticle(this.global_article);
        }
  },
  computed: {
    ...mapState(["global_article","global_isShow"]),
  },
  methods: {
    moveDetail(){
      this.dialogVisible = true;
    },
    // 채점표 템플릿 el-table 행열 병합
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (columnIndex === 0) {
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
    tableRowClassName({ rowIndex, columnIndex }) {
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
</style>