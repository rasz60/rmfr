<script setup>
import settingMethods from "@/assets/js/contents/setting/methods.js";
import settingDatas from "@/assets/js/contents/setting/data.js";
</script>

<template>
  <form id="signupFrm" name="signupFrm">
    <div class="row" v-show="!this.cert">
      <div class="col-2">
        <label for="currPw" class="col-12">현재 비밀번호</label>
      </div>
      <div class="col-7 w-btn-col">
        <input
          type="password"
          name="currPw"
          id="currPw"
          class="signIpt col-10"
          placeholder="●●●●●●●●●"
        />
        &nbsp;
        <a class="btn btn-sm btn-secondary" @click="currPwChk"> 변경하기 </a>
      </div>
      <div class="col-3 infoBox"></div>
    </div>

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="username" class="col-12">아이디</label>
      </div>
      <div class="col-7">
        <input
          type="text"
          id="username"
          class="signIpt col-12"
          placeholder="id"
          disabled="true"
          v-model="details.username.value"
        />
      </div>
      <div class="col-3 infoBox"></div>
    </div>

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="newPw" class="col-12">
          새 비밀번호&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-7">
        <input
          type="password"
          id="newPw"
          class="signIpt col-12"
          placeholder="new password"
          @keyup="fnPasswordValid($event)"
        />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="newPw">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;&nbsp;
        <span class="ruleTxt">
          8~16자리, 영문 소/대문자, 숫자, <br />특수문자
          ($,`,~,!,@,$,!,%,*,#,^,?,&,,(,),-,_,=,+)
        </span>
      </div>
    </div>

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="newPwChk" class="col-12">
          비밀번호 확인&nbsp;
          <span class="required">
            <font-awesome-icon :icon="['fas', 'asterisk']" />
          </span>
        </label>
      </div>
      <div class="col-7">
        <input
          type="password"
          id="newPwChk"
          class="signIpt col-12"
          placeholder="new password check"
          @keyup="fnPasswordChk($event)"
        />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge" id="newPwChk">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="emailId" class="col-12">
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
        <a class="btn btn-sm btn-dark cert" @click="fnEmailCert"> 인증완료 </a>
      </div>
      <div class="col-3 infoBox">
        <span class="chkd v-badge" id="email">
          <font-awesome-icon :icon="['fas', 'circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row certBox" v-show="this.cert">
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
      <div class="col-3 infoBox" v-show="this.cert">
        <span class="unchkd v-badge" id="emailCert">
          <font-awesome-icon :icon="['fas', 'envelope-circle-check']" />
        </span>
        &nbsp;
        <span class="ruleTxt"></span>
      </div>
    </div>

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="pHead" class="col-12">휴대폰</label>
      </div>
      <div class="col-7 w-btn-col">
        <select class="col-2" id="pHead" @change="fnPhoneNumberValid($event)">
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
          class="signIpt col-2"
          id="pMid"
          @keyup="fnPhoneNumberValid($event)"
        />
        -
        <input
          type="text"
          class="signIpt col-2"
          id="pLast"
          @keyup="fnPhoneNumberValid($event)"
        />

        <a class="btn btn-sm btn-outline-secondary" @click="fnPhoneNumberDel">
          삭제하기
        </a>
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

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="zipCode" class="col-12">
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
          v-model="details.zipCode.value"
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

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="addr1" class="col-12">
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
          id="addr1"
          class="signIpt col-12"
          placeholder=""
          readonly
          @click="fnSearchZipCode"
          v-model="details.addr1.value"
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

    <div class="row" v-show="this.cert">
      <div class="col-2">
        <label for="addr2" class="col-12">상세주소</label>
      </div>
      <div class="col-7">
        <input
          type="text"
          id="addr2"
          class="signIpt col-12"
          placeholder=""
          v-model="details.addr2.value"
        />
      </div>
      <div class="col-3 infoBox"></div>
    </div>

    <div class="row btnBox" v-show="this.cert">
      <a
        class="btn btn-sm btn-outline-success col-5"
        id="btnSignup"
        @click="frmSubmit"
      >
        변경하기
      </a>

      <a
        class="btn btn-sm btn-outline-danger col-5"
        id="btnSignup"
        @click="fnSignout"
      >
        탈퇴하기
      </a>
    </div>
  </form>
</template>

<script>
export default {
  data() {
    return settingDatas;
  },
  methods: settingMethods,
  mounted() {
    this.loadDaumPostcodeScript();
    this.validCodeTime;
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@/assets/css/contents/setting/setting.css";
</style>
