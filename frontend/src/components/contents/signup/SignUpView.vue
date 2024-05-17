<script setup></script>

<template>
  <form id="signupFrm" name="signupFrm">
    <div class="row">
      <div class="col-2">
        <label for="username" class="col-12">
          ID&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-6">
        <input
          type="text"
          id="username"
          class="signIpt col-12"
          placeholder="id"
        />
      </div>
      <div class="col-4"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="pw" class="col-12">
          Password&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-6">
        <input
          type="password"
          id="pw"
          class="signIpt col-12"
          placeholder="password"
        />
      </div>
      <div class="col-4"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="pwChk" class="col-12">
          Password Check&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-6">
        <input
          type="password"
          id="pwChk"
          class="signIpt col-12"
          placeholder="password check."
        />
      </div>
      <div class="col-4"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="email" class="col-12">
          E-mail&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-6">
        <input
          type="email"
          id="email"
          class="signIpt col-12"
          placeholder="id@domain.com"
        />
      </div>
      <div class="col-4"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="phone" class="col-12">
          phone&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-6">
        <input
          type="phone"
          id="phone"
          class="signIpt col-12"
          placeholder="### - #### - ####"
        />
      </div>
      <div class="col-4"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="addr1" class="col-12"> address&nbsp; </label>
      </div>
      <div class="col-6">
        <input type="text" id="addr1" class="signIpt col-12" placeholder="" />
      </div>
      <div class="col-4"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="addr2" class="col-12"></label>
      </div>
      <div class="col-6">
        <input type="text" id="addr2" class="signIpt col-12" placeholder="" />
      </div>
      <div class="col-4"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="addr3" class="col-12"></label>
      </div>
      <div class="col-6">
        <input type="text" id="addr3" class="signIpt col-12" placeholder="" />
      </div>
      <div class="col-4"></div>
    </div>
  </form>
</template>

<script>
export default {};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
form#signupFrm {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;

  div.row {
    display: flex;
    align-items: center;
    height: 70px;
    border-bottom: 1px solid #ececec;
    margin: 0 0;
    width: 80%;

    div {
      border-right: 1px solid #ececec;

      span.required {
        color: orange;
      }

      label {
        padding-left: 10px;
        text-align: left;
      }

      input.signIpt {
        border: none;
        border-bottom: 1px solid darkgray;
      }
    }
  }
}
</style>
