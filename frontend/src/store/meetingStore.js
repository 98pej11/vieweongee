import { getInterviewOrder } from "../api/meeting";

const meetingStore = {
  namespaced: true,
  state: {
    myId: null, //나의 아이디
    storePublisher: undefined, //스트림이 연결 되었는지 확인용

    isLeader: false, //방장
    isInterviewee: false, //면접자
    isInterviewer: false, //면접관

    interviewOrder: null, //면접 순서 시그널
    interviewOrderList: [], //면접 순서 리스트
    // nowTurn: 0, //현재 면접 회차

    scoreList: [], //스터디원의 채점표 리스트 (추후 자기소개서도)

    intervieweeRate: 0, //스터디장이 설정한 면접자의 수
    // intervieweeCount: 0, //나가기를 누른 면접자의 수
    totalTurn: null, //면접의 총 회차
    nowTurn: null, //현재 면접의 회차
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
  },
  actions: {
    async getOrder({ commit }, study_ID, cnt) {
      await getInterviewOrder(
        study_ID,
        cnt,
        ({ data }) => {
          console.log("받아온 면접 순서 >> ");
          console.log(data);
          //면접 순서 저장 처리
          // commit("SET_INTERVIEW_ORDER", data)
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default meetingStore;
