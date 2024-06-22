import { createRouter, createWebHistory } from "vue-router";
import MainView from "@v-components/contents/main/MainView.vue";
import SignUpView from "@v-components/contents/signup/SignUpView.vue";
import SettingView from "@v-components/contents/setting/SettingView.vue";

import NoticeBoardView from "@v-components/contents/board/notice/BoardView.vue";
import boardViewChild from "./board/board.js";

import AdminView from "@v-components/contents/admin/AdminView.vue";

const contentRouter = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: MainView,
    },
    {
      path: "/member/signup",
      component: SignUpView,
    },
    {
      path: "/member/settings",
      component: SettingView,
    },
    {
      path: "/board",
      component: NoticeBoardView,
      children: boardViewChild,
    },
    {
      path: "/admin/setting",
      component: AdminView,
    },
  ],
});

export default contentRouter;
