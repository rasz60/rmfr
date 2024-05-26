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

    @GetMapping("signin")
    public String signin() {
        return "/index";
    }

    @GetMapping("signup")
    public String signup() {
        return "/index";
    }

    @GetMapping("settings")
    public String settings() {
        return "/index";
    }

}
