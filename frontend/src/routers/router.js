import { createRouter, createWebHistory } from "vue-router";
import headerComponent from "@/components/HeaderComponent.vue";
import TestComponent from "@/components/TestComponent.vue";
// import HomeView from "@/views/HomeView.vue";
// import AboutView from "@/views/AboutView.vue";

// 어떤 컴포넌트에 연결할것인지 여기에 정의한다.
const routes = [
    {
         path: "/",
         name: "headerComponent",
         component: headerComponent
    },
    {
        path: "/test",  // TestComponent.vue로 이동할 Path
        name: "TestComponent",  // router name
        component: TestComponent,  // Path로 이동될 Component
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;