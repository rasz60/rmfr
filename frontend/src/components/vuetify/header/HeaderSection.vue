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

          <v-btn class="headerBtn" v-show="!login" @click="fnFlagInit(0)">
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
        v-show="!login && !findInfo.findFlag"
        action="/member/loginProc"
        method="post"
        ref="loginForm"
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
          가입하기&nbsp;<v-icon icon="fas fa-chevron-right"></v-icon>
        </a>
        &nbsp;
        <a
          class="text-blue text-decoration-none"
          href="#"
          @click.stop="fnFindFlag('id')"
        >
          ID 찾기&nbsp;<v-icon icon="fas fa-chevron-right"></v-icon>
        </a>
        &nbsp;
        <a
          class="text-blue text-decoration-none"
          href="#"
          @click.stop="fnFindFlag('pw')"
        >
          PW 찾기&nbsp;<v-icon icon="fas fa-chevron-right"></v-icon>
        </a>
      </v-form>

      <!-- 정보 찾기 -->
      <v-form
        @submit.prevent
        id="findInfo"
        v-show="findInfo.findFlag && !findInfo.certDone"
        ref="findForm"
      >
        <v-row>
          <v-col cols="12">
            <v-text-field
              type="ID"
              label="아이디(ID)"
              variant="underlined"
              prepend-inner-icon="far fa-id-badge"
              v-show="findInfo.pwFindFlag"
              v-model="findInfo.mId"
              :rules="usernameRules"
            >
            </v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12">
            <v-text-field
              type="email"
              label="이메일(email)"
              variant="underlined"
              :rules="emailRules"
              prepend-inner-icon="fas fa-at"
              v-model="findInfo.mEmail"
            >
            </v-text-field>
          </v-col>
        </v-row>
        <v-row id="my0">
          <v-col :cols="findInfo.cert ? 9 : 12">
            <v-text-field
              type="text"
              label="인증번호(code)"
              variant="underlined"
              prepend-inner-icon="fas fa-code"
              :readonly="!findInfo.cert"
              @keyup="validCodeChk($event.target.value)"
            >
            </v-text-field>
          </v-col>
          <v-col id="timer" :cols="3" v-show="findInfo.cert">
            <span id="validCodeTimer"></span>
          </v-col>
        </v-row>
        <v-btn
          class="mb-8"
          color="blue"
          size="large"
          variant="tonal"
          block
          @click="validateFindForm"
          :text="findInfo.cert ? '인증번호재발송' : '인증번호발송'"
        >
        </v-btn>

        <a class="text-blue text-decoration-none" @click="fnFlagInit">
          로그인 창으로 이동&nbsp;<v-icon icon="fas fa-chevron-right"></v-icon>
        </a>
      </v-form>

      <v-form
        ref="nPwForm"
        @submit.prevent
        id="nPwInfo"
        method="post"
        v-show="findInfo.certDone"
      >
        <v-select
          :items="this.findInfo.mIdList"
          variant="underlined"
          label="가입된 아이디(ID)"
          v-show="!findInfo.pwFindFlag"
        ></v-select>
        <v-text-field
          type="password"
          label="새 비밀번호(New Password)"
          variant="underlined"
          :rules="pwRules"
          prepend-inner-icon="fas fa-key"
          v-model="findInfo.nPw"
          v-show="findInfo.pwFindFlag"
        >
        </v-text-field>
        <v-text-field
          type="password"
          label="새 비밀번호 확인(New Password Check)"
          variant="underlined"
          :rules="pwChkRules"
          prepend-inner-icon="fas fa-key"
          v-model="findInfo.nPwChkVal"
          v-show="findInfo.pwFindFlag"
        >
        </v-text-field>
        <v-btn
          class="mb-8"
          color="blue"
          size="large"
          variant="tonal"
          block
          @click="validateNPwForm"
          v-show="findInfo.pwFindFlag"
          text="비밀번호변경"
        >
        </v-btn>

        <a class="text-blue text-decoration-none" @click="fnFlagInit">
          로그인 창으로 이동&nbsp;<v-icon icon="fas fa-chevron-right"></v-icon>
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
  </v-layout>
</template>

<script>
export default {
  data: () => headerDatas,
  computed: {
    usernameRules() {
      var rules = [];

      const nullchk = (v) => {
        if (!this.findInfo.pwFindFlag) return true;
        if (v) return true;
        return "아이디는 필수 입력사항입니다.";
      };
      rules.push(nullchk);

      const regchk = (v) => {
        var regExp = /^(?=.*[a-z0-9])[a-z0-9_-]{6,20}$/;
        var chk = regExp.test(v);

        if (!this.findInfo.pwFindFlag) return true;
        if (chk) return true;
        return "6~20자리의 영문소문자, 숫자, -, _ 조합으로 입력해주세요.";
      };
      rules.push(regchk);

      return rules;
    },
    emailRules() {
      const rules = [];

      const nullchk = (v) => {
        if (v) return true;
        this.findInfo.chkd = false;
        return "인증번호를 수신할 이메일 주소를 입력해주세요.";
      };
      rules.push(nullchk);

      const regchk = (v) => {
        var regExp =
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        var chk = regExp.test(v);

        this.findInfo.chkd = chk;

        if (chk) return true;
        return "형식에 맞는 이메일 주소를 입력해주세요. (ex> emailId@domain.com)";
      };
      rules.push(regchk);

      return rules;
    },
    pwRules() {
      const rules = [];
      const nullchk = (v) => {
        if (v) return true;
        return "비밀번호는 필수 입력사항입니다.";
      };
      rules.push(nullchk);

      const regchk = (v) => {
        var regExp =
          /(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
        var chk = regExp.test(v);
        this.findInfo.npwRegChk = chk;
        if (chk) return true;
        return "8~16자리의 영문 소/대문자, 숫자, 특수문자($,`,~,!,@,$,!,%,*,#,^,?,&,,(,),-,_,=,+) 조합으로 입력해주세요.";
      };
      rules.push(regchk);

      return rules;
    },
    pwChkRules() {
      const rules = [];

      const nullchk = (v) => {
        if (v) return true;
        return "비빌번호는 필수 입력사항입니다.";
      };
      rules.push(nullchk);

      if (this.findInfo.nPwChkVal) {
        const chkval = (v) => {
          var chk = this.findInfo.nPw == v;
          this.findInfo.nPwChk = chk;
          if (chk) return true;
          return "비밀번호가 일치하지 않습니다.";
        };
        rules.push(chkval);
      }
      return rules;
    },
  },
  methods: headerMethods,
  mounted() {
    console.log()
    this.fn_loginChk();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@v-css/header/header.css";
</style>
