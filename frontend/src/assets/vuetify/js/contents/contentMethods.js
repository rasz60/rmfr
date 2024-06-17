export default {
  fn_go_href(href) {
    var currUrl = this.$router.currentRoute._value.href;

    if (currUrl == href) this.$router.go(0);
    else this.$router.push(href);
  },
};
