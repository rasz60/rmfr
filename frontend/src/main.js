import { createApp } from 'vue'
import App from './App.vue'
import routers from "./routers/router.js"  // router import

// 부트스트렙을 쓰기 위한 기본 셋팅
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Make BootstrapVue available throughout your project
App.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
App.use(IconsPlugin)

// Import Bootstrap and BootstrapVue CSS files (order is important), 부트스트랩의 css 속성을 사용할 수 있게 해주는 라이브러리
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// application 객체 생성
const app = createApp(App);

// application에 사용 가능하도록 설정
app.use(routers)  // router 추가
app.mount(`#app`); // id app인 엘리먼트에 mount


