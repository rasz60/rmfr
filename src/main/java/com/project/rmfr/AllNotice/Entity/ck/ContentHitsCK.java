package com.project.rmfr.AllNotice.Entity.ck;

import com.project.rmfr.AllNotice.Entity.AllNoticeBoard;
import com.project.rmfr.AllNotice.Entity.AllNoticeContents;
import com.project.rmfr.Member.Entity.Members;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
public class ContentHitsCK implements Serializable {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "anbUuid", name="anbUuid")
    private AllNoticeBoard anbUuid;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "ancUuid", name="ancUuid")
    private AllNoticeContents ancUuid;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "mId", name="ancHitsId")
    private Members ancHitsId;
}
