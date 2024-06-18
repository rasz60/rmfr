package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.board.entity.ck.ContentLikesCK;
import com.project.rmfr.board.repository.AllNoticeContentsRepository;
import com.project.rmfr.board.repository.ContentLikesCustomRepository;
import com.project.rmfr.board.repository.ContentLikesRepository;
import com.project.rmfr.board.service.ContentLikesService;
import com.project.rmfr.board.spec.BoardSpecification;
import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentLikesServiceImpl implements ContentLikesService {

    private final MemberService memberService;
    private final ContentLikesRepository contentLikesRepository;
    private final ContentLikesCustomRepository contentLikesCustomRepository;
    @Override
    public String chngLikeFlag(String contentId, boolean flag, String mId) {
        String rst = "";
        try {
            ContentLikes like = ContentLikes.builder()
                    .contentId(contentId)
                    .likeId(memberService.loadUser(mId))
                    .contentType("ANC")
                    .build();
            if (flag) {
                rst = "ANC".equals(contentLikesRepository.save(like).getContentType()) ? "200" : "500";
            } else {
                contentLikesRepository.delete(like);
                rst = "200";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rst = "500";
        }

        return rst;
    }

    @Override
    public int countByContentLikesCKAndContentType(String contentId, Members member, String type) {
        int rst = 0;
        try {
            ContentLikesCK ck = new ContentLikesCK(contentId, member);
            Long cnt = contentLikesRepository.countByContentLikesCKAndContentType(ck, type);
            rst = cnt.intValue();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @Override
    public int countByContentLikesCKAndContentType(ContentLikesCK ck, String type) {
        int rst = 0;
        try {
            Long cnt = contentLikesRepository.countByContentLikesCKAndContentType(ck, type);
            rst = cnt.intValue();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @Override
    public int countByContentId(String contentId) {
        int rst = 0;
        try {
            rst = contentLikesCustomRepository.countByContentId(contentId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rst;
    }
    @Override
    public String saveLikes(ContentLikes like) {
        String rst = "";
        try {
            rst = contentLikesRepository.save(like).getContentType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }
    @Override
    public void deleteLikes(ContentLikes like) {
        try {
            contentLikesRepository.delete(like);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
