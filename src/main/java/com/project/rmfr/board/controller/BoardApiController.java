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
        String rst = "";
        try {
            if ( principal != null ) {
                rst = allNoticeContentsService.createItem(param, principal.getName());
            } else {
                rst = "500";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    @PostMapping("notice/item/update")
    @ResponseBody
    public String updateNoticeItem(@RequestBody Map<String, Object> param, Principal principal) {
        param.put("userId", (principal == null ? "rassayz60" : principal.getName()));

        return "".equals(allNoticeContentsService.updateItem(param)) ? "500" : "200";
    }

    @GetMapping("notice/item/delete/{ancUuid}")
    @ResponseBody
    public String deleteNoticeItem(@PathVariable("ancUuid") String ancUuid, Principal principal) {
        String rst = "";
        try {
            if ( principal != null ) {
                String mId = principal.getName();
                rst = allNoticeContentsService.deleteItem(ancUuid, principal.getName());
            } else {
                rst = "500";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }
}
