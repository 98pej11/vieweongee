<template>
  <div>
    <el-container>
      <el-main class="outline-box">
        <h2 class="text-h6 mb-3">스터디 만들기</h2>
        <el-form>
          <el-row :gutter="20">
            <el-col><p>제목</p></el-col>
            <el-col>
              <el-input
                v-model="studyFormInfo.title"
                ref="title"
                placeholder="제목을 입력하세요."
              />
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <p>기업</p>
            </el-col>
            <el-col :span="12">
              <p>직군</p>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-input
                v-model="studyFormInfo.companyName"
                ref="company"
                placeholder="기업을 입력하세요."
              />
            </el-col>

            <el-col :span="12">
              <el-select
                v-model="studyFormInfo.job"
                class="m-2"
                ref="job"
                placeholder="직군을 선택하세요."
              >
                <el-option
                  v-for="item in jobOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col><p>일시</p></el-col>
            <el-col>
              <el-date-picker
                v-model="studyFormInfo.studyDatetime"
                ref="date"
                type="datetime"
                style="width: 100%"
                placeholder="스터디 시작 시간"
                format="YYYY/MM/DD HH:mm"
                value-format="YYYY-MM-DD HH:mm"
              />
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <p>인원</p>
            </el-col>
            <el-col :span="12">
              <p>면접 유형</p>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-select
                v-model="studyFormInfo.personnel"
                ref="personnel"
                class="m-2 select"
                placeholder="인원 수를 선택하세요."
              >
                <el-option
                  v-for="item in personnelOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
            <el-col :span="12">
              <el-select
                v-model="studyFormInfo.type"
                ref="type"
                class="m-2"
                placeholder="면접 유형을 선택하세요."
              >
                <el-option
                  v-for="item in typeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-button
              color="#c7cde6"
              class="mt-10 mb-10"
              size="large"
              style="margin: 0 auto; margin-top: 3%"
              @click="showDialog"
            >
              채점 템플릿 선택
            </el-button>
            <el-dialog
              class="el-dialog"
              v-model="dialogVisible"
              width="600px"
              style="border-radius: 5%"
            >
              <el-table
                class="el-table"
                :span-method="objectSpanMethod"
                :data="tableData"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                :class="tableRowClassName"
              >
                <el-table-column type="selection" width="35"></el-table-column>
                <el-table-column prop="type" label="대분류" width="120" />
                <el-table-column prop="name1" label="항목" />
              </el-table>
              <div v-if="isChecked">
                <div style="margin-top: 3%; text-align: center; color: red">
                  * 최소 1개 이상의 대분류를 선택해주세요.
                </div>
              </div>
              <el-button
                block
                color="#9DADD8"
                size="large"
                style="margin-top: 3%; text-align: center; width: 25%"
                @click="setScoringList"
              >
                완료
              </el-button>
            </el-dialog>
          </el-row>

          <el-row
            :gutter="20"
            style="display: flex; justify-content: space-around"
          >
            <el-col><p>진행시간</p></el-col>
            <el-radio-group v-model="studyFormInfo.runningTime">
              <el-radio :label="1">1시간</el-radio>
              <el-radio :label="2">2시간</el-radio>
              <el-radio :label="3">3시간</el-radio>
              <el-radio :label="4">4시간</el-radio>
            </el-radio-group>
          </el-row>

          <el-row :gutter="20">
            <el-col><p>소개</p></el-col>
            <el-col>
              <el-input
                ref="desc"
                type="textarea"
                maxlength="300"
                v-model="studyFormInfo.content"
                class="text-area"
                :autosize="{ minRows: 5, maxRows: 5 }"
                show-word-limit
              ></el-input
            ></el-col>
          </el-row>

          <el-row :gutter="20">
            <el-button
              block
              color="#9DADD8"
              class="mt-10 mb-10 confirm-btn"
              size="large"
              style="margin: 0 auto; margin-top: 3%"
              @click="submitForm()"
            >
              완료
            </el-button>
          </el-row>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { ref } from "vue";
import moment from "moment";
// import { useStore } from "vuex";
// import { useRouter } from "vue-router";

const studyStore = "studyStore";
const multipleSelection = ref([]);

export default {
  name: "StudyCreate",
  computed: {
    ...mapState(studyStore, ["isError", "studyId"]),
  },

  methods: {
    ...mapActions(studyStore, ["createConfirm"]),

    async confirm() {
      await this.createConfirm(this.studyFormInfo);
    },

    // 스터디 생성 폼 submit
    submitForm() {
      // 1. 빈칸이 없으면
      // let err = true;
      // let msg = "";
      // !this.studyFormInfo.title &&
      //   ((msg = "제목을 입력해주세요"),
      //   (err = false),
      //   this.$refs.title.focus());
      // err &&
      //   !this.studyFormInfo.company &&
      //   ((msg = "기업을 입력해주세요"),
      //   (err = false),
      //   this.$refs.company.focus());
      // err &&
      //   !this.studyFormInfo.job &&
      //   ((msg = "직군을 입력해주세요"), (err = false), this.$refs.job.focus());
      // err &&
      //   !this.studyFormInfo.date &&
      //   ((msg = "날짜를 입력해주세요"), (err = false), this.$refs.date.focus());
      // err &&
      //   !this.studyFormInfo.personnel &&
      //   ((msg = "인원을 입력해주세요"),
      //   (err = false),
      //   this.$refs.personnel.focus());
      // err &&
      //   !this.studyFormInfo.type &&
      //   ((msg = "유형을 입력해주세요"), (err = false), this.$refs.type.focus());
      // err &&
      //   !this.studyFormInfo.runningTime &&
      //   ((msg = "진행시간을 입력해주세요"),
      //   (err = false),
      //   this.$refs.time.focus());
      // err &&
      //   !this.studyFormInfo.content &&
      //   ((msg = "내용을 입력해주세요"), (err = false), this.$refs.desc.focus());
      // if (!err) alert(msg);

      // console.log(msg);
      // console.log(this.studyFormInfo);
      // 2. 날짜 제한이 맞으면
      console.log(this.studyFormInfo);
      if (this.checkDate(this.studyFormInfo.studyDatetime)) this.confirm();
    },

    // 날짜 선택 제한
    checkDate(myDate) {
      var mine = moment(myDate, "YYYY-MM-DD HH:mm");
      var nowDate = moment();

      const diffDay = moment.duration(nowDate.diff(mine)).asDays();
      const diffTime = moment.duration(nowDate.diff(mine)).asHours();
      // console.log("날짜차이" + moment.duration(t2.diff(t1)).asDays());
      // console.log("시간차이" + moment.duration(t2.diff(t1)).asHours());

      if (diffDay > -1 && diffTime > 0) return false;
      if (diffDay > -1 && diffTime < 24) return false;
      if (diffDay < -1 && diffTime < -24) return true;
    },
    // 채점 템플릿 선택하기 버튼이벤트
    showDialog() {
      this.dialogVisible = true;
      multipleSelection.value = []; // checked option 초기화
    },
    // 채점 템플릿 checked option
    handleSelectionChange(val) {
      multipleSelection.value = val;
    },
    setScoringList() {
      if (multipleSelection.value.length == 0) this.isChecked = true;
      else {
        multipleSelection.value.forEach((el) => {
          console.log(el.type);
          if (el.type == "태도") this.studyFormInfo.attitude = 1;
          if (el.type == "직무역량") this.studyFormInfo.ability = 1;
          if (el.type == "팀워크") this.studyFormInfo.teamwork = 1;
          if (el.type == "기업이해도") this.studyFormInfo.solving = 1;
          if (el.type == "태도") this.studyFormInfo.loyalty = 1;
        });
        this.dialogVisible = false;
      }
    },
    // 채점표 템플릿 el-table 행열 병합
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 1) {
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

  data() {
    return {
      // 모달창 이벤트 변수
      dialogVisible: false,
      alertMsg: "24시간 이후로 선택해주세요",
      isChecked: false,
      // 스터디 생성 정보
      studyFormInfo: [
        {
          title: "",
          company: "",
          job: "",
          personnel: 1,
          type: "",
          studyDatetime: "",
          runningTime: 1,
          content: "",
          attitude: 0,
          ability: 0,
          teamwork: 0,
          solving: 0,
          loyalty: 0,
        },
      ],
      typeOptions: [
        {
          value: "일대다",
          label: "일대다",
        },
        {
          value: "다대다",
          label: "다대다",
        },
      ],
      // 직군 유형
      jobOptions: [
        {
          value: "연구직",
          label: "연구직",
        },
        {
          value: "소프트웨어",
          label: "소프트웨어",
        },
        {
          value: "영업마케팅직",
          label: "영업마케팅직",
        },
        {
          value: "경영지원직",
          label: "경영지원직",
        },
        {
          value: "디자인직",
          label: "디자인직",
        },
        {
          value: "기술직",
          label: "기술직",
        },
      ],
      // 인원 수
      personnelOptions: [
        {
          value: 1,
          label: "1",
        },
        {
          value: 2,
          label: "2",
        },
        {
          value: 3,
          label: "3",
        },
        {
          value: 4,
          label: "4",
        },
        {
          value: 5,
          label: "5",
        },
        {
          value: 6,
          label: "6",
        },
      ],
      // 기본 채점템플릿
      tableData: [
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
};
</script>

<style scoped>
h2 {
  text-align: center;
}
.el-container {
  margin: 0 auto;
  margin-top: 2%;
  margin-bottom: 2%;
  width: 45%;
}

p {
  float: left;
  margin: 30px 0 10px 0;
}
.el-dialog {
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
