package com.project.rmfr.member.controller;

import com.project.rmfr.member.dto.MembersDto;
import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import com.project.rmfr.utils.MailUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberRestApiController {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MailUtils mailUtils;

    /*
    * 아이디 중복 체크
    */
    @GetMapping("/rest/member/usernameDupChk/{username}")
    public boolean usernameDupChk(@PathVariable("username") String username) {
        return memberService.usernameDuplicateChk(username);
    }

    /*
     * 이메일 인증
     * type { s : 회원 가입(signup), c : 단순 인증(cert) }
     */
    @GetMapping("/rest/member/emailValid/{emailAddress}/{type}")
    public Map<String, Object> emailValid(@PathVariable("emailAddress") String emailAddress, @PathVariable("type") String type) {
        return mailUtils.sendEmail(emailAddress, type);
    }
    /*
     * 로그인 여부 확인
     * 1. principal에 저장된 userid get
     * 2. userid로 간단한 유저 정보 return
     */
    @GetMapping("/rest/member/loginchk")
    public Map<String, MembersDto> loginChk(Principal principal) {
        Map<String, MembersDto> loginInfo = new HashMap<>();
        try {
            String mId = principal.getName();
            loginInfo.put("info", memberService.getSimpleMemberInfo(mId));
        } catch (Exception e) {
            log.info("principal is null.");
            loginInfo.put("info", null);
        }
        return loginInfo;
    }
    /*
     * 회원 정보 상세(회원 정보 수정 화면)
     */
    @GetMapping("/rest/member/loginUserDetails")
    public Map<String, MembersDto> loginUserDetails(Principal principal) {
        Map<String, MembersDto> loginInfo = new HashMap<>();
        try {
            String mId = principal.getName();
            loginInfo.put("info", memberService.getDetailMemberInfo(mId));
        } catch (Exception e) {
            log.info("principal is null.");
            loginInfo.put("info", null);
        }
        return loginInfo;
    }
    /*
    * 회원 정보 수정 시 현재 비밀번호 인증
    */
    @GetMapping("/rest/member/pwChk/{password}")
    public boolean pwChk(@PathVariable("password") String password, Principal principal) {
        boolean chk = false;
        try {
            String mId = principal.getName();
            chk = memberService.passwordChecked(mId, password);
        } catch (Exception e) {
            log.info("principal is null");
        }
        return chk;
    }
    /*
    * 가입된 이메일 주소인지 체크
    */
    @GetMapping("/rest/member/mailChkExists/{mEmail}/{mId}")
    public List<String> mailChkExists(@PathVariable("mEmail") String mEmail, @PathVariable(value = "mId", required = false) String mId) {
        List<String> chk = new ArrayList<>();
        try {
            if ( "0".equals(mId) ) {
                chk = memberService.mailChkExists(mEmail);
            } else {
                Members member = memberService.getDetailMemberInfo(mId);
                if ( member.getMId() == null ) {
                    chk.add("501");
                } else {
                    String rst = mEmail.equals(member.getMEmail()) ? "200" : "502";
                    chk.add(rst);
                }
            }
        } catch (Exception e) {
            log.info("principal is null");
        }
        return chk;
    }
}
