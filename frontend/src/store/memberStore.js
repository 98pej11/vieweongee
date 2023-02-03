import jwtDecode from "jwt-decode";
import router from "@/router";

import { signin, findById, tokenRegeneration, logout, update, join, deleteUser, preDeleteUser } from "@/api/users";
// import http from "@/api/http";

const memberStore = {
    namespaced: true,
    state: {
      isLogin: false,
      isLoginError: false,
      userInfo: null,
      isValidToken: false,
    },
    getters: {
      checkUserInfo: function (state) {
        return state.userInfo;
      },
      checkToken: function (state) {
        return state.isValidToken;
      },
    },
    mutations: {
      SET_IS_LOGIN: (state, isLogin) => {
        state.isLogin = isLogin;
      },
      SET_IS_LOGIN_ERROR: (state, isLoginError) => {
        state.isLoginError = isLoginError;
      },
      SET_IS_VALID_TOKEN: (state, isValidToken) => {
        state.isValidToken = isValidToken;
      },
      SET_USER_INFO: (state, userInfo) => {
        state.isLogin = true;
        state.userInfo = userInfo;
      },
  
      CLEAR_USER_INFO: (state) =>{
        state.isLogin= false;
        state.isLoginError= false;
        state.userInfo= null;
        state.isValidToken= false;
      },
    },
    actions: {
      async userConfirm({ commit }, user) {
        await signin(
          user,
          ({ data }) => {
            if (data.message === "success") {
              let accessToken = data["accessToken"];
              let refreshToken = data["refreshToken"];
              // console.log("login success token created!!!! >> ", accessToken, refreshToken);
              commit("SET_IS_LOGIN", true);
              commit("SET_IS_LOGIN_ERROR", false);
              commit("SET_IS_VALID_TOKEN", true);
              sessionStorage.setItem("accessToken", accessToken);
              sessionStorage.setItem("refreshToken", refreshToken);
            } else {
              commit("SET_IS_LOGIN", false);
              commit("SET_IS_LOGIN_ERROR", true);
              commit("SET_IS_VALID_TOKEN", false);
            }
          },
          (error) => {
            console.log(error);
          }
        );
      },
      async getUserInfo({ commit, dispatch }, token) {
        let decodeToken = jwtDecode(token);
        // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
        await findById(
          decodeToken.email,
          ({ data }) => {
            if (data.message === "success") {
              commit("SET_USER_INFO", data.userInfo);
              // console.log("3. getUserInfo data >> ", data);
            } else {
              console.log("유저 정보 없음!!!!");
            }
          },
          async (error) => {
            console.log("getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ", error.response.status);
            commit("SET_IS_VALID_TOKEN", false);
            await dispatch("tokenRegeneration");
          }
        );
      },
      async tokenRegeneration({ commit, state }) {
        console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("accessToken"));
        await tokenRegeneration(
          JSON.stringify(state.userInfo),
          ({ data }) => {
            if (data.message === "success") {
              let accessToken = data["accessToken"];
              console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
              sessionStorage.setItem("accessToken", accessToken);
              commit("SET_IS_VALID_TOKEN", true);
            }
          },
          async (error) => {
            // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
            if (error.response.status === 401) {
              console.log("갱신 실패");
              // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
              await logout(
                state.userInfo.userid,
                ({ data }) => {
                  if (data.message === "success") {
                    console.log("리프레시 토큰 제거 성공");
                  } else {
                    console.log("리프레시 토큰 제거 실패");
                  }
                  alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                  commit("SET_IS_LOGIN", false);
                  commit("SET_USER_INFO", null);
                  commit("SET_IS_VALID_TOKEN", false);
                  router.push({ name: "login" });
                },
                (error) => {
                  console.log(error);
                  commit("SET_IS_LOGIN", false);
                  commit("SET_USER_INFO", null);
                }
              );
            }
          }
        );
      },
      async userLogout({ commit }, userid) {
        await logout(
          userid,
          ({ data }) => {
            if (data.message === "success") {
              commit("SET_IS_LOGIN", false);
              commit("SET_USER_INFO", null);
              commit("SET_IS_VALID_TOKEN", false);
            } else {
              console.log("유저 정보 없음!!!!");
            }
          },
          (error) => {
            console.log(error);
          }
        );
      },
  
      async userUpdate(user){
        await update(user,
          ()=>{
            console.log("업데이트 성공");
          }),
          (error)=>{
            console.log(error);
          }
      },
  
      async userJoin(user){
        await join(user,
          ()=>{
            console.log("회원가입 성공");
          }),
          (error)=>{
            console.log(error);
          }
      },
  
      async userDelete({commit}, userid){
  
        await preDeleteUser(userid,
          ()=>{
            console.log("사전삭제 완료");
          }),
          (error)=>{
            console.log(error);
          }
  
        await deleteUser(userid,
          ()=>{
            console.log("유저삭제완료");
            commit("CLEAR_USER_INFO");
          }),
          (error)=>{
            console.log(error);
          }
      }
    },
  };
  
  export default memberStore;