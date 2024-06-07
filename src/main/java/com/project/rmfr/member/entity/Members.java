package com.project.rmfr.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "members")
@Data
@DynamicInsert
public class Members implements UserDetails {
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

    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer mLevel;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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

    public Members() {}

    @Builder
    public Members(String email, String userName, String password, String phone, String mAddr1, String mAddr2, String mAddr3) {
        this.mEmail = email;
        this.mId = userName;
        this.mPw = password;
        this.mPhone = phone;
        this.mAddr1 = mAddr1;
        this.mAddr2 = mAddr2;
        this.mAddr3 = mAddr3;
    }



    /* security methods */
    // 권한 조회
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return mPw;
    }


    @Override
    public String getUsername() {
        return mId;
    }

    // 계정 만료 여부 조회
    @Override
    public boolean isAccountNonExpired() {
        return true; // true : 만료되지 않음, false : 만료
    }

    // 계정 잠금 여부 조회
    @Override
    public boolean isAccountNonLocked() {
        return true; // true : 잠금되지 않음, false : 잠금
    }

    // 패스워드 만료 여부 조회
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true : 만료되지 않음, false : 만료
    }

    // 계정 사용 가능 여부 조회
    @Override
    public boolean isEnabled() {
        return true; // true : 사용 가능, false : 사용 불가
    }
}
