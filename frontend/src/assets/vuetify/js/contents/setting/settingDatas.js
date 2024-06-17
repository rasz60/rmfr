const data = {
  cert: false,
  details: {
    username: {
      value: "",
      name: "아이디",
    },
    password: {
      value: "",
      chkval: "",
      name: "비밀번호",
      chngFlag: false,
      chkd: false,
    },
    email: {
      bValue: "",
      eValue: "",
      name: "이메일",
      chngFlag: false,
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
  cPwChk: false,
};

export default data;
