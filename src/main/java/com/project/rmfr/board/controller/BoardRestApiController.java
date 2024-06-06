package com.project.rmfr.board.controller;

import com.project.rmfr.board.dto.BoardItemDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.service.AllNoticeContentsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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
}
