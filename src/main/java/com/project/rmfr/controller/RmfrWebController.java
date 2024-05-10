package com.project.rmfr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RmfrWebController {

    @RequestMapping("/")
    public String rmfr() {
        System.out.println("controller");
        return "index.html";
    }
}
