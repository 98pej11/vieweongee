import { createStore } from "vuex";
// import createPersistedState from "vuex-persistedstate";
import memberStore from "@/store/memberStore";
import studyStore from "@/store/studyStore";
// import commentStore from "@/store/modules/commentStore";
import meetingStore from "@/store/meetingStore";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    memberStore,
    studyStore,
    meetingStore,
  },
  plugins: [
    // createPersistedState({
    //   paths: ["memberStore"],
    // }),
  ],
});
