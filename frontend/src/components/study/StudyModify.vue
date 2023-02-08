<template>
  <div>
    <el-container>
      <el-main class="outline-box">
        <h2 class="text-h6 mb-3">스터디 수정</h2>
        <el-form>
          <el-row :gutter="20">
            <el-col><p>제목</p></el-col>
            <el-col>
              <el-input v-model="studyInfo.title" ref="title" />
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
              <el-input v-model="studyInfo.company" disabled />
            </el-col>

            <el-col :span="12">
              <el-select v-model="studyInfo.job" class="m-2" disabled>
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
                v-model="studyInfo.study_datetime"
                ref="date"
                type="datetime"
                style="width: 100%"
                format="YYYY/MM/DD HH:mm"
                value-format="YYYY-MM-DD HH:mm"
                disabled
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
                v-model="studyInfo.personnel"
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
                v-model="studyInfo.type"
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

          <el-row
            :gutter="20"
            style="display: flex; justify-content: space-around"
          >
            <el-col><p>진행시간</p></el-col>
            <el-radio-group v-model="this.studyInfo.running_time">
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
                v-model="studyInfo.content"
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

export default {
  name: "studyModify",
  mounted() {
    this.setRadio();
  },
  computed: {
    ...mapState(studyStore, ["modifyError", "studyID", "studyInfo"]),
  },

  methods: {
    ...mapActions(studyStore, ["modifyConfirm", "getInfo"]),

    setRadio() {
      let runtime = this.studyInfo.running_time;
      if (runtime == 1) this.radio = ref("1");
      if (runtime == 2) this.radio = ref("2");
      if (runtime == 3) this.radio = ref("3");
      if (runtime == 4) this.radio = ref("4");
    },
    async confirm() {
      await this.modifyConfirm(this.studyID, this.myStudy);
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

    submitForm() {
      this.modifyInfo = this.studyInfo;
    },
  },

  data() {
    return {
      // 진행시간 라디오 버튼 세팅
      radio: ref("1"),
      // 모달창 이벤트 변수
      modifyInfo: {
        id: 0,
        study_title: "",
        company: "",
        job: "",
        personnel: 1,
        type: "",
        user_id: 0,
        user_nickname: "",
        study_datetime: "",
        regist_datetime: "",
        running_time: 1,
        content: "",
      },
      alertMsg: "24시간 이후로 선택해주세요",
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
