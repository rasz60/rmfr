<script setup></script>

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
    return {
      cert: false,
      details: {
        username: {
          value: "",
          name: "아이디",
          nullable: false,
        },
        password: {
          value: "",
          eValue: "",
          name: "비밀번호",
          nullable: false,
          chkd: false,
        },
        email: {
          bValue: "",
          eValue: "",
          name: "이메일",
          nullable: false,
          chkd: false,
        },
        phoneNumber: {
          value: "",
          name: "휴대폰",
          nullable: false,
        },
        zipCode: {
          value: "",
          name: "우편번호",
          nullable: true,
        },
        addr1: {
          value: "",
          name: "주소",
          nullable: true,
        },
        addr2: {
          value: "",
          name: "상세주소",
          nullable: true,
        },
      },
      validCode: "",
    };
  },
  mounted() {
    this.loadDaumPostcodeScript();
    this.validCodeTime;
  },
  methods: {
    async currPwChk() {
      var currPw = document.querySelector("input#currPw").value;
      if (currPw != "") {
        await this.axios
          .get("/rest/v1/pwChk/" + encodeURIComponent(currPw))
          .then((res) => {
            var rst = res.data;

            if (!rst) {
              alert("비밀번호가 일치하지 않습니다.");
              return false;
            } else {
              this.userInfo();
              this.cert = rst;
            }
          });
      } else {
        alert("비밀번호를 입력해주세요.");
        return false;
      }
    },

    async userInfo() {
      await this.axios.get("/rest/v1/loginUserDetails").then((res) => {
        let jsonData = res.data.info;
        if (jsonData != null) {
          this.details.username.value = jsonData.mid;

          var email = jsonData.memail;
          this.details.email.bValue = email;
          this.details.email.eValue = email;
          document.querySelector("input#emailId").value = email.substring(
            0,
            email.indexOf("@")
          );
          document.querySelector("select#domain").value = email.substring(
            email.indexOf("@") + 1
          );

          var phone = jsonData.mphone;
          if (phone != "" && phone != null) {
            document.querySelector("select#pHead").value = phone.substring(
              0,
              3
            );
            document.querySelector("input#pMid").value = phone.substring(
              3,
              phone.length - 4
            );
            document.querySelector("input#pLast").value = phone.substring(
              phone.length - 4
            );
          }

          var addr1 = jsonData.maddr1;
          var addr2 = jsonData.maddr2;
          var addr3 = jsonData.maddr3;

          this.details.zipCode.value = addr1;
          this.details.addr1.value = addr2;
          this.details.addr2.value = addr3;

          if (addr1 != "" && addr1 != null) {
            document.querySelector("input#zipCode").value = addr1;
          }

          if (addr2 != "" && addr2 != null) {
            document.querySelector("input#addr1").value = addr2;
          }

          if (addr3 != "" && addr3 != null) {
            document.querySelector("input#addr2").value = addr3;
          }
        }
      });
    },

    //비밀번호 검증
    fnPasswordValid(evt) {
      var chk = false;
      var target = evt.target;
      var value = target.value.trim(); // 공백제거
      value = value.length > 16 ? value.substring(0, 16) : value; //16자리 이상인 경우 잘라내기

      // 정규식 체크
      var regExp =
        /(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
      chk = regExp.test(value);

      // 정규식 체크 결과
      if (chk) {
        // password value 세팅
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge chkd";
        this.details.password.eValue = value;
      } else {
        // password value 초기화
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge unchkd";
        this.details.password.eValue = "";
      }
      target.value = value;

      // 패스워드 체크
      this.fnPasswordChk();
    },

    // 비밀번호 확인
    fnPasswordChk() {
      var target = document.querySelector("input#newPwChk");
      var value = target.value.trim(); // 공백 제거
      value = value.length > 16 ? value.substring(0, 16) : value; // 16자리 이상 잘라내기

      // 정규식 체크된 value가 있고, 비밀번호 확인으로 입력한 값과 같은지 체크
      var chk =
        this.details.password.eValue != "" &&
        this.details.password.eValue == value;

      if (chk) {
        // 비밀번호 확인 완료 처리
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge chkd";
        this.details.password.chkd = true;
      } else {
        // 비밀번호 확인 실패 처리
        document.querySelector("span.v-badge#" + target.id).className =
          "v-badge unchkd";
        this.details.password.chkd = false;
      }
      target.value = value;
    },

    // 이메일 검증
    fnEmailValid() {
      var emailId = document.querySelector("input#emailId");
      var emailDomain = document.querySelector("select#domain");
      var certSnd = document.querySelector("a.cert");

      // 항목 모두 입력된 경우
      if (emailId.value != "" && emailDomain.value != "0") {
        this.details.email.eValue = emailId.value + "@" + emailDomain.value;

        // 값 입력 완료된 경우 처리
        document.querySelector("span.v-badge#email").className = "v-badge chkd";
        if (this.details.email.eValue == this.details.email.bValue) {
          certSnd.className = "btn btn-sm btn-dark cert";
          certSnd.innerText = "인증완료";
        } else {
          certSnd.className = "btn btn-sm btn-outline-secondary cert";
          certSnd.innerText = "인증하기";
        }
      } else {
        // 값이 완성되지 않은 경우 초기화
        document.querySelector("span.v-badge#email").className =
          "v-badge unchkd";
        certSnd.className = "btn btn-sm btn-secondary cert";
        certSnd.innerText = "인증하기";
        this.details.email.eValue = "";
      }

      // 가입버튼 활성화 여부 체크
      //this.signupValid();
    },

    // 이메일 인증번호 발송
    fnEmailCert() {
      var certSnd = document.querySelector("a.cert");
      // 입력된 값 체크
      if (this.details.email.eValue == "") {
        alert("이메일 주소를 정확히 입력해주세요.");
        return false;
      }
      // 이메일 주소 완성되지 않은 경우
      else {
        // UI 처리
        var certChk = certSnd.className.indexOf("btn-outline-secondary") > 0;
        if (certChk) {
          certSnd.innerText = "다시발송";
          certSnd.className = "btn btn-sm btn-dark cert";
          // 인증번호 발송
          this.sendValidCode();
        }
      }
    },

    // 인증번호 발송
    async sendValidCode() {
      // 메일 주소
      var mailAddress = this.details.email.eValue;

      // 인증번호 발송 api 호출
      await this.axios.get("/rest/v1/emailValid/" + mailAddress).then((res) => {
        const jsonData = res.data;
        this.validCode = jsonData.token; // base64 encoding된 인증번호
        this.setTimer(30); // 3분 타이머 적용
        alert("인증번호가 발송되었습니다.");
        this.certBoxCtrl(0); // 인증번호 입력 창 관리
      });
    },

    // 인증번호 입력 창 관리
    certBoxCtrl(type) {
      var certBox = document.querySelector("div.certBox");

      // type == 0 ? 활성화 : 비활성화 ;
      if (type == 0) {
        certBox.style.display = "flex";
      } else {
        certBox.style.display = "none";
      }
    },

    // 인증번호 만료
    validCodeExpired() {
      this.validCode = "";
      alert(
        "인증번호 유효시간이 만료되었습니다.\n다시 인증번호를 발송해주세요."
      );
    },

    // 타이머 설정
    setTimer(time) {
      // 1초에 한 번 씩 반복
      this.validCodeTime = setInterval(function () {
        if (time <= 0) {
          // 시간이 모두 지나면 만료 처리
          this.clearTimer();
          this.validCodeExpired();
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

    // 타이머 초기화
    clearTimer() {
      document.querySelector("div.certBox div.infoBox span.ruleTxt").innerText =
        "00:00";
      clearInterval(this.validCodeTime);
    },

    // 인증번호 확인
    validCodeChk() {
      var validCode = this.validCode;

      // 만료된 인증번호인 경우
      if (validCode == "") {
        alert("인증번호가 만료되었습니다. 다시 발송하여 인증해주세요.");
        return false;
      } else {
        // base64 encoding
        var validCodeIpt = window.btoa(
          document.querySelector("input#emailCert").value
        );

        // 인증번호 체크
        if (validCode == validCodeIpt) {
          // 인증 완료 처리
          var certSnd = document.querySelector("a.cert");
          var certIpt = document.querySelector("input#emailId");
          var certSelect = document.querySelector("select#domain");
          alert("메일 인증이 완료되었습니다.");
          this.clearTimer();
          this.certBoxCtrl(1);
          this.details.email.chkd = true;
          certSnd.innerText = "인증완료";

          certIpt.disabled = true;
          certSelect.disabled = true;
        }
        // 인증 실패
        else {
          alert("인증번호를 다시 확인해주세요.");
        }
      }
    },

    // 핸드폰 번호 검증
    fnPhoneNumberValid(evt) {
      var target = evt.target;
      var value = target.value.trim(); //공백제거

      // 앞자리 제외 value 체크
      if (target.id != "pHead") {
        // 4자리 이상인 경우 잘라내기
        value = value.length > 4 ? value.substring(0, 4) : value;
        target.value = value;
      }

      // 전화번호 형식 체크
      this.fnPhoneNumber();
    },

    // 전화번호 형식 체크
    fnPhoneNumber() {
      var pHead = document.querySelector("select#pHead").value;
      var pMid = document.querySelector("input#pMid").value;
      var pLast = document.querySelector("input#pLast").value;

      // 010으로 시작하면 4자리, 아니면 3~4자리
      var pMidMin = pHead == "010" ? 4 : 3;
      var pMidMax = pHead == "010" ? 4 : 3;

      var pBadge = document.querySelector("span.v-badge#phone");

      // 모든 값이 입력되어있고, 각 자리마다 길이가 맞는 경우
      if (
        pHead != "0" &&
        (pMid.length == pMidMin || pMid.length == pMidMax) &&
        pLast.length == 4
      ) {
        // 검증 완료 처리
        this.details.phoneNumber.eValue = pHead + pMid + pLast;
        pBadge.className = "chkd v-badge";
      } else {
        // 초기화
        this.details.phoneNumber.eValue = "";
        pBadge.className = "unchkd v-badge";
      }
    },

    fnPhoneNumberDel() {
      document.querySelector("select#pHead").value = 0;
      document.querySelector("input#pMid").value = "";
      document.querySelector("input#pLast").value = "";

      this.details.phoneNumber.nullable = true;
    },

    // 다음 주소 api script tag 추가
    loadDaumPostcodeScript() {
      const script = document.createElement("script");
      // 다음 주소 api cdn
      script.src =
        "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
      script.onload = () => {
        this.isScriptLoaded = true; // 스크립트가 로드되면 isScriptLoaded를 true로 설정
      };
      document.head.appendChild(script);
    },
    // 다음 주소 검색 호출
    execDaumPostcode() {
      if (window.daum && window.daum.Postcode) {
        // 팝업 호출
        new window.daum.Postcode({
          oncomplete: (data) => {
            // 우편번호 검색 완료 후의 처리 로직
            this.details.zipCode.value = data.zonecode;
            this.details.addr1.value = data.address;
            this.details.addr2.value = "";

            document.querySelector("input#zipCode").value = data.zonecode;
            document.querySelector("input#addr1").value = data.address;
            document.querySelector("input#addr2").value = "";

            document.querySelector("span#zipCode.v-badge").className =
              "chkd v-badge";
            document.querySelector("span#addr1.v-badge").className =
              "chkd v-badge";
          },
        }).open();
      }
      // 오류 처리
      else {
        console.error("Daum Postcode 스크립트가 로드되지 않았습니다.");
      }
    },

    // 수정 정보 submit
    async frmSubmit() {
      let editInfo = this.submitValueSet();

      if (Object.keys(editInfo).length > 1) {
        if (
          confirm(
            "형식에 맞지 않거나, 인증이 완료되지 않은 정보는 수정되지 않습니다."
          )
        ) {
          await this.axios
            .post("/member/settings/update", editInfo)
            .then((res) => {
              console.log(res);
            });
        }
      }

      alert("정보 저장이 완료되었습니다.");
      location.href = "/";
    },

    submitValueSet() {
      let details = {};
      for (var i in this.details) {
        let d = this.details[i];
        if (i == "username") {
          details[i] = d.value;
        } else {
          var v = this.setBefore(d);

          if (!d.nullable) {
            v == "" || v == null ? false : (details[i] = v);
          } else {
            details[i] = v;
          }
        }
      }

      return details;
    },

    setBefore(info) {
      var setVal = info.eValue == null ? info.value : info.eValue;

      if (info.chkd != null && info.chkd == false) {
        setVal = "";
      }

      return setVal;
    },

    async fnSignout() {
      if (confirm("rmfr의 모든 정보를 삭제하고 탈퇴할까요?")) {
        await this.axios
          .get("/member/signout/" + this.details.username.value)
          .then((res) => {
            if (res.status) {
              alert("탈퇴가 완료되었습니다.");
              location.href = "/logout";
            } else {
              alert("탈퇴에 실패하였습니다.");
              location.href = "/";
            }
          });
      }
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

  div.row.btnBox {
    display: flex;
    justify-content: space-around;
  }

  div.certBox {
    display: none;
  }
}
</style>
