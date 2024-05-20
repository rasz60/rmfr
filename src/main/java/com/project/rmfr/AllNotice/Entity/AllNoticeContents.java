package com.project.rmfr.AllNotice.Entity;

import com.project.rmfr.Member.Entity.Members;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Clob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "allNoticeContents")
@Getter
@Setter
@DynamicInsert
public class AllNoticeContents {
    // Contents 테이블
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(100)")// auto-generator를 사용하면 int, float 자료형만 사용 가능, uuid 형식은 binary(16) 사용해야함.
    private String ancUuid;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "anbUuid")
    private AllNoticeBoard anbUuid;

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    private String ancTitle;

    @Column(columnDefinition = "LONGTEXT", nullable=false)
    private Clob ancContents;

    @Column(columnDefinition = "VARCHAR(300)")
    private String ancKw;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime ancRegDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mId" ,name = "ancRegId")
    private Members ancRegId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime ancUpdateDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mId" ,name = "ancUpdaterId")
    private Members ancUpdaterId;

    @Column(columnDefinition = "INT DEFAULT 1", nullable = false)
    private int ancState;

    @Column(columnDefinition = "INT DEFAULT 1", nullable = false)
    private int ancAuth;

    @OneToMany(mappedBy = "contentHitsCK.ancUuid")
    List<ContentHits> hits = new ArrayList<>();

    @OneToMany(mappedBy = "contentLikesCK.contentId")
    List<ContentLikes> likes = new ArrayList<>();
}
