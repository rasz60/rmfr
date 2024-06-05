package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.repository.AllNoticeContentsRepository;
import com.project.rmfr.board.service.AllNoticeContentsService;
import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class AllNoticeContentsServiceImpl implements AllNoticeContentsService {

    private final MemberService memberService;
    private final AllNoticeContentsRepository allNoticeContentsRepository;
    @Override
    public String createItem(Map<String, Object> param) {
        String rst = "";

        try {

            ArrayList<String> ancKw = (ArrayList<String>) param.get("ancKw");
            String keywordStr = "";

            for ( String kw : ancKw ) {
                keywordStr += kw + "|";
            }

            String userId = (String) param.get("userId");

            AllNoticeContents anc = AllNoticeContents.builder()
                                                        .ancTitle((String)param.get("ancTitle"))
                                                        .ancKw(keywordStr)
                                                        .ancContents((String) param.get("ancContents"))
                                                        .member(memberService.loadUser(userId))
                                                        .build();
            allNoticeContentsRepository.save(anc);
            log.info(anc.getAncTitle());
            log.info(anc.getAncKw());
            log.info(anc.getAncContents());
            log.info(anc.getAncRegId().getMId());
            log.info(anc.getAncUpdaterId().getMId());

        } catch (Exception e) {
            log.error("createItem() throws exceptions.");
            e.printStackTrace();
        }

        return rst;
    }
}
