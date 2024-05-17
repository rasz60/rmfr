<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <section id="contents">
    <div id="menuSlim" class="menuBox">
      <dl class="menu slim">
        <dd v-for="item in menus" :key="item" @click="fn_go_ref(item.href)">
          <span class="slimIcon">
            <font-awesome-icon :icon="['fas', item.icon]" />
          </span>
        </dd>
      </dl>
    </div>
    <div id="menuFull" class="menuBox">
      <dl class="menu full">
        <dd v-for="item in menus" :key="item" @click="fn_go_ref(item.href)">
          <div class="fullIcon">
            <font-awesome-icon :icon="['fas', item.icon]" />
          </div>
          <div class="fullTxt">
            <span>
              <font-awesome-icon
                v-for="char in Array.from(item.name)"
                :key="char"
                :icon="['fas', char]"
              />
            </span>
          </div>
        </dd>
      </dl>
    </div>
    <div id="content">
      <RouterView />
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      menus: [
        {
          name: "notice",
          href: "/board/notice",
          icon: "bullhorn",
        },
      ],
    };
  },
  methods: {
    fn_go_ref(href) {
      location.href = href;
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
section#contents {
  display: flex;
  flex-wrap: wrap;
  height: fit-content;
  min-height: 800px;

  dl.menu {
    list-style: none;
    padding-left: 0;

    dd {
      width: 100%;
      padding-top: 10px;
      padding-bottom: 10px;
      display: flex;
      cursor: pointer;

      justify-content: center;

      div.fullIcon {
        width: 10%;
      }

      div.fullTxt {
        width: 80%;

        span {
          font-size: 14px;

          svg {
            margin-left: 3px;
            margin-right: 3px;
          }
        }
      }
    }

    dd:hover {
      background-color: #ececec;
    }
  }

  div.menuBox {
    height: auto;
    padding-top: 20px;
    padding-bottom: 20px;
    border-right: 1px solid #ececec;
    border-left: 1px solid #ececec;
  }

  div#menuSlim {
    width: 3%;
    display: none;
  }

  div#menuFull {
    width: 15%;
  }

  div#content {
    width: 85%;
  }
}
</style>
