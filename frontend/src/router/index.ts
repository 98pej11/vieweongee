import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainView from "../views/MainView.vue";
import StudyView from "../views/StudyView.vue";
import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import FindpwdView from "../views/FindpwdView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "main",
    component: MainView,
  },
  {
    path: "/study",
    name: "study",
    component: StudyView,
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignupView,
  },
  {
    path: "/findpwd",
    name: "findpwd",
    component: FindpwdView,
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
