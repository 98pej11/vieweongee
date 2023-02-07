import { createStore } from 'vuex'
// import Vue from "vue"
// import Vuex from "vuex"
// import http from "@/api/http";
// import createPersistedState from "vuex-persistedstate";
import memberStore from "@/store/memberStore";
import createPersistedState from 'vuex-persistedstate';
// import commentStore from "@/store/modules/commentStore";

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    memberStore,
  },
  plugins: [createPersistedState({
    paths: ["memberStore"]
})]
})
