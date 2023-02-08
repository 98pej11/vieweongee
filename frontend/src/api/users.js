import http from "./http.js";

const api = http;

async function signin(user, success, fail) {
  await api
    .post(`/users/signin`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findByEmail(email, success, fail) {
  // async function findById(myemail, success, fail) {
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  await api
    .get(`/users/email-check/` + email)
    .then(success)
    .catch(fail);
  // await api.get(`/users/email-check`).then(success).catch(fail);
}

async function getCode(email, success, fail) {
  // async function findById(myemail, success, fail) {
  await api.get(`/users/email-valid/`+ email).then(success).catch(fail);
  // await api.get(`/users/email-check`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["REFRESH"] = sessionStorage.getItem("REFRESH"); //axios header에 refreshToken 셋팅
  await api.post(`/users/check-refresh`, user).then(success).catch(fail);
}

// async function signout(userid, success, fail) {
async function signout(obj, success, fail) {
  // 로그아웃은 포스트 방식입니닷~~!~~!
  // token을 어떻게 넘겨줘야 제대로 넘어가는거지...?
  api.defaults.headers["ACCESS"] = sessionStorage.getItem("ACCESS");
  // 헤더에 넣어줌
  await api.post(`/users/signout`, obj).then(success).catch(fail);
}

async function update(user, success, fail) {
  console.log("users : " + JSON.stringify(user))
  await api.put(`/users/`, JSON.stringify(user)).then(success).catch(fail);
}

async function signup(user, success, fail) {
  console.log("users : " + JSON.stringify(user))
  await api
    .post(`/users/signup`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

// async function preDeleteUser(userid, success, fail){
//   await api.delete(`/board/byuser/${userid}`).then(success).catch(fail);
// }

async function deleteUser(password, success, fail) {
  console.log("비번 : " + password);
  await api.delete(`/users/` + password).then(success).catch(fail);
}

export {
  signin,
  findByEmail,
  getCode,
  tokenRegeneration,
  signout,
  update,
  signup,
  deleteUser,
};
