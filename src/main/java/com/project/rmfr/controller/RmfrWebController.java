package com.project.rmfr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RmfrWebController {

    @GetMapping("/")
    public String rmfr() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "index";
    }

}
