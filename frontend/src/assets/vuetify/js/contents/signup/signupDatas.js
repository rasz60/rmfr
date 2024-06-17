const data = {
  popup: null,
  signupInfo: {
    username: {
      value: "",
      name: "아이디",
      lastdupchk: "",
      chkd: false,
      dupchk: false,
    },
    password: {
      value: "",
      chkval: "",
      name: "비밀번호",
      regChkd: false,
      pwChkd: false,
    },
    email: {
      value: "",
      lastChkVal: "",
      lastCertVal: "",
      name: "이메일",
      chkd: false,
      cert: false,
      certDone: false,
    },
    phoneNumber: {
      head: "",
      mid: "",
      last: "",
      full: "",
      name: "휴대폰",
    },
    zipCode: {
      value: "",
      name: "우편번호",
    },
    addr1: {
      value: "",
      name: "주소",
    },
    addr2: {
      value: "",
      name: "상세주소",
    },
  },
  validCode: "",
  validCodeTime: null,
  validCodeTimer: "00:00",
  loading: false,
};

export default data;
