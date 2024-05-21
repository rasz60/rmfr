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
    return {
      login: false,
      info: {
        mId: "",
        mUd: "",
        mThum: "",
        tmpImg: "",
        mPwUpdateDate: "",
        mLevel: "",
      },
    };
  },
  mounted() {
    this.fn_loginChk();
  },
  methods: {
    async fn_loginChk() {
      // 로그인 실패 시, loginError=true로 return
      var url = location.href;
      var query = url.substring(url.indexOf("?"));
      var param = new URLSearchParams(query);

      if (param.size > 0 && param.has("error")) {
        alert("로그인 정보를 다시 확인해주세요.");
        this.fn_toggleInfo();
        document.querySelector("input#mPw").focus();
      } else {
        await this.axios.get("/rest/v1/loginchk").then((res) => {
          var jsonData = res.data.info;
          if (jsonData != "" && jsonData != null) {
            // 결과
            var mId = jsonData.mid;
            var thum = jsonData.thum;
            var mPwUpdateDate = jsonData.mpwUpdateDate;
            var mLevel = jsonData.mlevel;

            // data setting
            this.info.mId = mId;
            this.info.mLevel = mLevel;
            this.info.mPwUpdateDate = mPwUpdateDate;

            if (thum == "" || thum == null) {
              this.info.tmpImg = this.info.mId.substring(0, 1);

              const rColor = Math.floor(Math.random() * 128 + 64);
              const gColor = Math.floor(Math.random() * 128 + 64);
              const bColor = Math.floor(Math.random() * 128 + 64);

              document.querySelector("a#tmpImg").style.backgroundColor =
                "rgb(" + rColor + "," + gColor + "," + bColor + ")";
            } else {
              this.info.mThum = thum;
            }
            var updateDate = new Date(mPwUpdateDate);
            updateDate.setDate(updateDate.getDate() + 89);
            var diff = Math.abs(updateDate - new Date());
            var diffDate = Math.ceil(diff / (1000 * 60 * 60 * 24));

            this.info.mUd = diffDate;

            this.login = true;
          }
        });
      }
    },
    fn_infoMenu(url) {
      location.href = url;
    },
    fn_focusSearch() {
      document.querySelector("div#preIcon").style.visibility = "visible";
    },
    fn_blurSearch() {
      document.querySelector("div#preIcon").style.visibility = "hidden";
    },

    fn_toggleMenu() {
      var slim = document.querySelector("div#menuSlim");
      var full = document.querySelector("div#menuFull");
      var contents = document.querySelector("div#content");

      if (slim.style.display == "none" || slim.style.display == "") {
        full.style.display = "none";
        slim.style.display = "block";
        contents.style.width = "96%";
        console.log("slim none or null");
      } else {
        slim.style.display = "none";
        full.style.display = "block";
        contents.style.width = "85%";
        console.log("full none or null");
      }
    },

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

    fn_submitFrm() {
      var username = document.querySelector("input#mId").value;
      var password = document.querySelector("input#mPw").value;

      if (username != "" && password != "") {
        document.querySelector("form#login").submit();
      } else {
        alert("아이디와 비밀번호를 모두 입력해주세요.");
        return false;
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
section#header {
  height: 80px;
  display: flex;
  border-bottom: 1px solid #ececec;

  a {
    text-decoration: none;
    cursor: pointer;
  }

  div#menuToggle {
    width: 3%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 22px;
    span.toggleBtn {
      cursor: pointer;
    }
  }

  div#logo {
    width: 15%;
    height: 100%;

    display: flex;
    justify-content: center;
    align-items: center;

    a {
      color: white;
      background-color: red;
      padding: 15px;
      border-radius: 20px 20px 20px 0;
    }
  }

  div#searchBox {
    width: 65%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    div#searchIptBox {
      width: 70%;
      height: 60%;
      display: flex;
      justify-content: center;
      align-items: center;
      border: 1px solid #ececec;
      border-radius: 50px;
      overflow: hidden;

      input#searchIpt {
        width: 85%;
        height: 100%;
        border: none;
        font-size: 1.3em;
      }

      div {
        text-align: center;
        font-size: 1.2em;
      }

      div#preIcon {
        width: 8%;
        visibility: hidden;
        color: lightgray;
      }
      div#suffixIcon {
        width: 8%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.2em;
        color: darkgrey;
        background-color: lightgray;
        cursor: pointer;
      }

      div#suffixIcon:hover {
        background-color: darkgray;
        color: white;
      }
    }
  }

  div#buttonBox {
    width: 15%;
    height: 100%;
    display: flex;
    justify-content: end;
    align-items: center;
    font-size: 20px;

    a.headerBtn {
      padding: 15px;
      cursor: pointer;
      color: black;
    }

    div#loginInfo {
      display: none;
      position: absolute;
      top: 75px;
      right: 10px;
      width: 350px;
      padding: 10px;
      background-color: white;
      font-size: 14px;
      border-radius: 8px;

      form {
        width: 100%;
      }

      box-shadow: 2px 2px 2px 2px lightgray;
      div {
        padding: 8px;
      }

      input {
        border: none;
        border-bottom: 1px solid darkgray;
      }

      div#info {
        width: 100%;

        div#basicInfo {
          border-bottom: 1px solid #ececec;

          div#thumImg {
            width: 60px;
            height: 60px;
            border: 1px solid #ececec;
            padding: 2px;
            border-radius: 50px;

            a {
              display: block;
              width: 100%;
              height: 100%;
              border: none;
              border-radius: 50px;
              color: white;
            }
          }

          div#thumImg:hover {
            border: 2px solid #656f9e;
          }

          div#basic {
            padding-left: 15px;
            text-align: left;

            a {
              display: block;
              text-decoration: none;
              padding: 3px;
            }

            a#mId {
              color: black;
              font-size: 1.3em;
              font-weight: 400;
            }

            a#mId:hover {
              color: darkblue;
              text-decoration: underline;
            }

            a#mUd {
              font-size: 1em;
              font-style: italic;
              color: gray;
            }
          }
        }

        div.infoMenu {
          display: flex;
          align-items: center;
          border-bottom: 1px solid #ececec;
          cursor: pointer;
        }

        div.infoMenu:hover {
          background-color: #ececec;
        }
      }
    }
  }
}
</style>
