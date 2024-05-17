<template>
  <section id="header">
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
        <label for="searchIpt"></label>
        <input id="searchIpt" placeholder="검색" />
        <div id="suffixIcon">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" />
        </div>
      </div>
    </div>
    <div id="buttonBox">buttonBox</div>
  </section>
</template>

<script>
export default {};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
section#header {
  height: 80px;
  display: flex;
  border-bottom: 1px solid #ececec;

  div#logo {
    width: 15%;
    height: 100%;

    display: flex;
    justify-content: center;
    align-items: center;

    a {
      text-decoration: none;
      color: black;
      cursor: pointer;
    }
  }

  div#searchBox {
    width: 65%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    div#searchIptBox {
      width: 80%;
      height: 80%;
      display: flex;
      justify-content: center;
      align-items: center;
      border: 1px solid #ececec;
      border-radius: 50px;
      overflow: hidden;

      input#searchIpt {
        width: 80%;
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
        color: #ececec;
      }
      div#suffixIcon {
        width: 15%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 1.3em;
        background-color: lightgray;
      }
    }
  }

  div#buttonBox {
    width: 15%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
