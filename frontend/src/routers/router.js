import { createRouter, createWebHistory } from "vue-router";
import ContentList from "@/components/views/ContentList.vue";
import SignIn from "@/components/views/SignInComponent.vue";
import SignUp from "@/components/views/SignUpComponent.vue";

// 어떤 컴포넌트에 연결할것인지 여기에 정의한다.
const routes = [
    {
         path: "/",
         name: "main",
         component: ContentList
    },
    {
        path: "/member/signin",
        name: "SignIn",
        component: SignIn
    },
    {
        path: "/member/signup",
        name: "SignUp",
        component: SignUp
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;