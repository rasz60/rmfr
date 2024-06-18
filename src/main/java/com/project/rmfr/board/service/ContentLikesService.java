package com.project.rmfr.board.service;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.board.entity.ck.ContentLikesCK;
import com.project.rmfr.member.entity.Members;
import org.springframework.stereotype.Service;

@Service
public interface ContentLikesService {

    public String chngLikeFlag(String contentId, boolean flag, String mId);
    public int countByContentLikesCKAndContentType(String contentId, Members member, String type);
    public int countByContentLikesCKAndContentType(ContentLikesCK ck, String type);
    public int countByContentId(String contentId);
    public String saveLikes(ContentLikes like);
    public void deleteLikes(ContentLikes like);
}
