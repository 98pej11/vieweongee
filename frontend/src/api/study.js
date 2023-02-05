import http from "./http.js";

const api = http;

// 게시글 CRUD
async function createStudy(info, success, fail) {
  api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");

  await api
    .post(`/users/study`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function getAllStudy(success, fail) {
  await api.get(`/users/study`).then(success).catch(fail);
}
async function getStudy(study_ID, success, fail) {
  await api.get(`/users/study/${study_ID}`).then(success).catch(fail);
}
async function modifyStudy(study_ID, info, success, fail) {
  api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  await api
    .put(`/users/study/${study_ID}`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function deleteStudy(study_ID, success, fail) {
  api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  await api.delete(`/users/study/${study_ID}`).then(success).catch(fail);
}

// 댓글 CRUD
async function createComment(study_ID, info, success, fail) {
  api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");

  await api
    .post(`/users/study/${study_ID}/comment`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function modifyComment(study_ID, comment_ID, info, success, fail) {
  api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  await api
    .put(`/users/study/${study_ID}/comment/${comment_ID}`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function deleteComment(study_ID, comment_ID, success, fail) {
  api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
  await api
    .delete(`/users/study/${study_ID}/comment/${comment_ID}`)
    .then(success)
    .catch(fail);
}
async function getAllComment(study_ID, success, fail) {
  await api.post(`/users/study/${study_ID}/comment`).then(success).catch(fail);
}

export {
  createStudy,
  getAllStudy,
  getStudy,
  modifyStudy,
  deleteStudy,
  createComment,
  modifyComment,
  deleteComment,
  getAllComment,
};
