export default {
  // 전체 검증
  signupValid() {
    var chk = true;

    // 검증 결과
    const valid = {
      rst: true,
      msg: "",
    };

    // signupInfo value check
    for (var i in this.signupInfo) {
      // index 하나씩
      var info = this.signupInfo[i];

      // 필수인 것들만 체크
      if (info.required) {
        // value check
        if (info.value == "") {
          chk = false;
        } else {
          // 중복 체크가 있는 속성인 경우
          if (info.dupchk != null) {
            // 정규식, 중복 모두 확인
            chk = info.chkd && info.dupchk;
          }
          // 정규식 체크 속성만 있는 경우
          else {
            if (info.chkd != null) {
              // 정규식 체크 속성만 확인
              chk = info.chkd;
            }
          }
        }
      }

      // false인 경우 break
      if (!chk) {
        valid.rst = chk;
        valid.msg = info.name + " 을(를) 다시 확인해주세요.";
        break;
      }
    }

    // 가입하기 버튼 변경
    var btnSignup = document.querySelector("a#btnSignup");
    if (chk) {
      btnSignup.className = "btn btn-sm btn-primary";
    } else {
      btnSignup.className = "btn btn-sm btn-secondary";
    }
    return valid;
  },

  // 아이디 검증
  fnUsernameVaild(evt) {
    var chk = false;
    var target = evt.target;
    var dupchkBtn = document.querySelector("a#username");

    var value = target.value.trim(); // 공백제거
    value = value.length > 20 ? value.substring(0, 20) : value; // 20자리 이상인 경우 잘라내기

    // 아이디 정규식 체크
    var regExp = /^[a-z0-9-_]{6,20}$/;
    chk = regExp.test(value);

    var regChk = document.querySelector("span.v-badge#" + evt.target.id);

    // 정규식 체크 결과
    if (chk) {
      regChk.className = "chkd v-badge";
      this.signupInfo.username.chkd = true;
    } else {
      regChk.className = "unchkd v-badge";
      this.signupInfo.username.chkd = false;
    }

    // 중복확인 다시 하도록 초기화
    dupchkBtn.className = "btn btn-sm btn-outline-secondary";
    dupchkBtn.innerText = "중복확인";
    this.signupInfo.username.dupchk = false;

    // target value 세팅
    target.value = value;

    // 가입 버튼 활성화 여부 체크
    this.signupValid();
  },

  // 아이디 중복 체크
  async fnUsernameDupChk() {
    var username = document.querySelector(
      "form#signupFrm input#username"
    ).value;
    var idChkd = this.signupInfo.username.chkd;
    var dupchkBtn = document.querySelector("a#username");

    // 정규식 검증 여부
    if (!idChkd) {
      alert("형식에 맞는 아이디를 입력해주세요.");
      return false;
    } else {
      // 정규식 검증이 끝난 경우, 중복 체크 api 호출
      await this.axios
        .get("/rest/v1/usernameDupChk/" + username)
        .then((res) => {
          const jsonData = res.data;
          // 중복인 경우 true, 아닌 경우 false
          this.signupInfo.username.dupchk = !jsonData;
          if (!jsonData) {
            // 사용 가능한 경우에만 value 세팅
            alert("사용 가능한 아이디입니다.");
            this.signupInfo.username.value = username;
            dupchkBtn.className = "btn btn-sm btn-dark";
            dupchkBtn.innerText = "확인완료";
          } else {
            // 중복인 경우 value 초기화
            alert("이미 사용 중인 아이디입니다.");
            dupchkBtn.className = "btn btn-sm btn-outline-secondary";
            dupchkBtn.innerText = "중복확인";
            this.signupInfo.username.value = "";
            return false;
          }
        });
    }
    // 가입하기 버튼 활성화 체크
    this.signupValid();
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
      this.signupInfo.password.value = value;
    } else {
      // password value 초기화
      document.querySelector("span.v-badge#" + target.id).className =
        "v-badge unchkd";
      this.signupInfo.password.value = "";
    }
    target.value = value;

    // 가입하기 버튼 활성화 체크
    this.fnPasswordChk();
  },

  // 비밀번호 확인
  fnPasswordChk() {
    var target = document.querySelector("input#pwChk");
    var value = target.value.trim(); // 공백 제거
    value = value.length > 16 ? value.substring(0, 16) : value; // 16자리 이상 잘라내기

    // 정규식 체크된 value가 있고, 비밀번호 확인으로 입력한 값과 같은지 체크
    var chk =
      this.signupInfo.password.value != "" &&
      this.signupInfo.password.value == value;

    if (chk) {
      // 비밀번호 확인 완료 처리
      document.querySelector("span.v-badge#" + target.id).className =
        "v-badge chkd";
      this.signupInfo.password.chkd = true;
    } else {
      // 비밀번호 확인 실패 처리
      document.querySelector("span.v-badge#" + target.id).className =
        "v-badge unchkd";
      this.signupInfo.password.chkd = false;
    }
    target.value = value;

    // 가입하기 버튼 활성화 체크
    this.signupValid();
  },

  // 이메일 검증
  fnEmailValid() {
    var emailId = document.querySelector("input#emailId");
    var emailDomain = document.querySelector("input#emailDomain");
    // var certSnd = document.querySelector("a.cert");

    // r1605866. delete 이메일 인증 제거(임시 차후 다시 등록예정)
    if (emailId.value != "" && emailDomain.value != "0") {
      this.signupInfo.email.value = emailId.value + "@" + emailDomain.value;
      this.signupInfo.email.chkd = true;
    } else {
      alert("이메일을 입력해주세요!!");
      this.signupInfo.email.value = "";
      this.signupInfo.email.chkd = false;
    }

    // 가입버튼 활성화 여부 체크
    this.signupValid();
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
    var pHead = document.querySelector("input#pHead").value;
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
      this.signupInfo.phoneNumber.value = pHead + pMid + pLast;
      pBadge.className = "chkd v-badge";
      console.log(this.signupInfo.phoneNumber);
    } else {
      // 초기화
      this.signupInfo.phoneNumber.value = "";
      pBadge.className = "unchkd v-badge";
    }
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
          this.signupInfo.zipCode.value = data.zonecode;
          this.signupInfo.addr1.value = data.address;

          // 가입버튼 활성화 여부 체크
          this.signupValid();
        },
      }).open();
    }
    // 오류 처리
    else {
      console.error("Daum Postcode 스크립트가 로드되지 않았습니다.");
    }
  },

  // 회원가입 정보 submit
  frmSubmit() {
    // 전체 value 체크
    var valid = this.signupValid();

    if (valid.rst) {
      var signup = {
        username: this.signupInfo.username.value,
        password: this.signupInfo.password.value,
        mEmail: this.signupInfo.email.value,
        mPhone: this.signupInfo.phoneNumber.value,
        mAddr1: this.signupInfo.zipCode.value,
        mAddr2: this.signupInfo.addr1.value,
        mAddr3: this.signupInfo.addr2.value,
      };

      // 전송
      this.axios
        .post("/api/signup/submit", JSON.stringify(signup))
        .then((res) => {
          if (res.status == 200) {
            alert("rmfr 가입이 완료되었습니다.");
            location.href = "/";
          }
        });
    } else {
      alert(valid.msg);
      return false;
    }
  },
};
