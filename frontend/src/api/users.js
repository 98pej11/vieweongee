import http from "./http.js";

const api = http;

async function signin(user, success, fail) {
    await api.post(`/users/signin`, JSON.stringify(user)).then(success).catch(fail);
  }

  async function findById(userid, success, fail) {
    api.defaults.headers["accessToken"] = sessionStorage.getItem("accessToken");
    await api.get(`/users/email-check`).then(success).catch(fail);
  }
  
  async function tokenRegeneration(user, success, fail) {
    api.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refreshToken 셋팅
    await api.post(`/users/check-refresh`, user).then(success).catch(fail);
  }
  
  async function logout(userid, success, fail) {
    await api.get(`/users/signout`).then(success).catch(fail);
  }
  
  async function update(user, success, fail){
    await api.put(`/users`,JSON.stringify(user)).then(success).catch(fail);
  }
  
  async function join(user, success, fail){
    await api.post(`/users/signup`,JSON.stringify(user)).then(success).catch(fail);
  }
  
  // async function preDeleteUser(userid, success, fail){
  //   await api.delete(`/board/byuser/${userid}`).then(success).catch(fail);
  // }
  
  async function deleteUser(userid, success, fail){
    await api.delete(`/users`).then(success).catch(fail);
  }
  
  export { signin, findById, tokenRegeneration, logout, update, join, deleteUser };