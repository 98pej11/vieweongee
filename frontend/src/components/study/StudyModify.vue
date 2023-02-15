<template>
  <div>
    <el-container>
      <el-main class="outline-box">
        <h2 class="text-h6 mb-3">스터디 수정</h2>
        <el-form>
          <el-row :gutter="20">
            <el-col><p>제목</p></el-col>
            <el-col>
              <el-input v-model="modifyInfo.title" ref="title" />
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
              <el-input v-model="modifyInfo.company" disabled />
            </el-col>

            <el-col :span="12">
              <el-select v-model="modifyInfo.job" class="m-2" disabled>
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
                v-model="modifyInfo.study_datetime"
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
                v-model="modifyInfo.personnel"
                ref="personnel"
                class="m-2 select"
                placeholder="인원 수를 선택하세요."
                disabled
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
                v-model="modifyInfo.type"
                ref="type"
                class="m-2"
                placeholder="면접 유형을 선택하세요."
                disabled
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
            <el-radio-group v-model="this.modifyInfo.running_time">
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
                v-model="modifyInfo.content"
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
import { ElMessage } from "element-plus";
import { mapState, mapActions } from "vuex";
import { ref } from "vue";
import moment from "moment";
// import { useStore } from "vuex";
// import { useRouter } from "vue-router";
const studyStore = "studyStore";

export default {
  name: "studyModify",
  mounted() {
    this.setInfo();
  },
  computed: {
    ...mapState(studyStore, ["isCreated", "studyID", "studyInfo"]),
  },

  methods: {
    ...mapActions(studyStore, ["modifyConfirm", "getInfo"]),

    setInfo() {
      this.modifyInfo = this.studyInfo;
      console.log(this.modifyInfo);

      let runtime = this.studyInfo.running_time;
      if (runtime == 1) this.radio = ref("1");
      if (runtime == 2) this.radio = ref("2");
      if (runtime == 3) this.radio = ref("3");
      if (runtime == 4) this.radio = ref("4");
    },
    async confirm() {
      // datetime 포매팅
      this.modifyInfo.study_datetime =
        this.modifyInfo.study_datetime.replace(" ", "T") + ":00";
      this.modifyInfo.regist_datetime =
        this.modifyInfo.regist_datetime.replace(" ", "T") + ":00";

      const params = {
        study_ID: this.studyID,
        info: this.modifyInfo,
      };

      await this.modifyConfirm(params);

      if (this.isCreated) {
        ElMessage({
          type: "success",
          message: "수정 완료",
        });
        this.$router.push({ name: "studyview" });
      } else {
        ElMessage({
          type: "warn",
          message: "수정 중 오류 발생",
        });
      }
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

    async submitForm() {
      if (this.isEmpty()) {
        console.log("빈칸");
        this.isError = true;
        await this.sleep(3000).then(() => {
          this.isError = false;
        });
      } else this.confirm();
    },
    // 유효성 검사
    isEmpty() {
      if (
        this.modifyInfo.title == "" ||
        this.modifyInfo.type == "" ||
        this.modifyInfo.running_time == 0 ||
        this.modifyInfo.personnel == 0
      ) {
        console.log("빈칸");
        return true;
      } else return false;
    }, // 시간 지연
    sleep(ms) {
      return new Promise((r) => setTimeout(r, ms));
    },
  },

  data() {
    return {
      // 진행시간 라디오 버튼 세팅
      radio: ref("1"),
      // 유효성 검사 변수
      isError: false,
      // 모달창 이벤트 변수
      modifyInfo: {
        id: this.studyID,
        title: "",
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
        attitude: true,
        ability: true,
        teamwork: true,
        solving: true,
        loyalty: true,
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
