import { createApp } from 'vue'
import App from './App.vue'
import routers from "./routers/router.js"  // router import
import BootstrapVue3 from 'bootstrap-vue-3'

// font-awesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { fab } from "@fortawesome/free-brands-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons";

// Import Bootstrap and BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

// axios
import axios from "axios";

// fontawesome library : solid, brands, regular 타입 사용
library.add(fas, fab, far);

// application 객체 생성
const app = createApp(App);

// axios 기본 http header 값 셋팅 (get / post / put / delete)
axios.defaults.headers.get["Content-Type"] = "application/json";
axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.put["Content-Type"] = "application/json";
axios.defaults.headers.delete["Content-Type"] = "application/json";

// 전역변수 선언
import { ref } from "vue";
const menuDrawer = ref(false);
app.config.globalProperties.axios = axios;
app.config.globalProperties.$menuDrawer = menuDrawer;

// fontawesome library : solid, brands, regular 타입 사용
library.add(fas, fab, far);

// application에 사용 가능하도록 설정
app.use(routers)  // router 추가
app.use(BootstrapVue3)
app.component("font-awesome-icon", FontAwesomeIcon) // font-awesome
app.mount(`#app`); // id app인 엘리먼트에 mount


