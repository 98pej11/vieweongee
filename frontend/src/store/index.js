import { createStore } from "vuex";
// import createPersistedState from "vuex-persistedstate";
import memberStore from "@/store/memberStore";
import studyStore from "@/store/studyStore";
// import commentStore from "@/store/modules/commentStore";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    memberStore,
    studyStore,
  },
  plugins: [
    // createPersistedState({
    //   paths: ["memberStore"],
    // }),
  ],
});
