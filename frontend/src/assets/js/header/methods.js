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
    var slim = document.querySelector("div#menuSlim");
    var full = document.querySelector("div#menuFull");
    var contents = document.querySelector("div#content");

    if (slim.style.display == "none" || slim.style.display == "") {
      full.style.display = "none";
      slim.style.display = "block";
      contents.style.width = "96%";
    } else {
      slim.style.display = "none";
      full.style.display = "block";
      contents.style.width = "85%";
    }
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

  fn_submitFrm() {
    var username = document.querySelector("input#mId").value;
    var password = document.querySelector("input#mPw").value;

    if (username != "" && password != "") {
      document.querySelector("form#login").submit();
    } else {
      alert("아이디와 비밀번호를 모두 입력해주세요.");
      return false;
    }
  },
};
