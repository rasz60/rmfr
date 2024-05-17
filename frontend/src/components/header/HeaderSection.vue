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
        <a class="headerBtn" href="/signup" title="signup">
          <font-awesome-icon :icon="['fas', 'user-plus']" />
        </a>
        <a class="headerBtn" href="#" title="login">
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
  methods: {
    fn_focusSearch() {
      document.querySelector("div#preIcon").style.visibility = "visible";
    },
    fn_blurSearch() {
      document.querySelector("div#preIcon").style.visibility = "hidden";
    },

    fn_toggleMenu() {
      console.log("toggleMenu");
      var slim = document.querySelector("div#menuSlim");
      var full = document.querySelector("div#menuFull");
      var contents = document.querySelector("div#content");
      console.log(slim, full);

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
    color: black;
  }

  div#menuToggle {
    height: 100%;

    display: flex;
    justify-content: center;
    align-items: center;

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
        outline: none;
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
    }
  }
}
</style>
