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
};

export default data;
