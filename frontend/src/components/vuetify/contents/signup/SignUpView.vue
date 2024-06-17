<script setup>
import signupDatas from "@v-js/contents/signup/signupDatas.js";
import signupMethods from "@v-js/contents/signup/signupMethods.js";
import signupRules from "@v-js/contents/signup/signupRules.js";
</script>

<template>
  <v-sheet id="signupBox">
    <v-form @submit.prevent id="vuetify_signup_frm" ref="form">
      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            id="mId"
            v-model="signupInfo.username.value"
            label="아이디(ID)"
            :rules="usernameRules"
            variant="underlined"
            required
            prepend-inner-icon="fas fa-asterisk"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol">
          <v-btn
            @click="fnUsernameDupChk()"
            :text="signupInfo.username.dupchk ? '확인완료' : '중복확인'"
          ></v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="password"
            label="비밀번호(Password)"
            :rules="pwRules"
            variant="underlined"
            required
            prepend-inner-icon="fas fa-asterisk"
            v-model="signupInfo.password.value"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol"></v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="password"
            label="비밀번호 확인(Password Check)"
            :rules="pwChkVal"
            variant="underlined"
            required
            prepend-inner-icon="fas fa-asterisk"
            v-model="signupInfo.password.chkval"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol"></v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="email"
            label="이메일(email)"
            :rules="emailRules"
            variant="underlined"
            required
            prepend-inner-icon="fas fa-asterisk"
            v-model="signupInfo.email.value"
            :readonly="signupInfo.email.certDone"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol">
          <v-btn
            :readonly="signupInfo.email.certDone"
            @click="fnEmailCert"
            :text="
              signupInfo.email.certDone
                ? '인증완료'
                : signupInfo.email.cert
                  ? '재발송'
                  : '인증하기'
            "
          ></v-btn>
        </v-col>
      </v-row>

      <v-row v-show="signupInfo.email.cert">
        <v-col cols="11" class="col">
          <v-text-field
            type="text"
            label="인증번호 확인"
            @keyup="validCodeChk($event.target.value)"
            variant="underlined"
            required
            prepend-inner-icon="fas fa-asterisk"
            :readonly="signupInfo.email.certDone || !signupInfo.email.cert"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol" v-show="signupInfo.email.cert">
          <span id="validCodeTimer"></span>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="3" class="col">
          <v-select
            :items="['------', '010', '011', '016', '017', '018', '019']"
            variant="underlined"
            label="휴대폰번호(Phone Number)"
            v-model="signupInfo.phoneNumber.head"
          ></v-select>
        </v-col>
        <v-col cols="1" class="col">
          <v-icon icon="fas fa-minus" />
        </v-col>
        <v-col cols="3" class="col">
          <v-text-field
            type="text"
            :rules="pnChk"
            variant="underlined"
            v-model="signupInfo.phoneNumber.mid"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col">
          <v-icon icon="fas fa-minus" />
        </v-col>
        <v-col cols="3" class="col">
          <v-text-field
            type="text"
            :rules="pnChk"
            variant="underlined"
            v-model="signupInfo.phoneNumber.last"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol"></v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="text"
            name="zipcode"
            label="우편번호(Zip Code)"
            variant="underlined"
            readonly
            @keyup="fnAlert()"
            v-model="signupInfo.zipCode.value"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol">
          <v-btn text="검색하기" @click.stop="execDaumPostcode"></v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="text"
            name="addr1"
            label="주소(Address)"
            variant="underlined"
            readonly
            @keyup="fnAlert()"
            v-model="signupInfo.addr1.value"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol">
          <v-btn text="초기화" @click="fnAddrReset"></v-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="11" class="col">
          <v-text-field
            type="text"
            label="상세주소(Details)"
            variant="underlined"
            v-model="signupInfo.addr2.value"
            :readonly="signupInfo.addr1.value == ''"
          ></v-text-field>
        </v-col>
        <v-col cols="1" class="col btnCol"> </v-col>
      </v-row>

      <v-btn @click="validate" color="primary">회원가입</v-btn>
    </v-form>
  </v-sheet>
</template>

<script>
export default {
  name: "signupView",
  data() {
    return signupDatas;
  },
  computed: signupRules,
  methods: signupMethods,

  mounted() {
    this.loadDaumPostcodeScript();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@v-css/contents/signup/signup.css";
</style>
