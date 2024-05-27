export default {
  fn_go_href(href) {
    location.href = href;
  },
  fn_menuDrawer() {
    console.log("fn_menuDrawer : " + this.$menuDrawer);
    return this.$menuDrawer;
  },
};
