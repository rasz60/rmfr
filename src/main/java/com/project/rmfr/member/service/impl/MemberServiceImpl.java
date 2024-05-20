package com.project.rmfr.member.service.impl;

import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.repository.MemberRepository;
import com.project.rmfr.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HttpSession httpSession;

    /*
     * @RequiredArgsConstructor : 초기화되지 않았거나 @NotNull인 필드의 생성자 생성
     * @Transactional : 내부 로직 수행 중 오류 발생 시 원복
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members member = null;

        try {
            Optional<Members> memberOptional = memberRepository.findBymId(username);

            if (memberOptional.isPresent()) {
                member = memberOptional.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    public boolean usernameDuplicateChk(String username) {
        Optional<Members> userOptional= memberRepository.findBymId(username);
        boolean chk = userOptional.isPresent();
        return chk;
    }

    public String signupMember(Map<String, Object> param) {
        String mEntrId = "";

        try {
            mEntrId = memberRepository.save(Members.builder()
                    .email((String) param.get("mEmail"))
                    // 패스워드 암호화
                    .password(bCryptPasswordEncoder.encode((String) param.get("password")))
                    .userName((String) param.get("username"))
                    .phone((String) param.get("mPhone"))
                    .mAddr1((String) param.get("mAddr1"))
                    .mAddr2((String) param.get("mAddr2"))
                    .mAddr3((String) param.get("mAddr3"))
                    .build()).getMEntrId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mEntrId;
    }
}
