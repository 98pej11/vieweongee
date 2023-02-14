import http from "./http.js";

const api = http;

// 댓글 CRUD
async function createComment(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .post(`/study/${params.study_ID}/comment`, JSON.stringify(params.info))
    .then(success)
    .catch(fail);
}

async function modifyComment(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .put(
      `/study/${params.study_ID}/comment/${params.comment_ID}`,
      JSON.stringify(params.info)
    )
    .then(success)
    .catch(fail);
}
async function deleteComment(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .delete(`/study/${params.study_ID}/comment/${params.comment_ID}`)
    .then(success)
    .catch(fail);
}
async function getAllComment(study_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/${study_ID}/comment`).then(success).catch(fail);
}

// 대댓글 CRUD
async function createReply(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .post(
      `/study/${params.study_ID}/comment/${params.comment_ID}/reply`,
      JSON.stringify(params.reply)
    )
    .then(success)
    .catch(fail);
}
async function modifyReply(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .put(
      `/study/${params.study_id}/comment/${params.comment_id}/${params.reply_id}`,
      JSON.stringify(params.info)
    )
    .then(success)
    .catch(fail);
}
async function deleteReply(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .delete(
      `/study/${params.study_id}/comment/${params.comment_id}/${params.reply_id}`
    )
    .then(success)
    .catch(fail);
}
export {
  createComment,
  modifyComment,
  deleteComment,
  getAllComment,
  createReply,
  modifyReply,
  deleteReply,
};
