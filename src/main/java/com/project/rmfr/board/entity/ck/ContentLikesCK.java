package com.project.rmfr.board.entity.ck;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Data
public class ContentLikesCK implements Serializable {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ancUuid", name="contentId")
    private AllNoticeContents contentId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "mEntrId", name="contentLikerId")
    private Members contentLikerId;

    public ContentLikesCK() {}
}
