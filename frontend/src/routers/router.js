import { createRouter, createWebHistory } from "vue-router";
import headerComponent from "@/components/layout/HeaderComponent.vue";

// 어떤 컴포넌트에 연결할것인지 여기에 정의한다.
const routes = [
    {
         path: "/",
         name: "n",
         component: headerComponent
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;