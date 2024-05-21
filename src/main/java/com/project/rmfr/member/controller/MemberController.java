package com.project.rmfr.member.controller;

import com.project.rmfr.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("signup")
    public String signup() {
        return "/index";
    }
    @PostMapping("signup/submit")
    @ResponseBody
    public String signupSubmit(@RequestBody Map<String, Object> param) {
        return memberService.signupMember(param);
    }

    @GetMapping("settings")
    public String settings() {
        return "/index";
    }
}
