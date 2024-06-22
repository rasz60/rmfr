package com.project.rmfr.menu.service;

import com.project.rmfr.menu.entity.RmfrMenus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

@Service
public interface RmfrMenusService {

    String createMenu(RmfrMenus menu, String mId);
}
