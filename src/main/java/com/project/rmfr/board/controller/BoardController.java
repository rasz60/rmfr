package com.project.rmfr.board.controller;

import com.project.rmfr.board.entity.AllNoticeContents;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    @GetMapping("notice")
    public String notice() {
        return "/index";
    }

    @GetMapping("notice/item/c")
    public String CreateNoticeForm() {
        return "/index";
    }

    @GetMapping("notice/item/d")
    public String DetailsNoticeForm() {
        return "/index";
    }
}
