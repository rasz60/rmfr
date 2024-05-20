package com.project.rmfr.api;

import com.project.rmfr.member.service.MemberService;
import com.project.rmfr.utils.MailUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {
    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MailUtils mailUtils;

    @GetMapping("/api/v1/usernameDupChk/{username}")
    public boolean usernameDupChk(@PathVariable("username") String username) {
        boolean chk = memberService.usernameDuplicateChk(username);
        return chk;
    }

    @GetMapping("/api/v1/emailValid/{emailAddress}")
    public Map<String, Object> emailValid(@PathVariable("emailAddress") String emailAddress) {

        Map<String, Object> mailResult = mailUtils.sendEmail(emailAddress);

        return mailResult;
    }


}
