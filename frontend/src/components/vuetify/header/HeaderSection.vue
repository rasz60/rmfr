<script setup>
import headerDatas from "@v-js/header/data.js";
import headerMethods from "@v-js/header/methods.js";
</script>

<template>
  <v-layout id="header">
    <v-app-bar id="headerMenu">
      <template v-slot:prepend>
        <v-app-bar-nav-icon
          class="toggleBtn"
          @click="fn_toggleMenu"
        ></v-app-bar-nav-icon>
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
        <div id="buttonBox">
          <v-btn class="headerBtn" href="/member/signup" v-show="!login">
            <v-icon icon="fas fa-user-plus"></v-icon>
            <v-tooltip location="bottom center" activator="parent">
              Signup
            </v-tooltip>
          </v-btn>

          <v-btn
            class="headerBtn"
            v-show="!login"
            @click.stop="loginDrawer = !loginDrawer"
          >
            <v-icon icon="fas fa-right-to-bracket"></v-icon>
            <v-tooltip location="bottom center" activator="parent">
              Login
            </v-tooltip>
          </v-btn>

          <v-btn class="headerBtn" v-show="login">
            <v-icon icon="fas fa-bell"></v-icon>
            <v-tooltip location="bottom" activator="parent">Alarms</v-tooltip>
          </v-btn>

          <v-btn
            class="headerBtn"
            v-show="login"
            @click.stop="loginDrawer = !loginDrawer"
          >
            <v-icon icon="fas fa-user-circle"></v-icon>
            <v-tooltip location="bottom" activator="parent">Info</v-tooltip>
          </v-btn>
        </div>
      </template>
    </v-app-bar>

    <v-card class="mx-auto" id="loginInfo" v-show="loginDrawer">
      <v-form
        @submit.prevent
        id="login"
        v-show="!login"
        action="/member/loginProc"
        method="post"
      >
        <v-text-field
          label="아이디(ID)"
          variant="underlined"
          prepend-inner-icon="far fa-id-badge"
          name="mId"
          v-model="info.mId"
        >
        </v-text-field>
        <v-text-field
          label="비밀번호(Password)"
          variant="underlined"
          prepend-inner-icon="fas fa-key"
          :append-inner-icon="pwVisible ? 'fas fa-eye' : 'fas fa-eye-slash'"
          :type="pwVisible ? 'text' : 'password'"
          @click:append-inner="pwVisible = !pwVisible"
          name="mPw"
          v-model="info.mPw"
        >
        </v-text-field>
        <v-btn
          class="mb-8"
          color="blue"
          size="large"
          variant="tonal"
          block
          @click="fn_submitFrm"
        >
          로그인
        </v-btn>

        <a
          class="text-blue text-decoration-none"
          href="/member/signup"
          target="_self"
        >
          rmfr 가입하기&nbsp;<v-icon icon="fas fa-chevron-right"></v-icon>
        </a>
      </v-form>

      <div id="info" v-show="login">
        <v-row id="basicInfo">
          <v-col id="thumImg" cols="3">
            <a
              id="tmpImg"
              class="display-6"
              v-show="info.tmpImg != ''"
              href="/member/settings"
            >
              {{ info.tmpImg }}
            </a>
          </v-col>
          <v-col cols="9" id="basic">
            <a id="mId" class="display-6" href="/member/settings">
              @{{ info.mId }}
            </a>
            <a id="mUd" class="display-6" href="/member/settings">
              패스워드 변경까지 D-{{ info.mUd }}
            </a>
          </v-col>
        </v-row>
        <v-row
          id="setting"
          class="infoMenu"
          @click="fn_infoMenu('/member/settings')"
        >
          <v-col cols="9" class="infoTxt">계정설정</v-col>
          <v-col cols="3" class="infoIcon">
            <v-icon icon="fas fa-gear"></v-icon>
          </v-col>
        </v-row>
        <v-row id="logout" class="infoMenu" @click="fn_infoMenu('/logout')">
          <v-col cols="9" class="infoTxt">로그아웃</v-col>
          <v-col cols="3" class="infoIcon">
            <v-icon icon="fas fa-right-from-bracket"></v-icon>
          </v-col>
        </v-row>
      </div>
    </v-card>
    <!--
    <div id="loginInfo">
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
  -->
  </v-layout>
</template>

<script>
export default {
  data: () => headerDatas,
  methods: headerMethods,
  mounted() {
    this.fn_loginChk();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@v-css/header/header.css";
</style>
