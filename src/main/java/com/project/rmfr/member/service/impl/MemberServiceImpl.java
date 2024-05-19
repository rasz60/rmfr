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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HttpSession httpSession;

    /*
     * @RequiredArgsConstructor : 초기화되지 않았거나 @NotNull인 필드의 생성자 생성
     * @Transactional : 내부 로직 수행 중 오류 발생 시 원복
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public boolean usernameDuplicateChk(String username) {
        Optional<Members> userOptional= userRepository.findBymId(username);

        boolean chk = userOptional.isPresent();
        log.info("usernameDuplicateChk("+username+") : " + chk);
        return chk;
    }
}
