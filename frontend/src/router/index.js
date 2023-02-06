import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import StudyListView from "../views/StudyListView.vue";
import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import FindpwdView from "../views/FindpwdView.vue";
import MypageView from "../views/MypageView.vue";

const routes = [
  {
    path: "/",
    name: "main",
    component: MainView,
  },
  {
    path: "/studylist",
    name: "studylist",
    component: StudyListView,
    redirect: "/studylist/list",
    children: [
      {
        path: "list",
        name: "list",
        component: () => import("@/components/study/StudyListCard.vue"),
      },
      {
        path: "create",
        name: "studycreate",
        component: () => import("@/components/study/StudyCreate.vue"),
      },
      {
        path: "view/:studyid",
        name: "studyview",
        component: () => import("@/components/study/StudyDetail.vue"),
      },
      {
        path: "modify/:studyid",
        name: "studymodify",
        component: () => import("@/components/study/StudyModify.vue"),
      },
    ],
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
  mode: "history",
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
