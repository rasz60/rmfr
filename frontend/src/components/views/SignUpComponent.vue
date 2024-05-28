<script setup>
import signupDatas from "@b-js/contents/signup/data.js";
import signupMethods from "@b-js/contents/signup/methods.js";
</script>

<template>
  <div id="signup">
    <form id="signupFrm" name="signupFrm">
      <table>
        <tbody>
        <tr>
          <td>
            <span class="loginlogo" style="font-size: 40px; font-weight: bold; margin: 25px 10px 0px 0px; color: #000000;">긁?</span>
            <span style="font-size: 20px; font-weight: bold;">회원가입</span>
          </td>
        </tr>
        <tr>
          <td class="req">아이디</td>
        </tr>
        <tr>
          <td>
            <input type="text"
                   id="username"
                   class="umem"
                   size="30"
                   maxlength="20"
                   autofocus
                   placeholder="아이디를 입력해주세요."
                   @keyup="fnUsernameVaild($event)">
            <!--            <div id="idCheck" @click="fnUsernameDupChk"></div>-->
            <a class="btn btn-sm btn-outline-secondary"
               id="username"
               @click="fnUsernameDupChk">중복확인</a>
          </td>
        </tr>
        <tr>
          <td>
            <span class="unchkd v-badge" id="username">
            <font-awesome-icon :icon="['fas', 'circle-check']"/>
              <span class="ruleTxt" style="font-size:12px">
                6~20자리, 영문 소/대문자, 숫자, 특수문자 (-, _)
              </span>
            </span>
          </td>
        </tr>
        <tr>
          <td class="req">패스워드</td>
        </tr>
        <tr>
          <td><input type="password"
                     id="pw"
                     class="umem"
                     size="40"
                     placeholder="비밀번호를 입력해주세요."
                     @keyup="fnPasswordValid($event)"></td>
        </tr>
        <tr>
          <td>
            <input type="password"
                   id="pwChk"
                   class="umem"
                   size="40"
                   placeholder="비밀번호를 한번 더 입력해주세요."
                   @keyup="fnPasswordChk($event)">
            <div>
              <span class="unchkd v-badge" id="pwChk"></span>
              <span class="ruleTxt"></span>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div>
              <span class="unchkd v-badge" id="pw">
                <font-awesome-icon :icon="['fas', 'circle-check']" />
              </span>
              <span class="ruleTxt" style="font-size:12px">
                8~16자리, 영문 소/대문자, 숫자, <br />특수문자
                ($,`,~,!,@,$,!,%,*,#,^,?,&,,(,),-,_,=,+)
              </span>
            </div>
          </td>
        </tr>
<!--        <tr>
          <td>
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
                  <option value="0">&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;</option>
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
                <span class="ruleTxt"></span>
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="row certBox">
              <div class="col-2">
                <label for="emailCert" class="col-12">
                  이메일 인증
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
                <span class="ruleTxt"></span>
              </div>
            </div>
          </td>
        </tr>-->
        <tr>
          <td class="req">전화번호</td>
        </tr>
        <tr>
          <td>
            <input
                type="text"
                class="umem"
                id="pHead"
                size="5"
                @change="fnPhoneNumberValid($event)"
            />
            -
            <input
                type="text"
                class="umem"
                id="pMid"
                size="5"
                @keyup="fnPhoneNumberValid($event)"
            />
            -
            <input
                type="text"
                class="umem"
                id="pLast"
                size="5"
                @keyup="fnPhoneNumberValid($event)"
            />
          </td>
        </tr>
        <tr>
          <td>
            <span class="unchkd v-badge" id="phone">
              <font-awesome-icon :icon="['fas', 'circle-check']" />
            </span>
            <span class="ruleTxt"
                  style="font-size:12px"> 형식에 맞지않는 휴대폰 번호는 저장되지 않습니다.</span>
          </td>
        </tr>
        <tr>
          <td class="req">주소 입력</td>
        </tr>
        <tr>
          <td>
            <input
                type="text"
                class="umem"
                id="zipCode"
                v-model="signupInfo.zipCode.value"
                @click="fnSearchZipCode"
                size="23" readonly>
            <button type="button"
                    id="zipBtn"
                    @click="execDaumPostcode">우편번호찾기</button>
          </td>
        </tr>
        <tr>
          <td>
            <input
                type="text"
                class="umem"
                id="addr2"
                size="40"
                readonly
                @click="fnSearchZipCode"
                v-model="signupInfo.addr1.value"
            />
          </td>
        </tr>
        <tr>
          <td>
            <input
                type="text"
                class="umem"
                id="addr3"
                size="40"
                v-model="signupInfo.addr2.value"
            />
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <button type="submit"
                    id="btnSignup"
                    @click="frmSubmit">가입하기</button>
          </td>
        </tr>
        <div class="row btnBox">
          <a class="btn btn-sm btn-secondary" id="btnSignup" @click="frmSubmit">
            가입하기
          </a>
        </div>
        </tbody>
      </table>
    </form>
  </div>
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

<style scoped>
@import "@/assets/css/contents/member/member.css";

</style>