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

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
            log.info("loadUserByUsername throw exception.");
        }

        return member;
    }

    public boolean usernameDuplicateChk(String username) {
        Optional<Members> userOptional= memberRepository.findBymId(username);

        return userOptional.isPresent();
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
            log.info("signupMember throw exception.");
            e.printStackTrace();
        }

        return mEntrId;
    }


    @Override
    public Members getSimpleMemberInfo(String username) {
        Members member = new Members();

        try {
            Optional<Members> memberOptional = memberRepository.findBymId(username);

            if (memberOptional.isPresent()) {
                Members tmpMember = memberOptional.get();

                member.setMId(tmpMember.getMId());
                member.setThum(tmpMember.getThum());
                member.setMLevel(tmpMember.getMLevel());
                member.setMPwUpdateDate(tmpMember.getMPwUpdateDate());
            }
        } catch (Exception e) {
            log.info("getMemberByUsername Throw Exception.");
            e.printStackTrace();
        }

        return member;
    }

    public Members getDetailMemberInfo(String username) {
        Members member = new Members();

        try {
            Optional<Members> memberOptional = memberRepository.findBymId(username);

            if (memberOptional.isPresent()) {
                Members tmpMember = memberOptional.get();

                member.setMId(tmpMember.getMId());
                member.setMEmail(tmpMember.getMEmail());
                member.setMPhone(tmpMember.getMPhone());
                member.setMAddr1(tmpMember.getMAddr1());
                member.setMAddr2(tmpMember.getMAddr2());
                member.setMAddr3(tmpMember.getMAddr3());
                member.setThum(tmpMember.getThum());

            }
        } catch (Exception e) {
            log.info("getMemberByUsername Throw Exception.");
            e.printStackTrace();
        }

        return member;
    }

    public boolean passwordChecked(String username, String password) {
        boolean chk = false;

        try {
            Optional<Members> memberOptional = memberRepository.findBymId(username);

            if (memberOptional.isPresent()) {
                Members member = memberOptional.get();

                log.info(password + "==" + member.getPassword() + " => " + bCryptPasswordEncoder.matches(password, member.getPassword()));

                chk = bCryptPasswordEncoder.matches(password, member.getPassword());
            }
        } catch (Exception e) {
            log.info("passwordChecked Throw Exception.");
            e.printStackTrace();
        }

        return chk;
    }

    public String updateMember(Map<String, Object> param) {
        String rst = "";

        try {
            Optional<Members> memberOptional = memberRepository.findBymId((String) param.get("username"));
            if (memberOptional.isPresent()) {
                Members member = memberOptional.get();

                if (param.containsKey("email")) {
                    log.info("edit email.");
                    member.setMEmail((String) param.get("email"));
                }

                if (param.containsKey("password")) {
                    log.info("edit password.");
                    member.setMPw(bCryptPasswordEncoder.encode((String) param.get("password")));
                    member.setMPwUpdateDate(LocalDateTime.now());
                }

                if (param.containsKey("phoneNumber")) {
                    log.info("edit phoneNumber.");
                    member.setMPhone((String) param.get("phoneNumber"));
                }

                if (param.containsKey("zipCode")) {
                    log.info("edit zipCode.");
                    member.setMAddr1((String) param.get("zipCode"));
                }

                if (param.containsKey("addr1")) {
                    log.info("edit addr1.");
                    member.setMAddr2((String) param.get("addr1"));
                }

                if (param.containsKey("addr2")) {
                    log.info("edit addr2.");
                    member.setMAddr3((String) param.get("addr2"));
                }

                rst = memberRepository.save(member).getMPwUpdateDate().toString();
            }
        } catch (Exception e) {
            log.info("updateMember throw exception.");
            e.printStackTrace();
        }

        return rst;
    }

    public boolean signout(String username) {
        Optional<Members> memberOptional = memberRepository.findBymId(username);
        boolean chk = memberOptional.isPresent();
        if (chk) {
            Members member = memberOptional.get();
            memberRepository.delete(member);
        }
        return this.usernameDuplicateChk(username);
    }
}
