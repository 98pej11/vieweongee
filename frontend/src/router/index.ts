import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainView from "../views/MainView.vue";
import StudyListView from "../views/StudyListView.vue";
import StudyCreateView from "../views/StudyCreateView.vue";
import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import FindpwdView from "../views/FindpwdView.vue";
import MypageView from "../views/MypageView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "main",
    component: MainView,
  },
  {
    path: "/studylist",
    name: "studylist",
    component: StudyListView,
  },
  {
    path: "/studycreate",
    name: "studycreate",
    component: StudyCreateView,
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
    path: "/mypage",
    name: "mypage",
    component: MypageView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
