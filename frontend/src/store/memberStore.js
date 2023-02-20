import jwtDecode from "jwt-decode";
import router from "@/router";

import {
  signin,
  findByEmail,
  getCode,
  tokenRegeneration,
  signout,
  update,
  signup,
  deleteUser,
} from "@/api/users";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    data: null,
    code: null,
    isValidToken: false,
    isValidEmail: false,
    isValidName: false,
  },
  getters: {
    checkIsLogin: function (state) {
      return state.isLogin;
    },
    checkUserInfo: function (state) {
      return state.data;
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
    SET_USER_INFO: (state, data) => {
      state.isLogin = true;
      state.data = data;
    },
    CLEAR_USER_INFO: (state) => {
      state.isLogin = false;
      state.isLoginError = false;
      state.data = null;
      state.isValidToken = false;
    },
    SET_EMAIL_CODE: (state, data) => {
      state.code = data;
    },
    SET_IS_VALID_EMAIL(state, flag) {
      state.isValidEmail = flag;
    },
    SET_IS_VALID_NAME(state, flag) {
      state.isValidName = flag;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await signin(
        user,
        ({ data }) => {
          // console.log(data);
          if (data.massage === "SUCCESS") {
            let ACCESS = data.access;
            let REFRESH = data.refresh;
            // console.log(ACCESS + " 그리고 " + REFRESH);
            // console.log(JSON.stringify(ACCESS));
            // console.log("login success token created!!!! >> ", accessToken, refreshToken);
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("ACCESS", ACCESS);
            sessionStorage.setItem("REFRESH", REFRESH);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        () => {
          // console.log(error);
        }
      );
    },

    async socialConfirm({ commit }, tokens) {
      // console.log("socialconfirm이야");
      // console.log("토큰들은??!!", tokens);

      let ACCESS = tokens[0];
      let REFRESH = tokens[1];

      // console.log("액세스는????", tokens[0]);
      commit("SET_IS_LOGIN", true);
      commit("SET_IS_LOGIN_ERROR", false);
      commit("SET_IS_VALID_TOKEN", true);
      sessionStorage.setItem("ACCESS", ACCESS);
      sessionStorage.setItem("REFRESH", REFRESH);
    },

    async checkEmail({ dispatch }, user) {
      // console.log(user.email);
      await findByEmail(
        user.email,
        ({ data }) => {
          if (data.message === "SUCCESS") {
            // console.log("회원가입 가능함! :  ");
            alert(
              "사용 가능한 이메일 입니다.\n작성하신 이메일로 인증번호를 전송했습니다."
            );
            dispatch("getEmailCode", user.email);
          }
        },
        (error) => {
          if (error.response.status == 409) {
            alert("이메일이 중복됐습니다");
          }
        }
      );
    },

    async getEmailCode({ commit }, email) {
      // console.log("실행되니..?!!!!");
      await getCode(email, ({ data }) => {
        if (data.message === "SUCCESS") {
          // console.log("코드 보내따!!!!");
          commit("SET_EMAIL_CODE", data.data);
          // sessionStorage.setItem("code", code);
        } else {
          console.log("유저 정보 없음!!!!");
        }
      });
    },

    // async getUserInfo({ commit, dispatch }, token) {
    //   // async getUserInfo({ commit, dispatch }, myemail) {
    //   let decodeToken = jwtDecode(token);
    //   // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
    //   await findByEmail(
    //     decodeToken.Id,
    //     ({ data }) => {
    //       if (data.message === "SUCCESS") {
    //         console.log("findById 안으로 들어왓다. ");
    //         // 백엔드에서 받아오는 userInfo가 없음
    //         // commit("SET_USER_INFO", this.state.data);
    //         // console.log("유저정보 바다와서 state에 올렸따 :  "  + data.userinfo);
    //         // console.log("3. getUserInfo data >> ", data);
    //       } else {
    //         console.log("유저 정보 없음!!!!");
    //       }
    //     },
    //     async (error) => {
    //       console.log(
    //         "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
    //         error.response.status
    //       );
    //       commit("SET_IS_VALID_TOKEN", false);
    //       await dispatch("tokenRegeneration");
    //     }
    //   );
    // },
    async tokenRegeneration({ commit, state }) {
      console.log(
        "토큰 재발급 >> 기존 토큰 정보 : {}",
        sessionStorage.getItem("accessToken")
      );
      await tokenRegeneration(
        JSON.stringify(state.data),
        ({ data }) => {
          if (data.message === "SUCCESS") {
            let accessToken = data["ACCESS"];
            // console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("ACCESS", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            // console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await signout(
              state.data.email,
              ({ data }) => {
                if (data.message === "SUCCESS") {
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
              () => {
                // console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          }
        }
      );
    },
    // async userLogout({ commit }, email) {
    async userLogout({ commit }, token) {
      // 엥 백엔드로직은 toekn 넘겨주는듯
      let decodeToken = jwtDecode(token);

      //오브젝트 생성
      let obj = { id: "", accessToken: "" };
      obj.id = decodeToken.Id;
      obj.accessToken = token;

      // console.log("토큰해독 : " + decodeToken);
      // console.log("생성한 오브젝트" + obj);
      await signout(
        obj,
        // email,
        // token,
        ({ data }) => {
          // console.log("로그아웃 백엔드에서 날라오는" + data);
          if (data.message === "SUCCESS") {
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
          } else {
            // console.log("유저 정보 없음!!!!");
          }
        },
        () => {
          // console.log(error);
        }
      );
    },

    async userJoin({ dispatch }, user) {
      // console.log("memberstor : " + JSON.stringify(user));
      await signup(
        user,
        ({ data }) => {
          // console.log(data);
          dispatch("getEmailCode", user.email);
          if (data.message === "SUCCESS") {
            console.log("회원가입 성공");
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        () => {
          // console.log(error);
        }
      );
    },
    async userUpdate({ commit }, user) {
      let token = sessionStorage.getItem("ACCESS");
      console.log(token);
      // console.log("스토어임");
      await update(user, token, ({ data }) => {
        console.log(data);
        commit("SET_IS_LOGIN", this.isLogin);
        // console.log("업데이트 성공");
      }),
        (error) => {
          console.log(error);
        };
    },
    async userDelete({ commit }, user) {
      // let token = sessionStorage.getItem("ACCESS");
      await deleteUser(user.password, ({ data }) => {
        console.log(data);
        // console.log(data);
        if (data.message === "SUCCESS") {
          // console.log("유저삭제완료");
          commit("CLEAR_USER_INFO");
        }
      }),
        () => {
          // console.log(error);
        };
    },
  },
};

export default memberStore;
