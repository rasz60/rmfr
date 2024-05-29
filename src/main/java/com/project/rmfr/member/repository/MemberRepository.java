package com.project.rmfr.member.repository;

import com.project.rmfr.member.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {

    /*
     * JpaRepository를 상속받아 CRUD 메서드 사용
     * save(), find(), update(), delete()
     */

    Optional<Members> findBymId(String userName);

    List<Members> findBymEmail(String mEmail);
}
