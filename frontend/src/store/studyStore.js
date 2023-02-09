// import router from "@/router";

import {
  getSearch,
  createStudy,
  getTopStudy,
  getAllStudy,
  getStudy,
  modifyStudy,
  deleteStudy,
  createComment,
  modifyComment,
  deleteComment,
  getAllComment,
  applyStudy,
  cancleStudy,
  getCurrent,
  getMyStudy,
} from "@/api/study";

const studyStore = {
  namespaced: true,
  state: {
    isCreated: false,
    isApplied: false, // 신청 여부

    appliedList: [], // 내가 신청한 리스트

    studyID: 0, // 검색 결과
    noResult: false, // 신청자 수
    current: 0,
    currentList: [],
    studyList: [
      {
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
    ],
    studyInfo: {
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
    commentList: [
      {
        depth: 1,
        user_id: 0,
        user_nickname: "",
        comment_id: 0,
        reply_id: 0,
        content: "",
        datetime: "",
      },
    ],
  },
  getters: {},
  mutations: {
    // SET_LOGIN_ID: (state, token_id) => {
    //   state.token_id = token_id;
    // },
    CLEAR_LIST: (state) => {
      state.studyList = [];
      state.commentList = [];
      state.currentList = [];
      state.noResult = false;
      state.isApplied = false;
    },
    SET_IS_SUCCESS: (state, isCreated) => {
      state.isCreated = isCreated;
    },
    // 스터디 신청 성공
    SET_APPLY_SUCCESS: (state, isApplied) => {
      state.isApplied = isApplied;
    },
    // 스터디 신청 여부
    SET_MY_APPLIED: (state, list) => {
      state.appliedList = list;
    },

    // 검색 결과
    SET_SEARCH_RESULT: (state, noResult) => {
      state.noResult = noResult;
    },
    // 현재 글의 신청자 수
    SET_NOW_PERSON: (state, current) => {
      state.current = current;
    },
    // 현재 신청자 수 리스트
    PUSH_CURRENT_LIST: (state, person) => {
      state.currentList.push(person);
    },
    SET_STUDY_ID: (state, studyID) => {
      state.studyID = studyID;
    },
    SET_STUDY_LIST: (state, list) => {
      list.forEach((el) => {
        // DateTime 포맷
        var dateFormat = el.study_datetime.substr(0, 16);
        dateFormat = dateFormat.replace("T", " ");

        state.studyList.push({
          id: el.id,
          title: el.title,
          company: el.company,
          job: el.job,
          personnel: el.personnel,
          type: el.type,
          user_id: el.user_id,
          user_nickname: el.user_nickname,
          study_datetime: dateFormat,
          regist_datetime: el.regist_datetime,
          running_time: el.running_time,
          content: el.content,
        });
      });
    },
    SET_STUDY_INFO: (state, studyInfo) => {
      state.studyInfo = studyInfo;
      var dateFormat1 = studyInfo.study_datetime.substr(0, 16);
      var dateFormat2 = studyInfo.regist_datetime.substr(0, 16);
      dateFormat1 = dateFormat1.replace("T", " ");
      dateFormat2 = dateFormat2.replace("T", " ");

      state.studyInfo.study_datetime = dateFormat1;
      state.studyInfo.regist_datetime = dateFormat2;
    },
    SET_ALL_COMMENT: (state, list) => {
      list.forEach((el) => {
        state.commentList.push({
          depth: el.depth,
          user_id: el.user_id,
          user_nickname: el.user_nickname,
          comment_id: el.comment_id,
          reply_id: el.reply_id,
          content: el.content,
          datetime: el.datetime,
        });
      });
    },
  },
  actions: {
    // 스터디 검색
    async searchConfirm({ commit }, words) {
      await getSearch(words, ({ data }) => {
        if (data.data.length == 0) {
          console.log("검색결과가 없습니다");
          commit("SET_SEARCH_RESULT", true);
        } else {
          commit("CLEAR_LIST");
          commit("SET_STUDY_LIST", data.data);
        }
      });
    },
    // 스터디 전체 글 조회 (메인페이지)
    async getTopList({ commit }) {
      await getTopStudy(({ data }) => {
        commit("SET_STUDY_LIST", data.data);
      }, {});
    },
    // 스터디 전체 글 조회 (스터디게시판)
    async getAllList({ commit }) {
      await getAllStudy(({ data }) => {
        // console.log(data.data);
        commit("SET_STUDY_LIST", data.data);
      });
    },

    // 스터디 생성
    async createConfirm({ commit }, studyInfo) {
      await createStudy(
        studyInfo,
        ({ data }) => {
          console.log(data.data);
          console.log("생성성공");
          // console.log(data.headers);
          // console.log(data.body);
          commit("SET_STUDY_ID", data);
        }
        // async (error) => {
        //   // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        //   if (error.response.status === 401) {
        //     console.log("401에러");
        //     commit("SET_IS_SUCCESS", false);
        //   }
        // }
      );
    },

    // 스터디 상세보기
    async getInfo({ commit }, params) {
      await getStudy(
        params,
        ({ data }) => {
          // 이미 신청한 스터디
          if (data.message == "MINE") {
            commit("SET_APPLY_SUCCESS", true);
          }
          // 신청하지 않은 스터디
          else {
            commit("SET_APPLY_SUCCESS", false);
          }
          commit("SET_STUDY_INFO", data.data);
        },
        async (error) => {
          console.log(error);
        }
      );
    },
    // 스터디 수정
    async modifyConfirm({ commit }, study_ID, info) {
      await modifyStudy(
        study_ID,
        info,
        (data) => {
          console.log(data);
          commit("SET_STUDY_ID", data.body);
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        }
      );
    },
    // 스터디 삭제
    async deleteConfirm({ commit }, study_ID) {
      await deleteStudy(study_ID, ({ data }) => {
        console.log(data);
        console.log(data.data);
        console.log(data.message);
        commit("SET_IS_SUCCESS", true);
        if (data.message == "SUCCESS") console.log("삭제성공이래");
      });
    },

    // 스터디 참가자 수 조회
    async getPersonnel({ commit }, study_ID) {
      await getCurrent(study_ID, ({ data }) => {
        console.log("현재 참가자 수 : " + data.data);
        commit("SET_NOW_PERSON", data.data);
        commit("PUSH_CURRENT_LIST", data.data);
      });
    },

    // 스터디 참가 신청하기
    async applyStudyConfirm({ commit }, studyId) {
      await applyStudy(studyId, ({ data }) => {
        console.log("참가신청 완료" + data.data);
        console.log("참가신청 완료" + data.message);
        commit("SET_APPLY_SUCCESS", true);
      });
    },

    // 스터디 참가 신청 취소하기
    async cancleStudyConfirm({ commit }, studyId) {
      await cancleStudy(studyId, () => {
        console.log("참가신청 취소 완료");
        commit("SET_APPLY_SUCCESS", false);
      });
    },

    // 신청한 스터디 목록 불러오기
    async getMyApplied({ commit }) {
      await getMyStudy(({ data }) => {
        console.log("내가 신청한 스터디는? store");
        console.log(data.data);
        if (data.message == "SUCCESS") {
          // console.log(data.message);
          commit("SET_APPLY_SUCCESS", true);
          commit("SET_MY_APPLIED", data.data);
        }
      });
    },

    // 댓글 작성
    async createCommentConfirm({ commit }, { studyId, myComment }) {
      console.log("컨펌 in  : " + studyId + " / " + myComment);
      await createComment(
        studyId,
        myComment,
        // comment_id가 반환됨
        ({ data }) => {
          console.log("작성성공  : " + myComment);
          console.log("작성성공 : " + data);
          commit("SET_STUDY_ID", studyId);
        }
        // async (error) => {
        //   // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        //   if (error.response.status === 401) {
        //     console.log("401에러");
        //     commit("SET_IS_SUCCESS", false);
        //   }
        // }
      );
    },
    // 댓글 수정
    async modifyCommentConfirm({ commit }, study_ID, comment_ID, info) {
      await modifyComment(
        study_ID,
        comment_ID,
        info,
        (data) => {
          console.log(data);
          commit("SET_STUDY_ID", data.body);
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        }
      );
    },
    // 댓글 삭제
    async deleteCommentConfirm({ commit }, study_ID, comment_ID, info) {
      await deleteComment(
        study_ID,
        comment_ID,
        info,
        (data) => {
          console.log(data);
          commit("SET_STUDY_ID", data.body);
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        }
      );
    },
    // 전체 댓글 조회
    async getCommentList({ commit }, study_ID) {
      await getAllComment(
        study_ID,
        ({ data }) => {
          if (data !== "") commit("SET_ALL_COMMENT", data);
          else console.log("댓글 아직 없음");
        },
        async (error) => {
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        }
      );
    },
  },
};

export default studyStore;
