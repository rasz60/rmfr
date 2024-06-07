package com.project.rmfr.member.service;

import com.project.rmfr.member.dto.MembersDto;
import com.project.rmfr.member.entity.Members;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface MemberService {
    public Members loadUser(String username);
    public String signupMember(Map<String, Object> param);
    public boolean usernameDuplicateChk(String username);

    public MembersDto getSimpleMemberInfo(String username);

    public MembersDto getDetailMemberInfo(String username);

    public boolean passwordChecked(String username, String password);

    public String updateMember(Map<String, Object> param);

    public boolean signout(String username);

    public List<String> mailChkExists(String mEmail);
}
