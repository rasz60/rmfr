export default {
  async fn_loginChk() {
    // 로그인 실패 시, loginError=true로 return
    var url = location.href;
    var query = url.substring(url.indexOf("?"));
    var param = new URLSearchParams(query);

    if (param.size > 0 && param.has("loginError")) {
      alert("로그인 정보를 다시 확인해주세요.");
      this.fn_toggleInfo();
      document.querySelector("input#mPw").focus();
    } else {
      await this.axios.get("/rest/v1/loginchk").then((res) => {
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
    console.log("fn_toggleMenu : " + this.$menuDrawer.value);
    this.$menuDrawer.value = !this.$menuDrawer.value;
    console.log("fn_toggleMenu : " + this.$menuDrawer.value);
  },

  fn_toggleInfo() {
    var info = document.querySelector("div#loginInfo");
    var curr = info.style.display;
    var chk = curr == "" || curr == "none";

    if (chk) {
      info.style.display = "flex";
    } else {
      info.style.display = "none";
      document.querySelector("input#mId").value = "";
      document.querySelector("input#mPw").value = "";
    }
  },
  fnFlagInit() {
    this.loginDrawer = !this.loginDrawer;
    this.findInfo.pwVisible = false;
    this.findInfo.findFlag = false;
    this.findInfo.cert = false;
    this.findInfo.certDone = false;
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
    let chk = await this.$refs.findForm.validate();

    if (chk.valid) {
      this.fnFindInfoCert();
    }
  },
  fnFindInfoCert() {
    var email = this.findInfo.mEmail;

    this.fnEmailChkExists(email);
    console.log("find : " + email);
  },
  async fnEmailChkExists(m) {
    await this.axios.get("rest/v1/mailChkExists/" + m).then((res) => {
      const jsonData = res.data;
      if (jsonData.length > 0) {
        this.findInfo.mIdList = jsonData;
        this.sendValidCode();
      } else {
        alert("해당 메일주소로 가입된 아이디가 없습니다.");
        return false;
      }
    });
  },

  // 인증번호 발송
  async sendValidCode() {
    // 메일 주소
    var mailAddress = this.findInfo.mEmail;

    // 인증번호 발송 api 호출
    await this.axios
      .get("/rest/v1/emailValid/" + mailAddress + "/c")
      .then((res) => {
        const jsonData = res.data;
        this.findInfo.validCode = jsonData.token; // base64 encoding된 인증번호

        if (this.validCodeTime != null) {
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
  },

  // 인증번호 확인
  validCodeChk(v) {
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
