export default {
  usernameRules() {
    var rules = [];

    const nullchk = (v) => {
      if (!this.findInfo.pwFindFlag) return true;
      if (v) return true;
      return "아이디는 필수 입력사항입니다.";
    };
    rules.push(nullchk);

    const regchk = (v) => {
      var regExp = /^(?=.*[a-z0-9])[a-z0-9_-]{6,20}$/;
      var chk = regExp.test(v);

      if (!this.findInfo.pwFindFlag) return true;
      if (chk) return true;
      return "6~20자리의 영문소문자, 숫자, -, _ 조합으로 입력해주세요.";
    };
    rules.push(regchk);

    return rules;
  },
  emailRules() {
    const rules = [];

    const nullchk = (v) => {
      if (v) return true;
      this.findInfo.chkd = false;
      return "인증번호를 수신할 이메일 주소를 입력해주세요.";
    };
    rules.push(nullchk);

    const regchk = (v) => {
      var regExp =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      var chk = regExp.test(v);

      this.findInfo.chkd = chk;

      if (chk) return true;
      return "형식에 맞는 이메일 주소를 입력해주세요. (ex> emailId@domain.com)";
    };
    rules.push(regchk);

    return rules;
  },
  pwRules() {
    const rules = [];
    const nullchk = (v) => {
      if (v) return true;
      return "비밀번호는 필수 입력사항입니다.";
    };
    rules.push(nullchk);

    const regchk = (v) => {
      var regExp =
        /(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
      var chk = regExp.test(v);
      this.findInfo.npwRegChk = chk;
      if (chk) return true;
      return "8~16자리의 영문 소/대문자, 숫자, 특수문자($,`,~,!,@,$,!,%,*,#,^,?,&,,(,),-,_,=,+) 조합으로 입력해주세요.";
    };
    rules.push(regchk);

    return rules;
  },
  pwChkRules() {
    const rules = [];

    const nullchk = (v) => {
      if (v) return true;
      return "비빌번호는 필수 입력사항입니다.";
    };
    rules.push(nullchk);

    if (this.findInfo.nPwChkVal) {
      const chkval = (v) => {
        var chk = this.findInfo.nPw == v;
        this.findInfo.nPwChk = chk;
        if (chk) return true;
        return "비밀번호가 일치하지 않습니다.";
      };
      rules.push(chkval);
    }
    return rules;
  },
};
