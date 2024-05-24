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

    @PostMapping("notice/item/create")
    public String CreateNoticeItem(@RequestParam("ancTitle") String ancTitle,
                                   @RequestParam(value = "ancContents", required = false) String ancContents,
                                   @RequestParam(value = "ancKw", required = false) String ancKw) {
        log.info("param1 : " + ancTitle);
        log.info("param2 : " + ancContents);
        log.info("param3 : " + ancKw);

        return "/index";
    }
}
