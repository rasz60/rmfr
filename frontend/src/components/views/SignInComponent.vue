<template>
  <div id="wrap">
    <!-- 로그인시작 -->
    <div id="signin">
      <form action="/member/loginProc" id="loginFrm" name="loginFrm" method="post" v-show="!this.login">
        <table>
          <tbody>
          <tr>
            <td colspan="2">
              <a href="/">
                <span class="loginlogo">rfmr</span>
              </a>
            </td>
          </tr>
          <tr>
            <td colspan="2"><input type="text" class="loginInput" name="m_Id" id="m_Id"
                                   autofocus size="30" placeholder="아이디를 입력하세요" /></td>
          </tr>
          <tr>
            <td colspan="2"><input type="password" class="loginInput" name="m_Pw"
                                   id="m_Pw" size="30" placeholder="비밀번호를 입력하세요" /></td>
          </tr>
          <tr>
            <td colspan="2">
              <button type="button" id="loginBtn" class="btn btn-danger">로그인</button>
            </td>
          </tr>
          <tr>
            <td>
              <button type="button" id="signUpBtn" class="buttomBtn"
                      style="margin: 0 0 0 40px;" onclick="location.href='/member/signup'">회원가입</button>
            </td>
            <td>
              <button type="button" id="loginFind"  class="buttomBtn">아이디/비밀번호 찾기</button>
            </td>
          </tr>
          </tbody>
        </table>
      </form>
    </div>
    <!-- 로그인끝 -->
  </div>
  <!-- div#wrap -->

</template>

<script>
export default {
  name: "SignIp",

};
</script>

<style scoped>
@import "@/assets/css/contents/member/member.css";

</style>