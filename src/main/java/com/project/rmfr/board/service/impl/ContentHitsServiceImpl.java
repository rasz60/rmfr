package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentHits;
import com.project.rmfr.board.entity.ck.ContentHitsCK;
import com.project.rmfr.board.repository.ContentHitsRepository;
import com.project.rmfr.board.service.AllNoticeContentsService;
import com.project.rmfr.board.service.ContentHitsService;
import com.project.rmfr.board.spec.BoardSpecification;
import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentHitsServiceImpl implements ContentHitsService {
    private final ContentHitsRepository contentHitsRepository;
    private final MemberService memberService;
    private final AllNoticeContentsService allNoticeContentsService;
    @Override
    public void hitsUp(String itemId, String mId) {
        try {
            AllNoticeContents anc = allNoticeContentsService.findByAncUuid(itemId);

            if ( anc != null ) {
                Members register = anc.getAncRegId();

                if ( mId.equals(register.getMId()) ) {
                    Members loginUser = memberService.loadUser(mId);
                    ContentHitsCK ck = new ContentHitsCK(anc, loginUser);

                    Long count = contentHitsRepository.countByContentHitsCK(ck);

                    if ( count.intValue() == 0 ) {
                        ContentHits hit = ContentHits.builder()
                                .anc(ck.getAncUuid())
                                .hits(ck.getAncHitsId())
                                .build();
                        log.info(!"".equals(contentHitsRepository.save(hit).getContentHitsCK().getAncUuid().getAncUuid()) ? "200" : "500");
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
