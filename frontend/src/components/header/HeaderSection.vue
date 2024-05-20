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
      <div class="guest" v-show="this.username == ''">
        <a class="headerBtn" href="/member/signup" title="signup">
          <font-awesome-icon :icon="['fas', 'user-plus']" />
        </a>
        <a class="headerBtn" href="#" title="login" @click="fn_toggleInfo">
          <font-awesome-icon :icon="['fas', 'right-to-bracket']" />
        </a>
      </div>
      <div class="user" v-show="this.username != ''">
        <a class="headerBtn" href="#" title="push">
          <font-awesome-icon :icon="['fas', 'bell']" />
        </a>
        <a class="headerBtn" href="#" title="mypage">
          <font-awesome-icon :icon="['fas', 'user-gear']" />
        </a>
        <a class="headerBtn" href="#" title="logout">
          <font-awesome-icon :icon="['fas', 'right-from-bracket']" />
        </a>
      </div>
      <div id="loginInfo">
        <form
          id="login"
          action="/member/loginProc"
          method="post"
          v-show="this.username == ''"
        >
          <div>
            <label for="username" class="col-3">아이디</label>
            <input type="text" name="mId" id="mId" class="col-8" />
          </div>

          <div>
            <label for="username" class="col-3">비밀번호</label>
            <input type="password" name="mPw" id="mPw" class="col-8" />
          </div>

          <div>
            <a class="btn btn-sm btn-primary" @click="fn_submitFrm"> 로그인 </a>
          </div>
        </form>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      username: "",
    };
  },
  mounted() {
    this.fn_loginChk();
  },
  methods: {
    async fn_loginChk() {
      await this.axios.get("/api/v1/loginchk").then((res) => {
        if (res.data != "" || res.data != null) {
          this.username = res.data;
        }
      });
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
    }
  }
}
</style>
