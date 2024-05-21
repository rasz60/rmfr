package com.project.rmfr.api;

import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import com.project.rmfr.utils.MailUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
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

    @GetMapping("/rest/v1/emailValid/{emailAddress}")
    public Map<String, Object> emailValid(@PathVariable("emailAddress") String emailAddress) {
        return mailUtils.sendEmail(emailAddress);
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

}
