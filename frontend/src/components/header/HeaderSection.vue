<script setup>
import headerDatas from "@/assets/js/header/data.js";
import headerMethods from "@/assets/js/header/methods.js";
</script>

<template>
  <section id="header">
    <div id="menuToggle">
      <span class="toggleBtn" @click="fn_toggleMenu">
        <font-awesome-icon :icon="['fas', 'bars']" />
      </span>
    </div>
    <div id="logo">
      <a href="/" title="home">
        <font-awesome-icon :icon="['fas', 'r']" />&nbsp;
        <font-awesome-icon :icon="['fas', 'm']" />&nbsp;
        <font-awesome-icon :icon="['fas', 'f']" />&nbsp;
        <font-awesome-icon :icon="['fas', 'r']" />&nbsp;
        <font-awesome-icon :icon="['fas', 'question']" />
      </a>
    </div>
    <div id="searchBox">
      <div id="searchIptBox">
        <div id="preIcon">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" />
        </div>
        <input
          id="searchIpt"
          placeholder="검색"
          @focus="fn_focusSearch"
          @blur="fn_blurSearch"
        />
        <div id="suffixIcon">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" />
        </div>
      </div>
    </div>
    <div id="buttonBox">
      <div class="guest" v-show="!this.login">
        <a class="headerBtn" href="/member/signup" title="signup">
          <font-awesome-icon :icon="['fas', 'user-plus']" />
        </a>
        <a class="headerBtn" href="#" title="login" @click="fn_toggleInfo">
          <font-awesome-icon :icon="['fas', 'right-to-bracket']" />
        </a>
      </div>
      <div class="user" v-show="this.login">
        <a class="headerBtn" href="#" title="push">
          <font-awesome-icon :icon="['fas', 'bell']" />
        </a>
        <a class="headerBtn" href="#" title="mypage" @click="fn_toggleInfo">
          <font-awesome-icon :icon="['fas', 'user']" />
        </a>
      </div>
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
                {{ info.tmpImg }}
              </a>
            </div>
            <div id="basic" class="col-9">
              <a id="mId" class="display-6" href="/member/settings">
                @{{ info.mId }}
              </a>

              <a id="mUd" class="display-6" href="/member/settings">
                패스워드 변경까지 D-{{ info.mUd }}
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
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return headerDatas;
  },
  methods: headerMethods,
  mounted() {
    this.fn_loginChk();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@/assets/css/header/header.css";
</style>
