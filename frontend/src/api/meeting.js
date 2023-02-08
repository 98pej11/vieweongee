import http from "./http.js";

const api = http;

//면접 순서 가져옴
async function getInterviewOrder(params, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api.post(`/meeting/${params.study_ID}/order`, params.data).then(success).catch(fail);
}

export { getInterviewOrder };
