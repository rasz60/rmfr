package com.project.rmfr.menu.service.impl;

import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import com.project.rmfr.menu.entity.RmfrMenus;
import com.project.rmfr.menu.repository.RmfrMenusRepository;
import com.project.rmfr.menu.service.RmfrMenusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RmfrMenusServiceImpl implements RmfrMenusService {

    private final RmfrMenusRepository rmfrMenusRepository;
    private final MemberService memberService;

    public String createMenu(RmfrMenus menu, String mId) {
        String rst = "";
        try {
            int cnt = rmfrMenusRepository.countBy().intValue();
            Members member = memberService.loadUser(mId);

            menu.setSortOrder(cnt);
            menu.setMenuRegId(member);
            menu.setMenuUpdaterId(member);
            System.out.println(menu.getMenuIcon());
            rst = rmfrMenusRepository.save(menu).getRmfrMenuUuid();

            rst = "".equals(rst) ? "500" : "200";
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rst;
    };
}
