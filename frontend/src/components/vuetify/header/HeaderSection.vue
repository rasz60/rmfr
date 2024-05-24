<script setup>
//import headerDatas from "@v-js/header/data.js";
import headerMethods from "@v-js/header/methods.js";
</script>

<template>
  <v-layout id="header">
    <v-app-bar id="headerMenu">
      <template v-slot:prepend>
        <v-app-bar-nav-icon class="toggleBtn"></v-app-bar-nav-icon>
        <v-app-bar-title id="logo">
          <a href="/">
            <v-icon icon="fas fa-r" class="logo-icons" />
            <v-icon icon="fas fa-m" class="logo-icons" />
            <v-icon icon="fas fa-f" class="logo-icons" />
            <v-icon icon="fas fa-r" class="logo-icons" />
            <v-icon icon="fas fa-question" class="logo-icons" />
          </a>
        </v-app-bar-title>
      </template>

      <div id="searchBox">
        <v-responsive class="mx-auto">
          <v-text-field
            prepend-inner-icon="fas fa-magnifying-glass"
            variant="solo"
            placeholder="전체 검색"
          >
          </v-text-field>
        </v-responsive>
        <v-btn id="searchBtn">
          <v-icon icon="fas fa-magnifying-glass"></v-icon>
        </v-btn>
      </div>

      <template v-slot:append>
        <v-action-items id="buttonBox">
          <v-tooltip
            location="bottom center"
            :origin="origin"
            no-click-animation
          >
            <template v-slot:activator="{ props }">
              <v-btn
                class="headerBtn"
                href="/member/signup"
                icon="fas fa-user-plus"
                v-bind="props"
              ></v-btn>
            </template>
            <div>Sign Up</div>
          </v-tooltip>

          <v-tooltip
            location="bottom center"
            :origin="origin"
            no-click-animation
          >
            <template v-slot:activator="{ props }">
              <v-btn
                class="headerBtn"
                v-bind="props"
                icon="fas fa-right-to-bracket"
                @click="fn_toggleInfo"
                @click.stop="drawer = !drawer"
              ></v-btn>
            </template>
            <div>Login</div>
          </v-tooltip>
        </v-action-items>
      </template>
    </v-app-bar>

    <div id="loginInfo">
      <form
        id="login"
        action="/member/loginProc"
        method="post"
        v-show="!this.login"
      >
        <div>
          <label for="username" class="col-3">아이디</label>
          <input type="text" name="mId" id="mId" class="col-8" />
        </div>

        <div>
          <label for="mPw" class="col-3">비밀번호</label>
          <input type="password" name="mPw" id="mPw" class="col-8" />
        </div>

        <div>
          <a class="btn btn-sm btn-primary" @click="fn_submitFrm"> 로그인 </a>
        </div>
      </form>

      <div id="info" v-show="this.login">
        <div id="basicInfo" class="row">
          <div id="thumImg" class="col-3">
            <a
              id="tmpImg"
              class="display-6"
              v-show="this.tmpImg != ''"
              href="/member/settings"
            >
            </a>
          </div>
          <div id="basic" class="col-9">
            <a id="mId" class="display-6" href="/member/settings"> </a>

            <a id="mUd" class="display-6" href="/member/settings">
              패스워드 변경까지 D-
            </a>
          </div>
        </div>

        <div
          id="setting"
          class="row infoMenu"
          @click="fn_infoMenu('/member/settings')"
        >
          <div id="" class="infoTxt col-9">계정설정</div>
          <div id="" class="infoIcon col-3">
            <font-awesome-icon :icon="['fas', 'gear']" />
          </div>
        </div>

        <div id="logout" class="row infoMenu" @click="fn_infoMenu('/logout')">
          <div id="" class="infoTxt col-9">로그아웃</div>
          <div id="" class="infoIcon col-3">
            <font-awesome-icon :icon="['fas', 'right-from-bracket']" />
          </div>
        </div>
      </div>
    </div>
  </v-layout>
</template>

<script>
export default {
  data: () => ({
    login: false,
    items: [
      {
        title: "Foo",
        value: "foo",
      },
      {
        title: "Bar",
        value: "bar",
      },
      {
        title: "Fizz",
        value: "fizz",
      },
      {
        title: "Buzz",
        value: "buzz",
      },
    ],
  }),
  methods: {
    headerMethods,
    fn_toggleInfo() {
      var info = document.querySelector("div#loginInfo");
      var curr = info.style.display;
      var chk = curr == "" || curr == "none";

      if (chk) {
        info.style.display = "flex";
      } else {
        info.style.display = "none";
        document.querySelector("input#mId").value = "";
        document.querySelector("input#mPw").value = "";
      }
    },
  },
  mounted() {
    this.fn_loginChk();
    console.log(this.drawer);
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@v-css/header/header.css";
</style>
