import http from "./http.js";

const api = http;

//면접 순서 가져옴
async function getInterviewOrder(params, success, fail) {
  // console.log("악시오스 보내기 전 스터디 아이디 >> " + params.study_ID);
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.post(`/meeting/${params.study_ID}/order`, params.data).then(success).catch(fail);
}

//채점표 생성
async function postScorecards(study_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.post(`/meeting/${study_ID}/score`).then(success).catch(fail);
}

//채점표 가져오기
async function getAllScorecards(study_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`/meeting/${study_ID}/score`).then(success).catch(fail);
}
//채점표 갱신하기
async function putScore(params, success, fail) {
  await api.put(`/meeting/${params.study_ID}/score`, params.data).then(success).catch(fail);
}
//자기소개서 가져오기
async function getAllResume(study_ID, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.get(`meeting/${study_ID}/resume`).then(success).catch(fail);
}

export { getInterviewOrder, postScorecards, getAllScorecards, putScore, getAllResume };
