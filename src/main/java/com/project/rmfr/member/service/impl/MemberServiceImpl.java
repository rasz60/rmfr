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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Members loadUser(String mId) {
        return (Members) loadUserByUsername(mId);
    }


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
                String info = "";
                if (param.containsKey("mEmail")) {
                    info += "mEmail";
                    member.setMEmail((String) param.get("mEmail"));
                }

                if (param.containsKey("password")) {
                    info += "".equals(info) ? "password" : ", password";
                    member.setMPw(bCryptPasswordEncoder.encode((String) param.get("password")));
                    member.setMPwUpdateDate(LocalDateTime.now());
                }

                if (param.containsKey("mPhone")) {
                    info += "".equals(info) ? "mPhone" : ", mPhone";
                    member.setMPhone((String) param.get("mPhone"));
                }

                if (param.containsKey("mAddr1")) {
                    info += "".equals(info) ? "mAddr1" : ", mAddr1";
                    member.setMAddr1((String) param.get("mAddr1"));
                }

                if (param.containsKey("mAddr2")) {
                    info += "".equals(info) ? "mAddr2" : ", mAddr2";
                    member.setMAddr2((String) param.get("mAddr2"));
                }

                if (param.containsKey("mAddr3")) {
                    info += "".equals(info) ? "mAddr3" : ", mAddr3";
                    member.setMAddr3((String) param.get("mAddr3"));
                }

                if (! "".equals(info) ) {
                    log.info("edit " + info);
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

    public List<String> mailChkExists(String mEmail) {
        List<String> members = new ArrayList<>();
        try {
            List<Members> memberList = memberRepository.findBymEmail(mEmail);
            for (Members m : memberList) {
                members.add(m.getMId());
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return members;
    }
}
