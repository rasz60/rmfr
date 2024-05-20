package com.project.rmfr.member.service;

import java.util.Map;

public interface MemberService {

    public String signupMember(Map<String, Object> param);
    public boolean usernameDuplicateChk(String username);
}
