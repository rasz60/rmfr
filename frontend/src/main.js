import { createApp } from "vue";
import App from "@/App.vue";

//router
import router from "@/router/contents/contents.js";

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

// application 객체 생성
const app = createApp(App);

// axios 기본 http header 값 셋팅 (get / post / put / delete)
axios.defaults.headers.get["Content-Type"] = "application/json";
axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.put["Content-Type"] = "application/json";
axios.defaults.headers.delete["Content-Type"] = "application/json";

app.config.globalProperties.axios = axios;

// fontawesome library : solid, brands, regular 타입 사용
library.add(fas, fab, far);

// application에 사용 가능하도록 설정
app
  .use(router) //router 별 view 파일 연결
  .component("font-awesome-icon", FontAwesomeIcon) // font-awesome
  .mount(`#app`); // id app인 엘리먼트에 mount
