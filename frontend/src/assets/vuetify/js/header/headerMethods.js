export default {
  async fn_loginChk() {
    // 로그인 실패 시, loginError=true로 return
    var url = location.href;
    var query = url.substring(url.indexOf("?"));
    var param = new URLSearchParams(query);

    if (param.size > 0 && param.has("loginError")) {
      alert("로그인 정보를 다시 확인해주세요.");
      this.fn_toggleInfo();
    } else {
      await this.axios.get("/rest/member/loginchk").then((res) => {
        var jsonData = res.data.info;
        if (jsonData != "" && jsonData != null) {
          // 결과
          var mId = jsonData.mid;
          var thum = jsonData.thum;
          var mPwUpdateDate = jsonData.mpwUpdateDate;
          var mLevel = jsonData.mlevel;

          // data setting
          this.info.mId = mId;
          this.info.mLevel = mLevel;
          this.info.mPwUpdateDate = mPwUpdateDate;

          if (thum == "" || thum == null) {
            this.info.tmpImg = this.info.mId.substring(0, 1);

            const rColor = Math.floor(Math.random() * 128 + 64);
            const gColor = Math.floor(Math.random() * 128 + 64);
            const bColor = Math.floor(Math.random() * 128 + 64);

            document.querySelector("a#tmpImg").style.backgroundColor =
              "rgb(" + rColor + "," + gColor + "," + bColor + ")";
          } else {
            this.info.mThum = thum;
          }
          var updateDate = new Date(mPwUpdateDate);
          updateDate.setDate(updateDate.getDate() + 89);
          var diff = Math.abs(updateDate - new Date());
          var diffDate = Math.ceil(diff / (1000 * 60 * 60 * 24));

          this.info.mUd = diffDate;

          this.login = true;

          this.$loginInfo.mId = mId;
          this.$loginInfo.mLevel = mLevel;
          this.$loginInfo.mEntrId = jsonData.mentrId;
        } else {
          this.$loginInfo.mId = null;
          this.$loginInfo.mLevel = null;
          this.$loginInfo.mEntrId = null;
        }
      });
    }
  },
  fn_infoMenu(url) {
    location.href = url;
  },
  fn_focusSearch() {
    document.querySelector("div#preIcon").style.visibility = "visible";
  },
  fn_blurSearch() {
    document.querySelector("div#preIcon").style.visibility = "hidden";
  },

  fn_toggleMenu() {
    this.$menuDrawer.value = !this.$menuDrawer.value;
  },

  fn_toggleInfo() {
    this.loginDrawer = true;
  },
  fnFlagInit(type) {
    // 로그인 버튼 클릭 시 모든 데이터 초기화
    if (type == 0) {
      this.loginDrawer = !this.loginDrawer;
    }
    this.pwVisible = false;

    this.findInfo.findFlag = false;
    this.findInfo.cert = false;
    this.findInfo.certDone = false;
    this.findInfo.pwFindFlag = false;
    this.findInfo.chkd = false;
    if (this.findInfo.validCodeTime != null) {
      this.clearTimer(this.findInfo.validCodeTime);
    }
    this.findInfo.validCode = "";
    this.findInfo.validCodeTimer = "00:00";
    this.findInfo.mEmail = "";
    this.findInfo.mIdList = [];
    this.findInfo.mId = "";
    this.findInfo.nPw = "";
    this.findInfo.nPwChkVal = "";
    this.findInfo.npwRegChk = false;
    this.findInfo.nPwChk = false;

    // 폼 입력 값 초기화
    this.$refs.loginForm.reset();
    this.$refs.findForm.reset();
    this.$refs.nPwForm.reset();
  },
  fnFindFlag(type) {
    if (type == "id") {
      this.findInfo.findFlag = true;
      this.findInfo.pwFindFlag = false;
    } else {
      this.findInfo.findFlag = true;
      this.findInfo.pwFindFlag = true;
    }
  },
  async validateFindForm() {
    // 인증번호 발송 form validation
    let chk = await this.$refs.findForm.validate();

    if (chk.valid) {
      this.fnFindInfoCert();
    }
  },
  fnFindInfoCert() {
    var param = this.findInfo.mEmail + "/";
    var mId = this.findInfo.mId;

    if (this.findInfo.pwFindFlag) {
      param += mId;
    } else {
      param += "0";
    }

    this.fnEmailChkExists(param);
    console.log("find : " + param);
  },
  async fnEmailChkExists(p) {
    await this.axios.get("rest/member/mailChkExists/" + p).then((res) => {
      const jsonData = res.data;
      if (!this.findInfo.pwFindFlag) {
        if (jsonData.length > 0) {
          this.findInfo.mIdList = jsonData;
          this.sendValidCode();
        } else {
          alert("해당 메일주소로 가입된 아이디가 없습니다.");
          return false;
        }
      } else {
        if (jsonData[0] == "200") {
          this.sendValidCode();
        } else {
          var msg =
            jsonData[0] == "501"
              ? "존재하지 않는 아이디입니다."
              : "회원 정보의 이메일 주소와 일치하지 않습니다.";
          alert(msg);
          return false;
        }
      }
    });
  },

  // 인증번호 발송
  async sendValidCode() {
    // 메일 주소
    var mailAddress = this.findInfo.mEmail;

    // 인증번호 발송 api 호출
    await this.axios
      .get("/rest/member/emailValid/" + mailAddress + "/c")
      .then((res) => {
        const jsonData = res.data;
        this.findInfo.validCode = jsonData.token; // base64 encoding된 인증번호

        if (this.findInfo.validCodeTime != null) {
          this.clearTimer(this.findInfo.validCodeTime);
        }
        document.querySelector("span#validCodeTimer").className = "";
        this.findInfo.validCodeTime = this.setTimer(179); // 3분 타이머 적용
        this.findInfo.cert = true;
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
        this.findInfo.validCode = "";
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
    document.querySelector("span#validCodeTimer").innerText = "00:00";
  },

  // 인증번호 확인
  validCodeChk(v) {
    if (this.findInfo.cert) {
      if (this.findInfo.validCode == "") {
        alert("인증번호가 만료되었습니다. 다시 발송하여 인증해주세요.");
        return false;
      }

      // base64 encoding
      var validCodeIpt = window.btoa(v);
      // 인증번호 체크
      if (this.findInfo.validCode == validCodeIpt) {
        this.clearTimer(this.findInfo.validCodeTime);
        this.findInfo.certDone = true;
        alert("인증이 완료되었습니다.");
        return true;
      } else {
        alert("인증번호를 다시 확인해주세요.");
        return false;
      }
    }
  },

  async validateNPwForm() {
    // 비밀번호 변경 form validation
    let chk = await this.$refs.nPwForm.validate();

    if (chk.valid) {
      this.fnPwChange();
    }
  },

  async fnPwChange() {
    let data = {
      username: this.findInfo.mId,
      password: this.findInfo.nPw,
    };

    await this.axios.post("/api/member/settings/update", data).then((res) => {
      console.log(res);

      if (res.status == 200) {
        alert("비밀번호 변경이 완료되었습니다.");
        this.fnFlagInit();
      }
    });
  },

  fn_submitFrm() {
    var username = this.info.mId;
    var password = this.info.mPw;

    if (username != "" && password != "") {
      document.querySelector("form#login").submit();
    } else {
      alert("아이디와 비밀번호를 모두 입력해주세요.");
      return false;
    }
  },
};
