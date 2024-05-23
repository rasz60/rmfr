<template>
  <div>
    <b-navbar toggleable="lg" type="" variant="info">
      <b-navbar-brand href="#">
        <div id="menubar">
          <span class="toggleBtn" @click="fn_toggleMenu">
            <font-awesome-icon :icon="['fas', 'bars']" />
          </span>
        </div>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/">rmfr</b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ms-auto" center>
          <b-nav-form width="30%">
            <b-form-input size="sm" class="mr-sm-2" placeholder="검색"></b-form-input>

            <b-button size="sm" class="my-2 my-sm-0" type="submit" ><font-awesome-icon :icon="['fas', 'magnifying-glass']" /></b-button>
          </b-nav-form>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ms-auto" right>
          <b-navbar-brand href="#">
            <font-awesome-icon :icon="['far', 'bell']" />
          </b-navbar-brand>

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em><font-awesome-icon :icon="['far', 'user']" /></em>
            </template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>

</template>

<script>
export default {
  // 홈페이지 url 이름 설정.
  name: "headerComponent",

  compatConfig: { MODE: 3 }
};
</script>

<style>

</style>