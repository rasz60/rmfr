package com.project.rmfr.member.service;

import com.project.rmfr.member.entity.Members;

import java.util.Map;

public interface MemberService {

    public String signupMember(Map<String, Object> param);
    public boolean usernameDuplicateChk(String username);

    public Members getSimpleMemberInfo(String username);

    public Members getDetailMemberInfo(String username);
}
