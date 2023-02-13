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
  // console.log(params);
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

// 스터디 참가 여부 확인
async function getAppliyID(success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/users/mystudy`).then(success).catch(fail);
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

// 자기소개서 첨부
async function applyImage(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .post(`/study/${params.study_ID}/resume`, params.file)
    .then(success)
    .catch(fail);
}

export {
  createStudy,
  getTopStudy,
  getAllStudy,
  getStudy,
  modifyStudy,
  deleteStudy,
  getSearch,
  applyStudy,
  cancleStudy,
  getCurrent,
  applyImage,
  getAppliyID,
};
