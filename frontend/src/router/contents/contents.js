import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/components/contents/main/MainView.vue";
import SignUpView from "@/components/contents/signup/SignUpView.vue";
import SettingView from "@/components/contents/setting/SettingView.vue";
import NoticeBoardList from "@/components/contents/board/notice/BoardList.vue";

const router = createRouter({
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
      path: "/board/notice",
      component: NoticeBoardList,
    },
  ],
});

export default router;
