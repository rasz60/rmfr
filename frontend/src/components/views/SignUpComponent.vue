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
        <tr>
          <td class="req">이메일</td>
        </tr>
        <tr>
          <td>
            <input
                type="text"
                name="emailId"
                id="emailId"
                class="umem"
                size="15"
                placeholder="아이디"
                @keyup="fnEmailValid"
            />
            @
            <input
                type="text"
                name="emailDomain"
                id="emailDomain"
                class="umem"
                size="15"
                placeholder="주소"
                @change="fnEmailValid" />
          </td>
        </tr>
        <tr>
          <td>
            <div>
              <span class="unchkd v-badge" id="pw">
                <font-awesome-icon :icon="['fas', 'circle-check']" />
              </span>
              <span class="ruleTxt" style="font-size:12px">
                이메일은 필수 입력값 입니다.
              </span>
            </div>
          </td>
        </tr>
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
                placeholder="우편번호 찾기 눌러주세요."
                size="27" readonly>
            <a class="btn btn-sm btn-outline-secondary"
               @click="execDaumPostcode">우편번호찾기</a>
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
          <td></td>
        </tr>
        <tr>
          <td>
            <div class="row btnBox">
              <a class="btn btn-sm btn-secondary"
                 id="btnSignup"
                 style="width: 80%"
                 @click="frmSubmit">
                가입하기
              </a>
            </div>
          </td>
        </tr>
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