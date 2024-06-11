package com.project.rmfr.board.controller;

import com.project.rmfr.board.dto.BoardItemDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.service.AllNoticeContentsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardRestApiController {

    private final AllNoticeContentsService allNoticeContentsService;

    @GetMapping("/rest/board/getItems/{page}")
    public Page<BoardItemDto> getItems(@PathVariable("page") String page) {
        return allNoticeContentsService.getItems(page);
    };

    @GetMapping("/rest/board/item/d/{itemId}")
    public BoardItemDto getItemDetails(@PathVariable("itemId") String itemId, Principal principal) {
        return allNoticeContentsService.getItemDetails(itemId, principal == null ? "guest" : principal.getName());
    };

    @GetMapping("/rest/board/item/likes/{ancUuid}/{flag}")
    public String chngLikeFlag(@PathVariable("ancUuid") String ancUuid, @PathVariable("flag") boolean flag, Principal principal) {
        return allNoticeContentsService.chngLikeFlag(ancUuid, flag, principal.getName());
    }

    @PostMapping("/rest/board/item/regComment")
    public String regComment(@RequestBody Map<String, Object> param, Principal principal) {
        String rst = "";
        try {
            rst = allNoticeContentsService.regComment(param, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
            rst = "500";
        }
        return rst;
    }

    @GetMapping("/rest/board/item/delComment/{ancCommentUuid}")
    public String delComment(@PathVariable("ancCommentUuid") String ancCommentUuid) {
        return allNoticeContentsService.delComment(ancCommentUuid);
    }
}
