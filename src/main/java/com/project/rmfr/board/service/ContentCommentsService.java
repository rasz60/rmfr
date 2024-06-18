package com.project.rmfr.board.service;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

@Service
public interface ContentCommentsService {

    public String regComment(Map<String, Object> param, String mId);
    public String delComment(String ancCommentUuid);
    public String likeComment(String ancCommentUuid, Principal principal);
}
