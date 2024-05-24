import { createRouter, createWebHistory } from "vue-router";
import MainView from "@b-components/contents/main/MainView.vue";
import SignUpView from "@b-components/contents/signup/SignUpView.vue";
import SettingView from "@b-components/contents/setting/SettingView.vue";

import NoticeBoardView from "@b-components/contents/board/notice/BoardView.vue";
import boardViewChild from "./board/board.js";

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
  ],
});

export default contentRouter;
