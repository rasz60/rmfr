package com.project.rmfr.board.controller;

import com.project.rmfr.board.service.AllNoticeContentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;
@Controller
@RequestMapping("/api/board/")
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {

    private final AllNoticeContentsService allNoticeContentsService;
    @PostMapping("notice/item/create")
    @ResponseBody
    public String CreateNoticeItem(@RequestBody Map<String, Object> param, Principal principal) {
        param.put("userId", (principal == null ? "rassayz60" : principal.getName()));

        return "".equals(allNoticeContentsService.createItem(param)) ? "500" : "200";
    }
}
