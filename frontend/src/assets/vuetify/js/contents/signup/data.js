const data = {
  signupInfo: {
    username: {
      value: "",
      name: "아이디",
      required: true,
      chkd: false,
      dupchk: false,
    },
    password: {
      value: "",
      name: "비밀번호",
      required: true,
      chkd: false,
    },
    email: {
      value: "",
      name: "이메일",
      required: true,
      chkd: false,
    },
    phoneNumber: {
      value: "",
      name: "휴대폰",
      required: false,
    },
    zipCode: {
      value: "",
      name: "우편번호",
      required: false,
    },
    addr1: {
      value: "",
      name: "주소",
      required: false,
    },
    addr2: {
      value: "",
      name: "상세주소",
      required: false,
    },
  },
  validCode: "",
  usernameRules: [
    (value) => {
      if (value) return true;
      return "아이디는 필수 입력사항입니다.";
    },
    (value) => this.fnUsernameVaild(value),
  ],

  pwRules: [
    (value) => {
      if (value) return true;
      return "비밀번호는 필수 입력사항입니다.";
    },
    (value) => this.fnUsernameVaild(value),
  ],

  pwChkRules: [
    (value) => {
      if (value) return true;
      return "비밀번호를 확인해주세요.";
    },
  ],
};

export default data;
