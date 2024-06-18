package com.project.rmfr.board.controller;

import com.project.rmfr.board.dto.BoardItemDto;
import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.repository.ContentHitsRepository;
import com.project.rmfr.board.service.AllNoticeContentsService;
import com.project.rmfr.board.service.ContentCommentsService;
import com.project.rmfr.board.service.ContentHitsService;
import com.project.rmfr.board.service.ContentLikesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardRestApiController {

    private final AllNoticeContentsService allNoticeContentsService;
    private final ContentHitsService contentHitsService;
    private final ContentLikesService contentLikesService;
    private final ContentCommentsService contentCommentsService;
    @GetMapping({ "/rest/board/getItems/{page}"
                , "/rest/board/getItems/{page}/{sort}"
                , "/rest/board/getItems/{page}/{sType}/{sValue}"
                , "/rest/board/getItems/{page}/{sort}/{sType}/{sValue}"})

    public Page<BoardItemDto> getItems(@PathVariable("page") String page,
                                       @PathVariable(value = "sort", required = false) String sort,
                                       @PathVariable(value = "sType", required = false) String sType,
                                       @PathVariable(value = "sValue", required = false) String sValue) {

        Map<String, String> param = new HashMap<>();
        param.put("page",page);
        if ( sort != null ) {
            param.put("sortOrder", sort);
        }

        if ( sType != null && sValue != null ) {
            param.put("sType", sType);
            param.put("sValue", sValue);
        }
        return allNoticeContentsService.getItems(param);
    };

    @GetMapping("/rest/board/item/d/{itemId}")
    public BoardItemDto getItemDetails(@PathVariable("itemId") String itemId, Principal principal) {
        String mId = "";
        if ( principal != null ) {
            mId = principal.getName();
            contentHitsService.hitsUp(itemId, mId);
        } else {
            mId = "guest";
        }

        return allNoticeContentsService.getItemDetails(itemId, mId);
    };

    @GetMapping("/rest/board/item/likes/{ancUuid}/{flag}")
    public String chngLikeFlag(@PathVariable("ancUuid") String ancUuid, @PathVariable("flag") boolean flag, Principal principal) {
        return contentLikesService.chngLikeFlag(ancUuid, flag, principal.getName());
    }

    @PostMapping("/rest/board/item/regComment")
    public String regComment(@RequestBody Map<String, Object> param, Principal principal) {
        String rst = "";
        try {
            rst = contentCommentsService.regComment(param, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
            rst = "500";
        }
        return rst;
    }

    @GetMapping("/rest/board/item/delComment/{ancCommentUuid}")
    public String delComment(@PathVariable("ancCommentUuid") String ancCommentUuid) {
        return contentCommentsService.delComment(ancCommentUuid);
    }

    @GetMapping("/rest/board/item/likeComment/{ancCommentUuid}")
    public String likeComment(@PathVariable("ancCommentUuid") String ancCommentUuid, Principal principal) {
        return contentCommentsService.likeComment(ancCommentUuid, principal);
    }

}
