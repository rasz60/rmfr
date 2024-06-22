package com.project.rmfr.menu.controller;

import com.project.rmfr.menu.entity.RmfrMenus;
import com.project.rmfr.menu.service.RmfrMenusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MenuRestApiController {

    private final RmfrMenusService rmfrMenusService;
    @PostMapping("/rest/admin/menu/update")
    public String updateMenus(@RequestBody Map<String, Object> param, Principal principal) {
        String rst = "";
        try {
            System.out.println(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @PostMapping("/rest/admin/menu/create")
    public String createMenus(@RequestBody RmfrMenus menu, Principal principal) {
        String rst = "";
        try {
            System.out.println(menu);
            if ( principal != null ) {
                rst = rmfrMenusService.createMenu(menu, principal.getName());
            } else {
                rst = "500";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }
}
