package com.project.rmfr.member.controller;

import com.project.rmfr.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PostMapping("settings/update")
    @ResponseBody
    public String updateInfo(@RequestBody Map<String, Object> param) {
        String rst = memberService.updateMember(param);
        return rst;
    }

    @GetMapping("signout/{username}")
    @ResponseBody
    public String signout(@PathVariable("username") String username) {
        boolean chk = memberService.signout(username);

        return chk ? "200" : "500";
    }
}
