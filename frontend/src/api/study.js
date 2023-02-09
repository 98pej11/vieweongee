import http from "./http.js";

const api = http;

// 글 검색
async function getSearch(words, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/search/${words}`).then(success).catch(fail);
}

// 메인화면 글 3개 조회
async function getTopStudy(success, fail) {
  // api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/top3`).then(success).catch(fail);
}

// 전체 글 조회
async function getAllStudy(success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study`).then(success).catch(fail);
}

// 스터디 1개 상세정보 조회
async function getStudy(params, success, fail) {
  console.log(params);
  // api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .get(`/study/detail/${params.study_ID}/${params.user_ID}`)
    .then(success)
    .catch(fail);
}

// 스터디 생성
async function createStudy(info, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.post(`/study`, JSON.stringify(info)).then(success).catch(fail);
}

// 스터디 수정
async function modifyStudy(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .put(`/study/${params.study_ID}`, JSON.stringify(params.info))
    .then(success)
    .catch(fail);
}

// 스터디 삭제
async function deleteStudy(study_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.delete(`/study/${study_ID}`).then(success).catch(fail);
}

// 스터디 참가 신청하기
async function applyStudy(study_id, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.post(`/study/${study_id}/member`).then(success).catch(fail);
}
// 스터디 참가 신청 취소하기
async function cancleStudy(study_id, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.delete(`/study/${study_id}/member`).then(success).catch(fail);
}

// 스터디 참가자 수 조회
async function getCurrent(study_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/${study_ID}/current-people`).then(success).catch(fail);
}

// 내가 신청한 스터디 조회
async function getMyStudy(success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/users/mystudy/upcoming`).then(success).catch(fail);
}

// 댓글 CRUD
async function createComment(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .post(`/study/${params.study_ID}/comment`, JSON.stringify(params.info))
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
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/study/${study_ID}/comment`).then(success).catch(fail);
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
  applyStudy,
  cancleStudy,
  getCurrent,
  getMyStudy,
};
