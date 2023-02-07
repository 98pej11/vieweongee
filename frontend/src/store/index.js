import { createStore } from "vuex";
// import createPersistedState from "vuex-persistedstate";
import memberStore from "@/store/memberStore";
// import commentStore from "@/store/modules/commentStore";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    memberStore,
  },
  plugins: [
    // createPersistedState({
    //   paths: ["memberStore"],
    // }),
  ],
});
