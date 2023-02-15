<template>
  <div class="score-box" style="text-align: center; margin-left: 2%">
    <h3>당신은 면접관입니다.</h3>
    <!-- <div v-for="(li, i) in nowScoreList" :key="i"> -->
    <el-tabs type="border-card" class="demo-tabs">
      <el-tab-pane v-for="(li, i) in totalNowList" :key="i">
        <template #label>
          <span class="custom-tabs-label">
            <span
              ><b>{{ li.nickname }}</b
              >님</span
            >
          </span> </template
        ><el-tabs type="border-card" class="demo-tabs">
          <el-tab-pane>
            <template #label>
              <span class="custom-tabs-label">
                <span>자기소개서</span>
              </span>
            </template>
            <!-- 자기소개서 파일업로드 -->
            <!-- 자기소개서 파일
            <img :src="li.imgURL" alt="자기소개서 파일" /> -->
            <meeting-resume :i="i"></meeting-resume>
          </el-tab-pane>

          <el-tab-pane>
            <template #label>
              <span class="custom-tabs-label">
                <span>채점표</span>
              </span>
            </template>

            <hr />
            <div>
              <div class="templete">
                <h4>{{ scoreData[0].type }}</h4>
                <li>
                  {{ scoreData[0].question }}
                </li>
                <li>
                  {{ scoreData[1].question }}
                </li>
              </div>
              <el-rate v-model="li.ability">
                ability: {{ li.ability }}
              </el-rate>
              <hr />
              <div class="templete">
                <h4>{{ scoreData[2].type }}</h4>
                <li>
                  {{ scoreData[2].question }}
                </li>
                <li>
                  {{ scoreData[3].question }}
                </li>
              </div>
              <el-rate v-model="li.attitude">
                attitude: {{ li.attitude }}
              </el-rate>
              <hr />
              <div class="templete">
                <h4>{{ scoreData[4].type }}</h4>
                <li>
                  {{ scoreData[4].question }}
                </li>
                <li>
                  {{ scoreData[5].question }}
                </li>
              </div>
              <el-rate v-model="li.loyalty">
                loyalty: {{ li.loyalty }}
              </el-rate>
              <hr />
              <div class="templete">
                <h4>{{ scoreData[6].type }}</h4>
                <li>
                  {{ scoreData[6].question }}
                </li>
                <li>
                  {{ scoreData[7].question }}
                </li>
              </div>
              <el-rate v-model="li.solving">
                solving: {{ li.solving }}
              </el-rate>
              <hr />
              <div class="templete">
                <h4>{{ scoreData[8].type }}</h4>
                <li>
                  {{ scoreData[8].question }}
                </li>
                <li>
                  {{ scoreData[9].question }}
                </li>
              </div>
              <el-rate v-model="li.teamwork">
                teamwork: {{ li.teamwork }}
              </el-rate>
              <hr />
              <div style="margin-top: 10%; text-align: left">
                <h4>피드백:</h4>
                <el-input
                  type="textarea"
                  :rows="7"
                  placeholder="피드백을 입력해주세요..."
                  v-model="li.feedback"
                >
                </el-input>
              </div>
            </div>
            <el-button @click="saveToLocal(li)" class="subbutton" round
              >저장</el-button
            >
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
    </el-tabs>
    <!-- <h3>면접자: {{ li.id }}</h3> -->
  </div>
  <!-- </div> -->
</template>

<script>
import { mapState } from "vuex";
import MeetingResume from "../meeting/MeetingResume.vue";

const meetingStore = "meetingStore";

export default {
  name: "MeetingScore",
  components: {
    MeetingResume,
  },
  computed: {
    ...mapState(meetingStore, [
      "nowScoreList",
      "nowResumeList",
      "nicknameList",
      "totalNowList",
    ]),
  },
  data() {
    return {
      // totalNowList: [],
      scoreData: [
        {
          type: "태도",
          question: "자신감 있는 표정과 목소리인가?",
          score: 3,
          index: 0,
        },
        {
          type: "태도",
          question: "기본 준비 자세가 올바른가?",
          score: 3,
          index: 1,
        },
        {
          type: "직무역량",
          question: "지원한 직무에 대한 구체적인 이해도를 가졌는가?",
          score: 3,
          index: 2,
        },
        {
          type: "직무역량",
          question: "직무 수행에 필요한 역량을 갖췄는가?",
          score: 3,
          index: 3,
        },
        {
          type: "팀워크",
          question: "다양한 의견을 수렴한 경험이 있는가?",
          score: 3,
          index: 4,
        },
        {
          type: "팀워크",
          question: "적극적으로 참여하는 자세를 가졌는가?",
          score: 3,
          index: 5,
        },
        {
          type: "문제해결",
          question: "문제를 해결하고자 하는 열정, 끈기, 의지를 가졌는가?",
          score: 3,
          index: 6,
        },
        {
          type: "문제해결",
          question: "해결에 도움이 되는 방향성을 제시하였는가?",
          score: 3,
          index: 7,
        },
        {
          type: "기업이해도",
          question: "기업에 대한 이해도가 높은가?",
          score: 3,
          index: 8,
        },
        {
          type: "기업이해도",
          question: "퇴사 가능성이 있는가?",
          score: 3,
          index: 9,
        },
      ],
    };
  },
  created() {},
  mounted() {
    //지금 면접자의 아이디와 닉네임 리스트에서 일치하는 닉네임을 total에 넣음
    // for (let i = 0; i < this.nowScoreList.length; i++) {
    //   // console.log("참가자 아이디 >>");
    //   // console.log(this.nowScoreList[i].id);
    //   for (let j = 0; j < this.nicknameList.length; j++) {
    //     if (this.nowScoreList[i].id == this.nicknameList[j].id) {
    //       // let obj = this.nicknameList[j].nickname;
    //       // console.log("지금 면접자 닉네임 >> " + obj);
    //       let obj = this.nowScoreList[i];
    //       obj.nickname = this.nicknameList[j].nickname;
    //       this.totalNowList.push(obj);
    //       break;
    //     }
    //   }
    // }
    // console.log("총 리스트 보여주세요 >>");
    // console.log(this.totalNowList);
  },

  methods: {
    saveToLocal(list) {
      console.log(list);
      console.log("저장 버튼 눌렀어요");
    },
  },
};
</script>

<style scoped>
/* 연보라색 배경 box */
.score-box {
  min-height: 800px;
  max-height: 800px;
  width: 20%;
  background-color: #eeecf8;
  overflow-y: scroll;
  border-radius: 5%;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
}

.score-box::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
.score-box::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-radius: 15px;
}
.score-box::-webkit-scrollbar-corner {
  background: #f8f9fa;
  border-radius: 15px;
}
.score-box::-webkit-scrollbar-thumb {
  background: #c6c7e0ce;
  border-radius: 15px;
}
.score-box::-webkit-scrollbar-button {
  border-radius: 15px;
}

#score-main {
  background-color: #cddefd;
}
.templete {
  text-align: left;
  padding: 3%;
}
.subbutton {
  margin-top: 5%;
  background-color: #cddefd;
}

.hr-sect {
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: rgba(0, 0, 0, 0.35);
  font-size: 12px;
  margin: 8px 0px;
}
.hr-sect::before,
.hr-sect::after {
  content: "";
  flex-grow: 1;
  background: rgba(0, 0, 0, 0.35);
  height: 1px;
  font-size: 0px;
  line-height: 0px;
  margin: 0px 16px;
}
</style>
