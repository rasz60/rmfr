<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="#">
        <font-awesome-icon :icon="['fas', 'bars']" style="color: #fafafa;"></font-awesome-icon>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-navbar-brand href="/" style="font-weight: bold; color: white;">rfmr</b-navbar-brand>
        </b-navbar-nav>

        <b-navbar-nav class="ms-auto" center>
          <b-nav-form width="30%">
            <b-form-input size="lg" class="mr-sm-2" placeholder="검색어를 입력해주세요." style="width: 700px;" ></b-form-input>
            <b-button><font-awesome-icon :icon="['fas', 'magnifying-glass']" style="color: #fafafa;" size="2x"/></b-button>
          </b-nav-form>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ms-auto" right>
          <b-navbar-brand href="#">
            <font-awesome-icon :icon="['far', 'bell']" style="color: #fafafa;" size="2x"/>
          </b-navbar-brand>
          <b-navbar-brand href="/member/signin">
            <font-awesome-icon :icon="['far', 'circle-user']" style="color: #fafafa;" size="2x" />
          </b-navbar-brand>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  // 홈페이지 url 이름 설정.
  name: "headerComponent",

};

</script>

<style>

</style>