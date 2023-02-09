import { getInterviewOrder, postScorecards, getAllScorecards } from "../api/meeting";

const meetingStore = {
  namespaced: true,
  state: {
    myId: null, //나의 아이디

    isLeader: false, //방장
    isInterviewee: false, //면접자
    isInterviewer: false, //면접관

    leaderOrder: null,
    interviewOrder: null, //면접 순서 시그널
    interviewOrderList: [], //면접 순서 리스트
    // nowTurn: 0, //현재 면접 회차

    scoreList: [], //스터디원의 채점표 리스트 (추후 자기소개서도)

    intervieweeRate: 0, //스터디장이 설정한 면접자의 수
    // intervieweeCount: 0, //나가기를 누른 면접자의 수
    totalTurn: null, //면접의 총 회차
    nowTurn: null, //현재 면접의 회차
    leaderTurn: null,
  },
  getters: {},
  mutations: {
    SET_MYID(state, id) {
      //나의 아이디 설정
      state.myId = id;
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
      //현재 회차에 처음 회차 설정
      commit("SET_NOWTURN", 0);
      //리더의 회차도 처음으로 초기화
      commit("SET_LEADER_TURN", 0);

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
    },
  },
};
export default meetingStore;
