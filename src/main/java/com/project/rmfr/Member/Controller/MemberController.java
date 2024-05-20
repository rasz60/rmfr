package com.project.rmfr.Member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MemberController {

    @GetMapping("/")
    public String rmfr() {
        System.out.println("안녕!!!");
        return "index";
    }

}
