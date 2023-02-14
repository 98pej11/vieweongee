import {
  createComment,
  modifyComment,
  deleteComment,
  getAllComment,
  createReply,
  modifyReply,
  deleteReply,
} from "@/api/comment";

const commentStore = {
  namespaced: true,
  state: { studyID: 0, isComment: false, commentList: [] },
  getters: {},
  mutations: {
    SET_IS_SUCCESS: (state, isComment) => {
      state.isComment = isComment;
    },
    SET_STUDY_ID: (state, studyID) => {
      state.studyID = studyID;
    },
    SET_MY_COMMENT: (state, info) => {
      state.commentList.push(info);
    },
    SET_ALL_COMMENT: (state, list) => {
      state.commentList = [];
      state.commentList = list;

      state.commentList.forEach((el) => {
        el.datetime = el.datetime.substr(0, 16).replace("T", " ");
      });
    },
  },
  actions: {
    // 전체 댓글 조회
    async getCommentList({ commit }, study_ID) {
      await getAllComment(
        study_ID,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            console.log(data.data);
            commit("SET_ALL_COMMENT", data.data);
            commit("SET_IS_SUCCESS", true);
          }
          // 댓글이 없을 때
          else {
            commit("SET_IS_SUCCESS", false);
          }
        },
        async (error) => {
          if (error.response.status === 401) {
            console.log("401에러");
            commit("SET_IS_SUCCESS", false);
          }
        }
      );
    },
    // 댓글 작성
    async createCommentConfirm({ commit }, params) {
      await createComment(
        params,
        // comment_id가 반환됨
        ({ data }) => {
          console.log(data.message);

          if (data.message == "SUCCESS") {
            commit("SET_STUDY_ID", data.data);
            commit("SET_IS_SUCCESS", true);
          } else if (data.data == "null") {
            console.log("이젠 댓글X");
            commit("SET_IS_SUCCESS", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          console.log(error);
          commit("SET_IS_SUCCESS", false);
        }
      );
    },

    // 댓글 수정
    async modifyCommentConfirm({ commit }, params) {
      await modifyComment(
        params,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            commit("SET_IS_SUCCESS", true);
          }
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
    async deleteCommentConfirm({ commit }, params) {
      console.log(params.comment_ID);
      await deleteComment(
        params,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            commit("SET_STUDY_ID", data.data);
            commit("SET_IS_SUCCESS", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          commit("SET_IS_SUCCESS", false);
          console.log(error);
        }
      );
    },

    // 대댓글 작성
    async createReplyConfrim({ commit }, params) {
      console.log(params);
      await createReply(params, ({ data }) => {
        console.log(data.data);
        commit("SET_IS_SUCCESS", true);
      });
    },
    // 대댓글 수정
    async modifyReplyConfirm({ commit }, params) {
      await modifyReply(
        params,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            commit("SET_IS_SUCCESS", true);
          }
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
    // 대댓글 삭제
    async deleteReplyConfirm({ commit }, params) {
      console.log(params.comment_ID);
      await deleteReply(
        params,
        ({ data }) => {
          if (data.message == "SUCCESS") {
            commit("SET_STUDY_ID", data.data);
            commit("SET_IS_SUCCESS", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          commit("SET_IS_SUCCESS", false);
          console.log(error);
        }
      );
    },
  },
};

export default commentStore;
