<template>
  <div>
    <!-- <ModalView v-if="showModal">안녕</ModalView> -->
    <el-container>
      <el-main class="outline-box">
        <h2 class="text-h6 mb-3">스터디 만들기</h2>
        <el-form>
          <el-row :gutter="20">
            <el-col><p>제목</p></el-col>
            <el-col><el-input placeholder="제목을 입력하세요." /></el-col>
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
              <el-input placeholder="기업을 입력하세요." />
            </el-col>
            <el-col :span="12">
              <el-select
                v-model="value"
                class="m-2"
                placeholder="직군을 선택하세요."
                size=""
              >
                <el-option
                  v-for="item in deptOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col><p>날짜</p></el-col>
            <el-col> </el-col>
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
                v-model="value"
                class="m-2 select"
                placeholder="인원 수를 선택하세요."
                size=""
              >
                <el-option
                  v-for="item in capacityOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-col>
            <el-col :span="12">
              <el-select
                v-model="value"
                class="m-2"
                placeholder="면접 유형을 선택하세요."
              >
                <el-option
                  v-for="item in studyOptions"
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
              @click="showModal = true"
            >
              채점 템플릿 선택
            </el-button>
          </el-row>

          <el-row
            :gutter="20"
            style="display: flex; justify-content: space-around"
          >
            <el-col><p>진행시간</p></el-col>

            <el-checkbox-group>
              <el-checkbox label="1시간" />
              <el-checkbox label="2시간" />
              <el-checkbox label="3시간" />
              <el-checkbox label="4시간" />
            </el-checkbox-group>
          </el-row>

          <el-row :gutter="20">
            <el-col><p>소개</p></el-col>
            <el-col
              ><el-input
                type="textarea"
                maxlength="300"
                v-model="textarea"
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
// import { parseHeight } from "element-plus/es/components/table/src/util";
import { ref } from "vue";
// import ModalView from "@/components/board/StudyScoreModal.vue";

const checkList = "1시간";
export default {
  name: "UserSignup",
  components: {
    // ModalView,
  },
  data() {
    return {
      showModal: false,
      text: ref(""),
      textarea: ref(""),
      checkList,
      studyOptions: [
        {
          value: "일대다",
          label: "일대다",
        },
        {
          value: "다대다",
          label: "다대다",
        },
      ],
      deptOptions: [
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
      capacityOptions: [
        {
          value: "1",
          label: "1",
        },
        {
          value: "2",
          label: "2",
        },
        {
          value: "3",
          label: "3",
        },
        {
          value: "4",
          label: "4",
        },
        {
          value: "5",
          label: "5",
        },
        {
          value: "6",
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
</style>
