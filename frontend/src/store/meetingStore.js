import {
  getInterviewOrder,
  postScorecards,
  getAllScorecards,
  putScore,
  getAllResume,
  putConfirmStatus,
} from "../api/meeting";

const meetingStore = {
  namespaced: true,
  state: {
    storesession: undefined,
    myId: null, //나의 아이디

    isLeader: false, //방장
    isInterviewee: false, //면접자
    isInterviewer: false, //면접관
    isLeaveSession: false, //세션 나갔는지 확인

    leaderOrder: null,
    interviewOrder: null, //면접 순서 시그널
    interviewOrderList: [], //면접 순서 리스트
    // nowTurn: 0, //현재 면접 회차

    scoreList: [], //스터디원의 채점표 리스트 (추후 자기소개서도)
    nowScoreList: [], //현재 면접자의 채점표만 보기 위한 리스트
    resumeList: [],
    nowResumeList: [],

    intervieweeRate: 0, //스터디장이 설정한 면접자의 수
    // intervieweeCount: 0, //나가기를 누른 면접자의 수
    totalTurn: null, //면접의 총 회차
    nowTurn: null, //현재 면접의 회차
    leaderTurn: null,

    isShowChat: false, // 채탕 보여줄래 말래
  },
  getters: {},
  mutations: {
    SET_SESSION(state, session) {
      state.session = session;
    },
    SET_MYID(state, id) {
      //나의 아이디 설정
      state.myId = id;
    },
    SET_IS_CHAT(state, flag) {
      //면접자로 설정/해제
      state.isShowChat = flag;
    },
    SET_IS_LEADER(state, flag) {
      //방장으로 설정/해체
      state.isLeader = flag;
    },
    SET_IS_INTERVIEWEE(state, flag) {
      //면접자로 설정/해제
      state.isInterviewee = flag;
    },
    SET_IS_INTERVIEWER(state, flag) {
      //면접관으로 설정/해제
      state.isInterviewer = flag;
    },
    SET_INTERVIEW_ORDER(state, order) {
      //면접 순서 지정
      state.interviewOrder = order;
    },
    SET_LEADER_ORDER(state, order) {
      state.leaderOrder = order;
    },
    SET_INTERVIEW_ORDER_LIST(state, list) {
      //실제 면접 순서를 리스트로 변환
      state.interviewOrderList = list;
    },
    SET_INTERVIEWEERATE(state, cnt) {
      state.intervieweeRate = cnt; //방장이 설정한 면접자 수
    },
    SET_TOTALTURN(state, turn) {
      state.totalTurn = turn;
    },
    SET_NOWTURN(state, turn) {
      state.nowTurn = turn;
    },
    SET_LEADER_TURN(state, turn) {
      state.leaderTurn = turn;
    },
    SET_SCORE_LIST(state, list) {
      state.scoreList = list;
    },
    SET_NOW_SCORE_LIST(state, list) {
      state.nowScoreList = list;
    },
    SET_RESUME_LIST(state, list) {
      state.resumeList = list;
    },
    SET_NOW_RESUME_LIST(state, list) {
      state.nowResumeList = list;
    },
    SET_IS_LEAVE_SESSION(state, flag) {
      //세션 나가기 버튼
      state.isLeaveSession = flag;
    },
  },
  actions: {
    async makeScoreAndGetOrder({ commit, state }, params) {
      // console.log("1!!!!! 액션진입");
      //채점표를 생성한다
      await postScorecards(
        params.study_ID,
        ({ data }) => {
          // console.log("방장님 채점표가 어떻게 됐을까요");
          // console.log(data);
          if (data.message == "SUCCESS") {
            console.log("방장님 채점표가 생성됐어요");
          }
        },
        (error) => {
          console.log(error);
        }
      );
      // console.log("2!!!!! 채점표생성완료");
      //면접 순서를 받아온다
      await getInterviewOrder(
        params,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            //정상적으로 받아왔을 때 채점표 생성부터 처리
            // console.log("방장님 면접 순서 잘 받아왔는데 채점표부터 생성할게요");
            // dispatch("makeScorecards", params.study_ID);
            //면접 순서 저장 처리
            // commit("SET_INTERVIEW_ORDER", data.data);
            commit("SET_LEADER_ORDER", data.data);
            // console.log(data.data);
            console.log("리더의 순서 지정 성공 했어요 >> " + state.leaderOrder);
          }
        },
        (error) => {
          console.log(error);
        }
      );
      // console.log("3!!!!! 면접 순서 불러오고 저장 완료");
    },
    async makeScorecards(study_ID) {
      //채점표 생성하기
      await postScorecards(
        study_ID,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            //정상처리
            console.log("방장님 채점표 생성 완료했어요");
            console.log(data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getScorecards({ commit, state }, study_ID) {
      //채점표 전부 가져오기
      await getAllScorecards(
        study_ID,
        ({ data }) => {
          // console.log("채점표가 잘 가져와졌을까요");
          // console.log(data);
          if (data.message == "SUCCESS") {
            // console.log("채점표 전부 가져왔어요");
            //   console.log(data.data);
            commit("SET_SCORE_LIST", data.data);
            console.log("스코어리스트 보여드릴게요");
            console.log(state.scoreList);
          }
        },
        (error) => {
          console.log(error);
        }
      );

      await getAllResume(
        study_ID,
        ({ data }) => {
          console.log("자기소개서 가져왔나요");
          console.log(data);
          commit("SET_RESUME_LIST", data.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setLeader({ commit }, flag) {
      //아이디가 맞으면 리더로 설정
      commit("SET_IS_LEADER", flag);
    },
    setMyid({ commit }, id) {
      commit("SET_MYID", id);
    },
    setIntervieweeRate({ commit }, cnt) {
      //방장이 설정한 면접자 인원 설정
      commit("SET_INTERVIEWEERATE", cnt);
    },
    setInterviewList({ commit, state }, order) {
      //총 회차 설정
      commit("SET_TOTALTURN", order.split("!")[0]);

      // & 로 분리
      let turnList = order.split("!")[1].split("&");

      // = 로 분리
      let tempList = [];
      turnList.forEach((element) => {
        tempList.push(element.split("=")[1]);
      });

      let resultList = [];
      tempList.forEach((element) => {
        // , 가 있는지 확인
        if (element.indexOf(",") != -1) {
          let temp = element.split(",");
          resultList.push(temp);
        } else {
          resultList.push(element);
        }
      });

      //면접자의 아이디 리스트 저장
      commit("SET_INTERVIEW_ORDER_LIST", resultList);
      console.log("면접 리스트 >> ");
      console.log(state.interviewOrderList);

      //현재 회차에 처음 회차 설정
      commit("SET_NOWTURN", 0);
      //리더의 회차도 처음으로 초기화
      commit("SET_LEADER_TURN", 0);
    },
    setShowScoreList({ commit, state }, turn) {
      //면접관이라면 채점표 저장
      // if (state.isInterviewer) {
      let list = [];
      let rlist = [];
      //현재 회차의 면접자 아이디, 채점표의 아이디 비교하여 해당하는 것만 보여줌
      for (let i = 0; i < state.interviewOrderList[turn].length; i++) {
        //현재 회차의 면접자수만큼
        for (let j = 0; j < state.scoreList.length; j++) {
          //스터디 참가자 수만큼
          if (state.interviewOrderList[turn][i] == state.scoreList[j].id) {
            //현재회차아이디와 채점표 아이디가 같으면
            list.push(state.scoreList[j]);
            rlist.push(state.resumeList[j]);
            break;
          }
        }
      }
      commit("SET_NOW_SCORE_LIST", list);
      console.log("보여줄 채점표 보여줄게요");
      console.log(state.nowScoreList);
      commit("SET_NOW_RESUME_LIST", rlist);
      console.log("자소서 이미지도 보여줄게요");
      console.log(state.nowResumeList);
      // } else {
      //면접관이 아니라면
      // console.log("채점표를 볼 수 없어요");
      // }
    },
    async saveScore({ state }, study_ID) {
      //로컬 스토리지에 있는 지금 면접자꺼 다 가져옴
      console.log("총 면접자");
      console.log(state.nowScoreList);
      for (let i = 0; i < state.nowScoreList.length; i++) {
        console.log("현재 면접자 아이디 >> " + state.nowScoreList[i].id);
        // let id = state.nowScoreList[i].id;
        // let saveData = JSON.parse(localStorage.getItem(`save#${id}`));

        //put 요청
        // const params = {
        //   study_ID: study_ID,
        //   data: saveData,
        // };
        const params = {
          study_ID: study_ID,
          data: state.nowScoreList[i],
        };

        await putScore(
          params,
          ({ data }) => {
            console.log("채점표 저장이 됐을까요");
            console.log(data);
            if (data.message == "SUCCESS") {
              console.log("저장이 완료됐어요");
            }
          },
          (error) => {
            console.log(error);
          }
        );
        //저장이 성공하면 로컬 스토리지 클리어
      }
    },
    async changeConfirmAndStatus({ state }, study_ID) {
      console.log(state.nowTurn);
      await putConfirmStatus(
        study_ID,
        ({ data }) => {
          console.log("스터디 confirm, 진행상황 status 잘 처리 됐을까요");
          if (data.message == "SUCCESS") {
            console.log("진행상황 처리가 잘 됐어요");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default meetingStore;
