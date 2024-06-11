export default {
  async currPwChk() {
    var currPw = document.querySelector("input#currPw").value;
    if (currPw != "") {
      await this.axios
        .get("/rest/member/pwChk/" + encodeURIComponent(currPw))
        .then((res) => {
          var rst = res.data;

          if (!rst) {
            alert("비밀번호가 일치하지 않습니다.");
            return false;
          } else {
            this.cPwChk = rst;
            this.userInfo();
            //this.cert = rst;
          }
        });
    } else {
      alert("비밀번호를 입력해주세요.");
      return false;
    }
  },

  async userInfo() {
    await this.axios.get("/rest/member/loginUserDetails").then((res) => {
      let jsonData = res.data.info;
      if (jsonData != null) {
        this.details.username.value = jsonData.mid;

        var email = jsonData.memail;
        this.details.email.bValue = email;
        this.details.email.eValue = email;
        var phone = jsonData.mphone;
        if (phone != "" && phone != null) {
          var pHead = phone.substring(0, 3);
          var pMid = "";
          var pLast = "";
          if (phone.length == 11) {
            pMid = phone.substring(3, 7);
            pLast = phone.substring(7, 11);
          } else {
            pMid = phone.substring(3, 6);
            pLast = phone.substring(6, 10);
          }

          this.details.phoneNumber.head = pHead;
          this.details.phoneNumber.mid = pMid;
          this.details.phoneNumber.last = pLast;
          this.details.phoneNumber.full = phone;
        }

        this.details.zipCode.value = jsonData.maddr1;
        this.details.addr1.value = jsonData.maddr2;
        this.details.addr2.value = jsonData.maddr3;
      }
    });
  },

  // 이메일 인증번호 발송
  fnEmailCert() {
    // 입력된 값 체크
    if (!this.details.email.chngFlag) {
      this.details.email.chngFlag = true;
      this.details.email.eValue = "";
    } else if (
      this.details.email.chngFlag &&
      this.details.email.chkd &&
      !this.details.email.certDone
    ) {
      // 인증번호 발송
      this.sendValidCode();
    }
    // 이메일 주소 완성되지 않은 경우
    else if (this.details.email.chngFlag && !this.details.email.chkd) {
      alert("이메일 주소를 정확히 입력해주세요.");
    } else if (this.details.email.chngFlag && this.details.email.certDone) {
      alert("이미 이메일 인증이 완료되었습니다.");
    }
  },

  // 인증번호 발송
  async sendValidCode() {
    // 메일 주소
    var mailAddress = this.details.email.eValue;

    // 인증번호 발송 api 호출
    await this.axios
      .get("/rest/member/emailValid/" + mailAddress + "/c")
      .then((res) => {
        const jsonData = res.data;
        this.validCode = jsonData.token; // base64 encoding된 인증번호

        if (this.validCodeTime != null) {
          this.clearTimer(this.validCodeTime);
        }
        document.querySelector("span#validCodeTimer").className = "";
        this.validCodeTime = this.setTimer(179); // 3분 타이머 적용
        this.details.email.cert = true;
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
      this.details.email.certDone = true;
      alert("인증이 완료되었습니다.");
      return true;
    } else {
      alert("인증번호를 다시 확인해주세요.");
      return false;
    }
  },

  // 전화번호 형식 체크
  fnPhoneNumber() {
    var pHead = this.details.phoneNumber.head;
    var pMid = this.details.phoneNumber.mid;
    var pLast = this.details.phoneNumber.last;
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
        this.details.phoneNumber.full = pHead + pMid + pLast;
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
          this.details.zipCode.value = data.zonecode;
          this.details.addr1.value = data.address;

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

  fnAlert() {
    alert("주소 검색을 이용하여 입력해주세요.");
  },

  fnAddrReset() {
    this.details.zipCode.value = "";
    this.details.addr1.value = "";
    this.details.addr2.value = "";
  },

  async validate() {
    let chk = await this.$refs.form.validate();
    var chk2 = true;
    if (!chk.valid) {
      if (this.details.email.chngFlag && !this.details.email.certDone) {
        chk2 = confirm(
          "인증되지 않은 메일 주소를 저장할 수 없습니다. 변경한 이메일을 제외하고 저장할까요?"
        );
      }

      if (this.details.password.chngFlag && this.details.password.chkd) {
        chk2 = confirm(
          "형식에 맞지 않은 비밀번호를 저장할 수 없습니다. 변경한 비밀번호를 제외하고 저장할까요?"
        );
      }
    }

    if (chk2) {
      if (chk) {
        chk2 = confirm("수정한 정보를 저장할까요?");
      }
      chk2 ? this.frmSubmit() : false;
    }
  },
  // 수정 정보 submit
  async frmSubmit() {
    let editInfo = this.submitValueSet();

    if (Object.keys(editInfo).length > 1) {
      await this.axios
        .post("/api/member/settings/update", editInfo)
        .then(() => {
          alert("정보 저장이 완료되었습니다.");
          this.$router.push("/");
        });
    }
  },

  submitValueSet() {
    let details = {
      username: this.details.username.value,
    };

    if (this.details.password.chngFlag && this.details.password.chkd) {
      details.password = this.details.password.value;
    }

    if (this.details.email.chngFlag && this.details.email.certDone) {
      details.mEmail = this.details.email.eValue;
    }

    this.fnPhoneNumber();
    details.mPhone = this.details.phoneNumber.full;
    details.mAddr1 = this.details.zipCode.value;
    details.mAddr2 = this.details.addr1.value;
    details.mAddr3 = this.details.addr2.value;

    return details;
  },

  async fnSignout() {
    if (confirm("rmfr의 모든 정보를 삭제하고 탈퇴할까요?")) {
      await this.axios
        .get("/api/member/signout/" + this.details.username.value)
        .then((res) => {
          if (res.status) {
            alert("탈퇴가 완료되었습니다.");
            location.href = "/logout";
          } else {
            alert("탈퇴에 실패하였습니다.");
            this.$router.push("/");
          }
        });
    }
  },
};
