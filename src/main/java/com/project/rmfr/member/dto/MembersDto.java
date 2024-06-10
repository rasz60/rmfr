package com.project.rmfr.member.dto;

import com.project.rmfr.member.entity.Members;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MembersDto {
    private String mEntrId;
    private String mId;
    private String mEmail;
    private Integer mLevel;
    private LocalDateTime mPwUpdateDate;
    private String mPhone;
    private String mAddr1;
    private String mAddr2;
    private String mAddr3;
    private String thum;

    public static MembersDto of (Members member, int type) {
        MembersDto dto = null;

        if ( type == 0 ) {
            dto = new MembersDto(member.getMEntrId()
                                ,member.getMId()
                                ,member.getThum()
                                ,member.getMLevel()
                                ,member.getMPwUpdateDate()
            );
        } else {
            dto = new MembersDto(member.getMEntrId()
                              , member.getMId()
                              , member.getMEmail()
                              , member.getMLevel()
                              , member.getMPhone()
                              , member.getMAddr1()
                              , member.getMAddr2()
                              , member.getMAddr3()
                              , member.getThum()
                              ,member.getMPwUpdateDate()
            );
        }
        return dto;
    }

    public MembersDto() {}

    public MembersDto(String mEntrId, String mId, String thum, Integer mLevel, LocalDateTime mPwUpdateDate) {
        this.mEntrId = mEntrId;
        this.mId = mId;
        this.thum = thum;
        this.mLevel = mLevel;
        this.mPwUpdateDate = mPwUpdateDate;
    }

    public MembersDto(String mEntrId, String mId, String mEmail, Integer mLevel, String mPhone, String mAddr1, String mAddr2, String mAddr3, String thum, LocalDateTime mPwUpdateDate) {
        this.mEntrId = mEntrId;
        this.mId = mId;
        this.mEmail = mEmail;
        this.mLevel = mLevel;
        this.mPhone = mPhone;
        this.mAddr1 = mAddr1;
        this.mAddr2 = mAddr2;
        this.mAddr3 = mAddr3;
        this.thum = thum;
        this.mPwUpdateDate = mPwUpdateDate;
    }

}
