package com.project.rmfr.board.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AllNoticeContentsService {

    public String createItem(Map<String, Object> param);
}
