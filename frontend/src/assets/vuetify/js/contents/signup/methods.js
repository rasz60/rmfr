export default {
  // 아이디 중복 체크
  async fnUsernameDupChk() {
    var value = this.signupInfo.username.value;
    var idChkd = this.signupInfo.username.chkd;
    // 정규식 검증 여부
    if (!idChkd) {
      alert("형식에 맞는 아이디를 입력해주세요.");
    } else {
      // 정규식 검증이 끝난 경우, 중복 체크 api 호출
      await this.axios.get("/rest/v1/usernameDupChk/" + value).then((res) => {
        const jsonData = res.data;
        // 중복인 경우 true, 아닌 경우 false
        this.signupInfo.username.dupchk = !jsonData;
        if (!jsonData) {
          // 사용 가능한 경우에만 value 세팅
          alert("사용 가능한 아이디입니다.");
          this.signupInfo.username.lastdupchk = value;
        } else {
          // 중복인 경우 value 초기화
          alert("이미 사용 중인 아이디입니다.");
        }
      });
    }
  },

  // 이메일 인증번호 발송
  fnEmailCert() {
    // 입력된 값 체크
    if (this.signupInfo.email.chkd && !this.signupInfo.email.certDone) {
      // 인증번호 발송
      this.sendValidCode();
    }
    // 이메일 주소 완성되지 않은 경우
    else if (!this.signupInfo.email.chkd) {
      alert("이메일 주소를 정확히 입력해주세요.");
    } else if (this.signupInfo.email.certDone) {
      alert("이미 이메일 인증이 완료되었습니다.");
    }
  },

  // 인증번호 발송
  async sendValidCode() {
    // 메일 주소
    var mailAddress = this.signupInfo.email.value;

    // 인증번호 발송 api 호출
    await this.axios.get("/rest/v1/emailValid/" + mailAddress).then((res) => {
      const jsonData = res.data;
      this.validCode = jsonData.token; // base64 encoding된 인증번호

      if (this.validCodeTime != null) {
        this.clearTimer(this.validCodeTime);
      }
      document.querySelector("span#validCodeTimer").className = "";
      this.validCodeTime = this.setTimer(179); // 3분 타이머 적용
      this.signupInfo.email.cert = true;
      alert("인증번호가 발송되었습니다.");
    });
  },

  // 타이머 설정
  setTimer(time) {
    // 1초에 한 번 씩 반복
    let interval = setInterval(function () {
      var span = document.querySelector("span#validCodeTimer");
      if (time == 0) {
        span.className = "expired";
        this.clearInterval(interval);
        this.validCode = "";
        alert(
          "인증번호 유효시간이 만료되었습니다.\n다시 인증번호를 발송해주세요."
        );
      }
      var minutes = "0" + Math.trunc(time / 60);
      var seconds =
        time % 60 < 10 ? "0" + Math.trunc(time % 60) : Math.trunc(time % 60);
      var timerString = minutes + ":" + seconds;
      span.innerText = timerString;
      time--;
    }, 1000);
    return interval;
  },

  // 타이머 초기화
  clearTimer(interval) {
    clearInterval(interval);
  },

  // 인증번호 확인
  validCodeChk(v) {
    if (this.validCode == "") {
      alert("인증번호가 만료되었습니다. 다시 발송하여 인증해주세요.");
      return false;
    }

    // base64 encoding
    var validCodeIpt = window.btoa(v);
    // 인증번호 체크
    if (this.validCode == validCodeIpt) {
      this.clearTimer(this.validCodeTime);
      this.signupInfo.email.certDone = true;
      alert("인증이 완료되었습니다.");
      return true;
    } else {
      alert("인증번호를 다시 확인해주세요.");
      return false;
    }
  },
  // 전화번호 형식 체크
  fnPhoneNumber() {
    var pHead = this.signupInfo.phoneNumber.head;
    var pMid = this.signupInfo.phoneNumber.mid;
    var pLast = this.signupInfo.phoneNumber.last;
    var chk = true;

    if (pHead && pMid && pLast) {
      if (pHead == "010") {
        chk = pMid.length == 4;
      } else {
        chk = pMid.length >= 3 && pMid.length <= 4;
      }

      if (chk) {
        chk = pLast.length == 4;
      }

      if (chk) {
        this.signupInfo.phoneNumber.full = pHead + pMid + pLast;
      }
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
    console.log(window.postcode);
    if (window.daum && window.daum.Postcode) {
      // 팝업 호출
      this.popup = new window.daum.Postcode({
        oncomplete: (data) => {
          console.log(window.postcode);

          // 우편번호 검색 완료 후의 처리 로직
          this.signupInfo.zipCode.value = data.zonecode;
          this.signupInfo.addr1.value = data.address;

          // 가입버튼 활성화 여부 체크
          //this.signupValid();
        },
      });

      this.popup.open();
    }
    // 오류 처리
    else {
      console.error("Daum Postcode 스크립트가 로드되지 않았습니다.");
    }
  },

  fnAddrReset() {
    this.signupInfo.zipCode.value = "";
    this.signupInfo.addr1.value = "";
    this.signupInfo.addr2.value = "";
  },

  // 회원가입 정보 submit
  frmSubmit() {
    var signup = {
      username: this.signupInfo.username.value,
      password: this.signupInfo.password.value,
      mEmail: this.signupInfo.email.value,
      mPhone: this.signupInfo.phoneNumber.full,
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
  },

  async validate() {
    let chk = await this.$refs.form.validate();
    console.log(chk);
    if (!chk.valid) {
      alert("입력한 값을 다시 확인해주세요.");
      return false;
    } else if (!this.signupInfo.username.dupchk) {
      alert("아이디 중복확인을 완료해주세요.");
      return false;
    } else if (!this.signupInfo.email.certDone) {
      alert("이메일 인증을 완료해주세요.");
      return false;
    } else {
      if (confirm("rmfr 회원으로 가입할까요?")) {
        this.fnPhoneNumber();
        this.frmSubmit();
      }
    }
  },
  fnAlert() {
    alert("주소 검색을 이용하여 입력해주세요.");
  },
};
