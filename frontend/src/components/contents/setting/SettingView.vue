<script setup></script>

<template>
  <form id="signupFrm" name="signupFrm">
    <div class="row">
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

    <div class="row">
      <div class="col-2">
        <label for="pw" class="col-12">현재 비밀번호</label>
      </div>
      <div class="col-7 w-btn-col">
        <input
          type="password"
          id="pw"
          class="signIpt col-10"
          placeholder="●●●●●●●●●"
        />
        &nbsp;
        <a class="btn btn-sm btn-secondary chkd"> 변경하기 </a>
      </div>
      <div class="col-3 infoBox"></div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="pw" class="col-12">
          새 비밀번호&nbsp;
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
          placeholder="new password"
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
          placeholder="new password check"
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
          id="addr1"
          class="signIpt col-12"
          placeholder=""
          readonly
          @click="fnSearchZipCode"
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
        <label for="addr2" class="col-12">상세주소</label>
      </div>
      <div class="col-7">
        <input type="text" id="addr2" class="signIpt col-12" placeholder="" />
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
      details: {
        username: {
          value: "",
          name: "아이디",
          editable: false,
        },
        password: {
          value: "",
          eValue: "",
          name: "비밀번호",
          editable: true,
        },
        email: {
          bValue: "",
          eValue: "",
          name: "이메일",
          editable: true,
          chkd: false,
        },
        phoneNumber: {
          value: "",
          name: "휴대폰",
          editable: true,
          eValue: "",
        },
        zipCode: {
          value: "",
          name: "우편번호",
          editable: true,
          eValue: "",
        },
        addr1: {
          value: "",
          name: "주소",
          editable: true,
          eValue: "",
        },
        addr2: {
          value: "",
          name: "상세주소",
          editable: true,
          eValue: "",
        },
      },
      validCode: "",
    };
  },
  mounted() {
    this.userInfo();
    this.loadDaumPostcodeScript();
    this.validCodeTime;
  },
  methods: {
    async userInfo() {
      await this.axios.get("/rest/v1/loginUserDetails").then((res) => {
        let jsonData = res.data.info;

        console.log(jsonData);

        if (jsonData != null) {
          this.details.username.value = jsonData.mid;

          var email = jsonData.memail;
          this.details.email.bValue = email;
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

          console.log(document.querySelector("input#zipCode"));

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

            document.querySelector("span#zipCode.v-badge").className =
              "chkd v-badge";
            document.querySelector("span#addr1.v-badge").className =
              "chkd v-badge";

            // 가입버튼 활성화 여부 체크
            //this.signupValid();
          },
        }).open();
      }
      // 오류 처리
      else {
        console.error("Daum Postcode 스크립트가 로드되지 않았습니다.");
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

  div.certBox {
    display: none;
  }
}
</style>
