<script setup></script>

<template>
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
          id="zipCodeHead"
          class="signIpt col-4"
          placeholder=""
          readonly
          @click="fnSearchZipCode"
        />
        -
        <input
          type="text"
          id="zipCodeEnd"
          class="signIpt col-4"
          placeholder=""
          readonly
          @click="fnSearchZipCode"
        />

        <a class="btn btn-sm btn-outline-secondary" @click="fnSearchZipCode">
          주소찾기
        </a>

        <input type="hidden" id="addr1" class="signIpt col-12" placeholder="" />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge">
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
        />
      </div>
      <div class="col-3 infoBox">
        <span class="unchkd v-badge">
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
        <input type="text" id="addr3" class="signIpt col-12" placeholder="" />
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
    return {
      signupInfo: {
        username: {
          value: "",
          required: true,
          chkd: false,
          dupchk: false,
        },
        password: {
          value: "",
          required: true,
          chkd: false,
        },
        email: {
          value: "",
          required: true,
          chkd: true,
        },
        phoneNumber: {
          value: "",
          required: false,
        },
        zipCode: {
          value: "",
          required: false,
        },
        addr1: {
          value: "",
          required: false,
        },
        addr2: {
          value: "",
          required: false,
        },
      },
      validCode: "",
    };
  },
  methods: {
    // 전체 검증
    signupValid() {
      const signupInfo = this.signupInfo;
      var chk = true;
      for (var i in signupInfo) {
        var info = signupInfo[i];
        if (info.required) {
          if (info.value == "") {
            chk = false;
          } else {
            if (info.chkd != null || info.dupchk != null) {
              chk = info.chkd;
            }
          }
        }
        if (!chk) break;
      }

      var btnSignup = document.querySelector("a#btnSignup");
      if (chk) {
        btnSignup.className = "btn btn-sm btn-primary";
      } else {
        btnSignup.className = "btn btn-sm btn-secondary";
      }
    },

    // 아이디 검증
    fnUsernameVaild(evt) {
      var chk = false;
      var target = evt.target;
      var dupchkBtn = document.querySelector("a#username");
      var value = target.value.trim();
      value = value.length > 20 ? value.substring(0, 20) : value;
      var regExp = /^[a-z0-9-_]{6,20}$/;
      chk = regExp.test(value);
      var regChk = document.querySelector("span.v-badge#" + evt.target.id);
      if (chk) {
        regChk.className = "chkd v-badge";
        this.signupInfo.username.chkd = true;
      } else {
        regChk.className = "unchkd v-badge";
        this.signupInfo.username.chkd = false;
      }
      dupchkBtn.className = "btn btn-sm btn-outline-secondary";
      dupchkBtn.innerText = "중복확인";
      this.signupInfo.username.dupchk = false;
      target.value = value;
      this.signupValid();
    },

    fnUsernameDupChk() {
      var username = document.querySelector("input#username").value;
      var idChkd = this.signupInfo.username.chkd;
      var dupchkBtn = document.querySelector("a#username");
      if (!idChkd) {
        alert("형식에 맞는 아이디를 입력해주세요.");
        return false;
      } else {
        this.axios.get("/api/v1/usernameDupChk/" + username).then((res) => {
          const jsonData = res.data;
          this.signupInfo.username.dupchk = jsonData;
          if (!jsonData) {
            alert("사용 가능한 아이디입니다.");
            this.signupInfo.username.value = username;
            dupchkBtn.className = "btn btn-sm btn-dark";
            dupchkBtn.innerText = "확인완료";
          } else {
            alert("이미 사용 중인 아이디입니다.");
            dupchkBtn.className = "btn btn-sm btn-outline-secondary";
            dupchkBtn.innerText = "중복확인";
            this.signupInfo.username.value = "";
            return false;
          }
        });
      }
      this.signupValid();
    },

    //비밀번호 검증
    fnPasswordValid(evt) {
      var target = evt.target;
      var value = target.value.trim();
      value = value.length > 20 ? value.substring(0, 20) : value;
      var chk = false;
      var regExp =
        /(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
      chk = regExp.test(value);

      if (chk) {
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge chkd";
        this.signupInfo.password.value = value;
      } else {
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge unchkd";
        this.signupInfo.password.value = "";
      }
      target.value = value;
      this.fnPasswordChk();
    },
    fnPasswordChk() {
      var target = document.querySelector("input#pwChk");
      var value = target.value.trim();
      value = value.length > 20 ? value.substring(0, 20) : value;
      var chk =
        this.signupInfo.password.value != "" &&
        this.signupInfo.password.value == value;
      if (chk) {
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge chkd";
        this.signupInfo.password.chkd = true;
      } else {
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge unchkd";
        this.signupInfo.password.chkd = false;
      }
      target.value = value;
      this.signupValid();
    },

    // 이메일 검증
    fnEmailValid() {
      var emailId = document.querySelector("input#emailId");
      var emailDomain = document.querySelector("select#domain");
      var certSnd = document.querySelector("a.cert");
      var certBox = document.querySelector("div.certBox");
      if (emailId.value != "" && emailDomain.value != "0") {
        certSnd.className = certSnd.className.replace(
          "btn-secondary",
          "btn-outline-secondary"
        );
        document.querySelector("span.v-badge#email").className = "v-badge chkd";

        this.signupInfo.email.value = emailId.value + "@" + emailDomain.value;
      } else {
        document.querySelector("span.v-badge#email").className =
          "v-badge unchkd";

        certSnd.className = "btn btn-sm btn-secondary cert";

        certSnd.innerText = "인증하기";
        certBox.style.display = "none";
        this.signupInfo.email.value = "";
      }

      this.signupValid();
    },
    fnEmailCert() {
      if (this.signupInfo.email != "") {
        var certSnd = document.querySelector("a.cert");

        var certChk = certSnd.className.indexOf("btn-outline-secondary") > 0;

        if (certChk) {
          certSnd.innerText = "다시발송";
          certSnd.className = certSnd.className.replace(
            "btn-outline-secondary",
            "btn-dark"
          );
        }

        this.sendValidCode();
      } else {
        alert("이메일 주소를 정확히 입력해주세요.");
        return false;
      }
    },
    async sendValidCode() {
      var certBox = document.querySelector("div.certBox");
      var mailAddress = this.signupInfo.email.value;

      await this.axios.get("/api/v1/emailValid/" + mailAddress).then((res) => {
        const jsonData = res.data;
        this.validCode = jsonData.token;
        this.setTimer(179);

        setTimeout(this.validCodeExpired, 180 * 1000);
        certBox.style.display = "flex";
      });
    },
    validCodeExpired() {
      this.validCode = "";
      alert(
        "인증번호 유효시간이 만료되었습니다.\n다시 인증번호를 발송해주세요."
      );
    },
    setTimer(time) {
      this.validCodeTime = setInterval(function () {
        if (time <= 0) {
          this.clearTimer();
        }
        var minutes = "0" + Math.trunc(time / 60);
        var seconds =
          time % 60 < 10 ? "0" + Math.trunc(time % 60) : Math.trunc(time % 60);
        var timerString = minutes + ":" + seconds;
        document.querySelector(
          "div.certBox div.infoBox span.ruleTxt"
        ).innerText = timerString;
        time--;
      }, 1000);
    },
    clearTimer() {
      document.querySelector("div.certBox div.infoBox span.ruleTxt").innerText =
        "00:00";
      clearInterval(this.validCodeTime);
    },
    validCodeChk() {
      var validCode = this.validCode;
      if (validCode == "") {
        alert("인증번호가 만료되었습니다. 다시 발송하여 인증해주세요.");
        return false;
      } else {
        var validCodeIpt = window.btoa(this.validCodeIpt);
        if (validCode == validCodeIpt) {
          alert("메일 인증이 완료되었습니다.");
          // 작업 : 버튼 변경 추가
        } else {
          alert("인증번호를 다시 확인해주세요.");
        }
      }
    },

    // 핸드폰 번호 검증
    fnPhoneNumberValid(evt) {
      var target = evt.target;
      var value = target.value.trim();

      if (target.id != "pHead") {
        value = value.length > 4 ? value.substring(0, 4) : value;
        target.value = value;
      }

      this.fnPhoneNumber();
    },
    fnPhoneNumber() {
      var pHead = document.querySelector("select#pHead").value;
      var pMid = document.querySelector("input#pMid").value;
      var pLast = document.querySelector("input#pLast").value;

      var pMidMin = pHead == "010" ? 4 : 3;
      var pMidMax = pHead == "010" ? 4 : 3;

      var pBadge = document.querySelector("span.v-badge#phone");

      if (
        pHead != "0" &&
        (pMid.length == pMidMin || pMid.length == pMidMax) &&
        pLast.length == 4
      ) {
        this.signupInfo.phoneNumber.value = pHead + pMid + pLast;
        pBadge.className = "chkd v-badge";
        console.log(this.signupInfo.phoneNumber);
      } else {
        this.signupInfo.phoneNumber.value = "";
        pBadge.className = "unchkd v-badge";
      }

      this.signupValid();
    },
  },
};
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
    div.w-btn-col {
      display: flex;
      justify-content: space-between;
    }

    div.infoBox {
      display: flex;
      justify-content: flex-start;

      span.unchkd {
        color: red;
      }

      span.chkd {
        color: green;
      }

      span.ruleTxt {
        text-align: left;
        color: darkcyan;
        font-size: 11px;
        font-style: italic;
      }
    }
  }

  div.certBox {
    display: none;
  }
}
</style>
