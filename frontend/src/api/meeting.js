import http from "./http.js";

const api = http;

//면접 순서 가져옴
async function getInterviewOrder(study_ID, cnt, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("accessToken");
  await api
    .post(`/meeting/${study_ID}/order`, { interviewee: cnt })
    .then(success)
    .catch(fail);
}

export { getInterviewOrder };
