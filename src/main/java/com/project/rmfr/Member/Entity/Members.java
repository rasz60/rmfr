package com.project.rmfr.Member.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
@Setter
@DynamicInsert
public class Members {
    // MEMBERS Entity 테이블
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(100)")// auto-generator를 사용하면 int, float 자료형만 사용 가능, uuid 형식은 binary(16) 사용해야함.
    private String mEntrId;

    @Column(columnDefinition = "VARCHAR(40)", unique = true, nullable=false)
    private String mId;

    @Column(columnDefinition = "VARCHAR(100)", nullable=false)
    private String mPw;

    @Column(columnDefinition = "VARCHAR(200)", nullable=false)
    private String mEmail;

    @Column(columnDefinition = "INT DEFAULT 1", nullable=false)
    private int mLevel;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable=false)
    private LocalDateTime mPwUpdateDate;

    @Column(columnDefinition = "VARCHAR(11)")
    private String mPhone;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String mAddr1;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String mAddr2;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String mAddr3;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String thum;
}
