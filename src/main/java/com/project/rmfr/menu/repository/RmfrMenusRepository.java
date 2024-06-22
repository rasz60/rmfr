package com.project.rmfr.menu.repository;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.menu.entity.RmfrMenus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RmfrMenusRepository  extends JpaRepository<RmfrMenus, Long>, JpaSpecificationExecutor<AllNoticeContents> {

    Long countBy();
}
