import { getInterviewOrder } from "../api/meeting";

const meetingStore = {
  namespaced: true,
  state: {
    myId: null, //나의 아이디
    storePublisher: undefined, //스트림이 연결 되었는지 확인용

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
    SET_STORE_PUBLISHER(state, publisher) {
      state.storePublisher = publisher;
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
    // ADD_INTERVIEWEECOUNT(state) {
    //   //면접자가 나가기 버튼을 누를 때마다 추가
    //   state.intervieweeCount++;
    // },
    // CLEAR_INTERVIEWEECOUNT(state) {
    //   //면접 회차가 변경되면 초기화
    //   state.intervieweeCount = 0;
    // },
    SET_TOTALTURN(state, turn) {
      state.totalTurn = turn;
    },
    SET_NOWTURN(state, turn) {
      state.nowTurn = turn;
    },
    SET_LEADER_TURN(state, turn) {
      state.leaderTurn = turn;
    },
  },
  actions: {
    async getOrder({ commit }, params) {
      await getInterviewOrder(
        params,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            //정상적으로 받아왔을 때
            //면접 순서 저장 처리
            // commit("SET_INTERVIEW_ORDER", data.data);
            commit("SET_LEADER_ORDER", data.data);
            // console.log(data.data);
            // console.log("리더의 순서 지정 성공 >> " + state.leaderOrder);
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
    setInterviewList({ commit }, order) {
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
      // console.log("면접 리스트 >> ");
      // console.log(resultList);
    },
  },
};
export default meetingStore;
