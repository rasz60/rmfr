package com.project.rmfr.board.entity.ck;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class ContentHitsCK implements Serializable {
/*
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "anbUuid", name="anbUuid")
    private AllNoticeBoard anbUuid;
*/
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ancUuid", name="ancUuid")
    private AllNoticeContents ancUuid;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "mEntrId", name="ancHitsId")
    private Members ancHitsId;

    public ContentHitsCK() {}
}
