// import router from "@/router";

import {
  createStudy,
  getAllStudy,
  getStudy,
  modifyStudy,
  deleteStudy,
  createComment,
  modifyComment,
  deleteComment,
  getAllComment,
} from "@/api/study.js";

const studyStore = {
  namespaced: true,
  state: {
    isCreated: false,
    studyID: 0,
    studyList: [
      {
        title: "",
        company: "",
        job: "",
        personnel: 1,
        type: "",
        user_id: "",
        user_nickname: "",
        study_datetime: "",
        regist_datetime: "",
        running_time: 1,
        content: "",
      },
    ],
    studyInfo: {
      study_title: "",
      company: "",
      job: "",
      personnel: 1,
      type: "",
      user_id: "",
      user_nickname: "",
      study_datetime: "",
      regist_datetime: "",
      running_time: 1,
      content: "",
    },
    commentList: [
      {
        depth: 1,
        user_id: "",
        user_nickname: "",
        comment_id: "",
        reply_id: "",
        content: "",
        datetime: "",
      },
    ],
  },
  getters: {},
  mutations: {
    SET_IS_SUCCESS: (state, isCreated) => {
      state.isCreated = isCreated;
    },
    SET_STUDY_ID: (state, studyId) => {
      state.studyId = studyId;
    },
    SET_ALL_STUDY: (state, list) => {
      list.forEach((el) => {
        state.studyList.push({
          id: el.id,
          comment: el.comment,
          user: el.user,
          content: el.content,
          datetime: el.datetime,
        });
      });
    },
    SET_STUDY_INFO: (state, studyInfo) => {
      state.studyInfo = studyInfo;
    },
    SET_ALL_COMMENT: (state, list) => {
      list.forEach((el) => {
        state.commentList.push({
          id: el.id,
          comment: el.comment,
          user: el.user,
          content: el.content,
          datetime: el.datetime,
        });
      });
    },
  },
  actions: {
    // 스터디 생성
    async createConfirm({ commit }, studyInfo) {
      await createStudy(
        studyInfo,
        (data) => {
          console.log(data);
          // console.log(data.headers);
          // console.log(data.body);
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
    // 스터디 전체 목록
    async getList({ commit }) {
      await getAllStudy(
        (data) => {
          console.log(data);
          commit("SET_ALL_STUDY", data);
        },
        async (error) => {
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        },
        async (error) => {
          console.log(error);
        }
      );
    },
    // 스터디 상세보기
    async getInfo({ commit }, study_ID) {
      await getStudy(
        study_ID,
        (data) => {
          console.log(data);
          commit("SET_STUDY_INFO", data);
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
      await deleteStudy(
        study_ID,
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
    // 댓글 작성
    async createCommentConfirm({ commit }, study_ID, info) {
      await createComment(
        study_ID,
        info,
        (data) => {
          console.log(data);
          // console.log(data.headers);
          // console.log(data.body);
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
    async getcommentList({ commit }, study_ID) {
      await getAllComment(
        study_ID,
        (data) => {
          console.log(data);
          commit("SET_ALL_COMMENT", data);
        },
        async (error) => {
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        },
        async (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default studyStore;
