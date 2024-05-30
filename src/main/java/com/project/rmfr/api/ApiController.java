package com.project.rmfr.api;

import com.project.rmfr.member.service.MemberService;
import com.project.rmfr.utils.MailUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ApiController {
    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MailUtils mailUtils;

    /*
    * 회원가입 처리 로직 
    */
    @PostMapping("signup/submit")
    @ResponseBody
    public String signupSubmit(@RequestBody Map<String, Object> param) {
        return memberService.signupMember(param);
    }
    /*
     * 회원 정보 수정 로직
     */
    @PostMapping("settings/update")
    @ResponseBody
    public String updateInfo(@RequestBody Map<String, Object> param) {
        return memberService.updateMember(param);
    }
    /*
    * 회원 탈퇴 로직
    */
    @GetMapping("signout/{username}")
    @ResponseBody
    public String signout(@PathVariable("username") String username) {
        return memberService.signout(username) ? "200" : "500";
    }
}
