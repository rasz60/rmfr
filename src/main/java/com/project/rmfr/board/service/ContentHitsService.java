package com.project.rmfr.board.service;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ck.ContentHitsCK;
import com.project.rmfr.member.entity.Members;

public interface ContentHitsService {

    public void hitsUp(String itemId, String mId);
}
