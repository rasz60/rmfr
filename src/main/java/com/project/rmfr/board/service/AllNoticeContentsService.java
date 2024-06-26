package com.project.rmfr.board.service;

import com.project.rmfr.board.dto.BoardItemDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.List;
@Service
public interface AllNoticeContentsService {

    public String createItem(Map<String, Object> param, String mId);
    public Page<BoardItemDto> getItems(Map<String, String> param);
    public BoardItemDto getItemDetails(String itemId, String mId);
    public String updateItem(Map<String, Object> param);
    public String deleteItem(String ancUuid, String mId);
    public AllNoticeContents findByAncUuid(String ancUuid);
}
