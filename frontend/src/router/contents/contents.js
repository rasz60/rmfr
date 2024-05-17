import { createRouter, createWebHistory } from "vue-router";
import MainView from "../../components/contents/main/MainView.vue";
import SignUpView from "../../components/contents/signup/SignUpView.vue";
//import LoginView from "../components/contents/LoginView";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: MainView,
    },
    {
      path: "/signup",
      component: SignUpView,
    },
  ],
});

export default router;
