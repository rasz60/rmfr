<script setup>
import signupDatas from "@v-js/contents/signup/data.js";
import signupMethods from "@v-js/contents/signup/methods.js";
</script>

<template>
  <v-sheet id="signupBox">
    <v-form @submit.prevent id="vuetify_signup_frm">
      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            id="username"
            v-model="signupInfo.username.value"
            label="아이디(ID)"
            :rules="usernameRules"
            variant="outlined"
            required
            prepend-icon="fas fa-asterisk"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol">
          <v-btn @click="fnUsernameDupChk($event)" text="중복확인"></v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="password"
            label="비밀번호(Password)"
            :rules="pwRules"
            variant="outlined"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol"></v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="password"
            label="비밀번호 확인(Password Check)"
            :rules="pwChkRules"
            variant="outlined"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol"></v-col>
      </v-row>
    </v-form>
  </v-sheet>

  <!--기존-->
  <form id="signupFrm" name="signupFrm">
    <div class="row">
      <div class="col-2">
        <label for="username" class="col-12">
          아이디&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-7 w-btn-col">
        <input
          type="text"
          id="username"
          class="signIpt col-10"
          placeholder="id"
          @keyup="fnUsernameVaild($event)"
        />
        <a
          class="btn btn-sm btn-outline-secondary"
          id="username"
          @click="fnUsernameDupChk"
          >중복확인</a
        >
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="username">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;&nbsp;
        <span class="ruleTxt">
          6~20자리, 영문 소/대문자, 숫자, 특수문자 (-, _)
        </span>
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="pw" class="col-12">
          비밀번호&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-7">
        <input
          type="password"
          id="pw"
          class="signIpt col-12"
          placeholder="password"
          @keyup="fnPasswordValid($event)"
        />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="pw">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;&nbsp;
        <span class="ruleTxt">
          8~16자리, 영문 소/대문자, 숫자, <br />특수문자
          ($,`,~,!,@,$,!,%,*,#,^,?,&,,(,),-,_,=,+)
        </span>
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="pwChk" class="col-12">
          비밀번호 확인&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-7">
        <input
          type="password"
          id="pwChk"
          class="signIpt col-12"
          placeholder="password check"
          @keyup="fnPasswordChk($event)"
        />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="pwChk">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="email" class="col-12">
          이메일&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-7 w-btn-col">
        <input
          type="text"
          name="emailId"
          id="emailId"
          class="signIpt col-5"
          placeholder="id"
          @keyup="fnEmailValid"
        />
        @
        <select id="domain" class="email domain col-4" @change="fnEmailValid">
          <option value="0">--------------</option>
          <option value="naver.com">naver.com</option>
          <option value="gmail.com">gmail.com</option>
          <option value="hanmail.net">hanmail.net</option>
          <option value="hotmail.com">hotmail.com</option>
          <option value="daum.net">daum.net</option>
        </select>
        &nbsp;
        <a class="btn btn-sm btn-secondary cert" @click="fnEmailCert">
          인증하기
        </a>
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="email">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row certBox">
      <div class="col-2">
        <label for="emailCert" class="col-12">
          이메일 인증&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-7 w-btn-col">
        <input
          type="text"
          id="emailCert"
          class="signIpt col-10"
          placeholder="이메일로 발송된 인증번호를 확인해주세요."
        />
        &nbsp;
        <a class="btn btn-sm btn-outline-secondary" @click="validCodeChk"
          >인증하기</a
        >
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="emailCert">
          <font-awesome-icon :icon="['fas', 'envelope-circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="phone" class="col-12">휴대폰</label>
      </div>
      <div class="col-7 w-btn-col">
        <select class="col-3" id="pHead" @change="fnPhoneNumberValid($event)">
          <option value="0">------------</option>
          <option value="010">010</option>
          <option value="011">011</option>
          <option value="016">016</option>
          <option value="017">017</option>
          <option value="018">018</option>
          <option value="019">019</option>
        </select>
        -
        <input
          type="text"
          class="signIpt col-3"
          id="pMid"
          @keyup="fnPhoneNumberValid($event)"
        />
        -
        <input
          type="text"
          class="signIpt col-3"
          id="pLast"
          @keyup="fnPhoneNumberValid($event)"
        />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="phone">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt">
          형식에 맞지않는 휴대폰 번호는 저장되지 않습니다.
        </span>
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="addr1" class="col-12">
          우편번호&nbsp;
          <!--
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
          -->
        </label>
      </div>
      <div class="col-7 w-btn-col">
        <input
          type="text"
          id="zipCode"
          class="signIpt col-10"
          placeholder=""
          readonly
          @click="fnSearchZipCode"
          v-model="signupInfo.zipCode.value"
        />
        <a class="btn btn-sm btn-outline-secondary" @click="execDaumPostcode">
          주소찾기
        </a>
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="zipCode">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="addr2" class="col-12">
          주소&nbsp;
          <!--
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
          -->
        </label>
      </div>
      <div class="col-7">
        <input
          type="text"
          id="addr2"
          class="signIpt col-12"
          placeholder=""
          readonly
          @click="fnSearchZipCode"
          v-model="signupInfo.addr1.value"
        />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="addr1">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="addr3" class="col-12">상세주소</label>
      </div>
      <div class="col-7">
        <input
          type="text"
          id="addr3"
          class="signIpt col-12"
          placeholder=""
          v-model="signupInfo.addr2.value"
        />
      </div>
      <div class="col-3 infoBox"></div>
    </div>

    <div class="row btnBox">
      <a class="btn btn-sm btn-secondary" id="btnSignup" @click="frmSubmit">
        가입하기
      </a>
    </div>
  </form>
</template>

<script>
export default {
  data() {
    return signupDatas;
  },
  methods: signupMethods,

  mounted() {
    this.loadDaumPostcodeScript();
    this.validCodeTime;
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@v-css/contents/signup/signup.css";
#signupBox {
  width: 100%;
  display: flex;
  justify-content: center;
  padding-top: 30px;
}

#vuetify_signup_frm {
  width: 70%;

  .col {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .btnCol {
    padding-top: 0;
  }
}
</style>
