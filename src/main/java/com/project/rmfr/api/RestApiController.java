package com.project.rmfr.api;

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
public class RestApiController {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MailUtils mailUtils;


    @GetMapping("/rest/v1/usernameDupChk/{username}")
    public boolean usernameDupChk(@PathVariable("username") String username) {
        return memberService.usernameDuplicateChk(username);
    }

    @GetMapping("/rest/v1/emailValid/{emailAddress}/{type}")
    public Map<String, Object> emailValid(@PathVariable("emailAddress") String emailAddress, @PathVariable("type") String type) {
        return mailUtils.sendEmail(emailAddress, type);
    }

    @GetMapping("/rest/v1/loginchk")
    public Map<String, Members> loginChk(Principal principal) {
        Map<String, Members> loginInfo = new HashMap<>();
        try {
            String mId = principal.getName();
            loginInfo.put("info", memberService.getSimpleMemberInfo(mId));
        } catch (Exception e) {
            log.info("principal is null.");
            loginInfo.put("info", null);
        }
        return loginInfo;
    }

    @GetMapping("/rest/v1/loginUserDetails")
    public Map<String, Members> loginUserDetails(Principal principal) {
        Map<String, Members> loginInfo = new HashMap<>();
        try {
            String mId = principal.getName();
            loginInfo.put("info", memberService.getDetailMemberInfo(mId));
        } catch (Exception e) {
            log.info("principal is null.");
            loginInfo.put("info", null);
        }
        return loginInfo;
    }
    @GetMapping("/rest/v1/pwChk/{password}")
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
    @GetMapping("/rest/v1/mailChkExists/{mEmail}")
    public List<String> mailChkExists(@PathVariable("mEmail") String mEmail) {
        List<String> chk = new ArrayList<>();
        try {
            chk = memberService.mailChkExists(mEmail);
        } catch (Exception e) {
            log.info("principal is null");
        }
        return chk;
    }
}
