package com.project.rmfr.board.controller;

import com.project.rmfr.board.service.AllNoticeContentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String createNoticeItem(@RequestBody Map<String, Object> param, Principal principal) {
        param.put("userId", (principal == null ? "rassayz60" : principal.getName()));

        return "".equals(allNoticeContentsService.createItem(param)) ? "500" : "200";
    }

    @PostMapping("notice/item/update")
    @ResponseBody
    public String updateNoticeItem(@RequestBody Map<String, Object> param, Principal principal) {
        param.put("userId", (principal == null ? "rassayz60" : principal.getName()));

        return "".equals(allNoticeContentsService.updateItem(param)) ? "500" : "200";
    }

    @GetMapping("notice/item/delete/{ancUuid}")
    @ResponseBody
    public String updateNoticeItem(@PathVariable("ancUuid") String ancUuid, Principal principal) {
        return "".equals(allNoticeContentsService.deleteItem(ancUuid, principal.getName())) ? "500" : "200";
    }
}
