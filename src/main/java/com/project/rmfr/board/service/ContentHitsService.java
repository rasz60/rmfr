package com.project.rmfr.board.service;

import org.springframework.stereotype.Service;

@Service
public interface ContentHitsService {

    public void hitsUp(String itemId, String mId);
}
