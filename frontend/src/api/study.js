import http from "./http.js";

const api = http;

// 글 검색
async function getSearch(words, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/search/${words}`).then(success).catch(fail);
}

// 게시글 CRUD
async function createStudy(info, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");

  await api
    .post(`/users/study`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
// 메인화면 글 3개 조회
async function getTopStudy(success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/top3`).then(success).catch(fail);
}
// 전체 글 조회
async function getAllStudy(success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study`).then(success).catch(fail);
}

// 글 1개 상세정보 조회
async function getStudy(study_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/${study_ID}`).then(success).catch(fail);
}
async function modifyStudy(study_ID, info, success, fail) {
  // api.defaults.headers["ACCESS"] = sessionStorage.getItem("accessToken");
  await api
    .put(`/study/${study_ID}`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function deleteStudy(study_ID, success, fail) {
  // api.defaults.headers["ACCESS"] = sessionStorage.getItem("accessToken");
  await api.delete(`/study/${study_ID}`).then(success).catch(fail);
}

// 댓글 CRUD
async function createComment(study_ID, info, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");

  await api
    .post(`/study/${study_ID}/comment`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function modifyComment(study_ID, comment_ID, info, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .put(`/study/${study_ID}/comment/${comment_ID}`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}
async function deleteComment(study_ID, comment_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .delete(`/study/${study_ID}/comment/${comment_ID}`)
    .then(success)
    .catch(fail);
}
async function getAllComment(study_ID, success, fail) {
  await api.post(`/study/${study_ID}/comment`).then(success).catch(fail);
}

export {
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
  getSearch,
};
