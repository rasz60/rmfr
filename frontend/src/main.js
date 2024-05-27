import { createApp } from "vue";
import App from "@/App.vue";

//router
// b-router : bootstrap, v-router : vuetify
//import router from "@b-router/contents/contents.js";
import router from "@v-router/contents/contents.js";

// axios
import axios from "axios";

// font-awesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { fab } from "@fortawesome/free-brands-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons";

// bootstrap
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

// Vuetify
import vuetify from "./plugins/vuetify";

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
app
  .use(router) //router 별 view 파일 연결
  .use(vuetify) //vuetify
  .component("font-awesome-icon", FontAwesomeIcon) // font-awesome
  .mount(`#app`); // id app인 엘리먼트에 mount
