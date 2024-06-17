export default {
  pwRules() {
    const rules = [];

    const regchk = (v) => {
      var regExp =
        /(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
      var chk = regExp.test(v);
      this.details.password.regChkd = chk;
      if (chk) return true;
      return "8~16자리의 영문 소/대문자, 숫자, 특수문자($,`,~,!,@,$,!,%,*,#,^,?,&,,(,),-,_,=,+) 조합으로 입력해주세요.";
    };
    rules.push(regchk);

    return rules;
  },
  pwChkVal() {
    const rules = [];

    if (this.details.password.chkval) {
      const chkval = (v) => {
        var chk = this.details.password.value == v;
        this.details.password.chkd = chk;
        if (chk) return true;
        return "비밀번호가 일치하지 않습니다.";
      };
      rules.push(chkval);
    }
    return rules;
  },
  pnChk() {
    const rules = [];

    const regchk = (v) => {
      var regExp = /[0-9]$/;
      var chk = regExp.test(v);

      if (chk) return true;
      else if (v == null || v == "") return true;
      return "숫자로만 입력해주세요.";
    };
    rules.push(regchk);

    const lengthChk = (v) => {
      if (v.length >= 3 && v.length <= 4) return true;
      else if (v == null || v == "") return true;
      return "입력한 휴대폰 번호의 자리 수를 확인해주세요.";
    };
    rules.push(lengthChk);

    return rules;
  },
  emailRules() {
    const rules = [];

    const regchk = (v) => {
      var regExp =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      var chk = regExp.test(v);

      this.details.email.chkd = chk;

      if (chk) return true;
      return "형식에 맞는 이메일 주소를 입력해주세요. (ex> emailId@domain.com)";
    };
    rules.push(regchk);

    return rules;
  },
};
