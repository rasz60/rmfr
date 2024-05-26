<template>
  <div id="signup">
    <form id="submitFrom" @submit.prevent="submitFrom">
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
            <input type="text" id="mId" class="umem" size="40" v-model="mId"
                   maxlength="20" autofocus placeholder="아이디를 입력해주세요.">
            <div id="idCheck"></div>
          </td>
        </tr>
        <tr>
          <td class="req">패스워드</td>
        </tr>
        <tr>
          <td><input type="password" id="mPw" class="umem" v-model="mPw" size="40"
                     placeholder="비밀번호를 입력해주세요."></td>
        </tr>
        <tr>
          <td>
            <input type="password" class="umem" id="mPwRe" v-model="mPwRe"  size="40"
                   placeholder="비밀번호를 한번 더 입력해주세요.">
            <div id="pwCheck"></div>
          </td>
        </tr>
        <tr>
          <td class="req">이메일</td>
        </tr>
        <tr>
          <td>
            <input type="text" class="umem" id="mEmail" v-model="mEmail" size="40"
                   placeholder="이메일 주소를 입력해주세요.">
          </td>
        </tr>
        <tr>
          <td style="display: flex">
            <input type="text" class="umem" id="mEmailChk" v-model="mEmailChk" size="30"
                   placeholder="인증번호">
            <button type="button" id="emailBtn">인증발송</button>
          </td>
        </tr>
        <tr>
          <td class="req">전화번호</td>
        </tr>
        <tr>
          <td>
            <input type="text" class="umem" id="mPhone" v-model="mPhone" size="40"
                   placeholder="전화번호를 입력해주세요." maxlength="13">
          </td>
        </tr>
        <tr>
          <td class="req">주소 입력</td>
        </tr>
        <tr>
          <td>
            <input type="text" class="umem" id="mAddr1" v-model="mAddr1" size="26" readonly>
            <button type="button" id="zipBtn">우편번호찾기</button>
          </td>
        </tr>
        <tr>
          <td>
            <input type="text" class="umem" id="mAddr2" v-model="mAddr2" size="40" readonly>
          </td>
        </tr>
        <tr>
          <td>
            <input type="text" class="umem" id="mAddr3" v-model="mAddr3" size="40">
          </td>
        </tr>
        <tr>
          <td>
            <div id="chk">
              <label>
                <input type="checkbox" id="allChk">
                <span>전부 동의합니다.</span>
              </label><br>
              <label>
                <input type="checkbox" id="uChk2">
                <span>이용약관에 동의합니다.</span>
              </label><br>
              <label>
                <input type="checkbox" id="uChk3">
                <span>개인정보수집·이용에 동의합니다.</span>
              </label>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <button type="submit" id="btnSignup">가입하기</button>
          </td>
        </tr>
        </tbody>
      </table>
    </form>
  </div>
</template>

<script>
export default {
  // 홈페이지 url 이름 설정.
  name: "SignUp",

  data(){
    return {
      mId: '',
      mPw: '',
      mPwRe: '',
      mEmail: '',
      mEmailChk: '',
      mAddr1: '',
      mAddr2: '',
      mAddr3: ''
    }
  },
  methods: {
    submitFrom(){
      console.log('dd');
    }
  }

};

</script>

<style scoped>
@import "@/assets/css/contents/member/member.css";

</style>