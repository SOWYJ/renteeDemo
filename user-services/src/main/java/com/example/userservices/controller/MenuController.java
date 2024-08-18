package com.example.userservices.controller;


import com.example.userservices.domain.Menu;
import com.example.userservices.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author liwei
 * @Date 2024/7/11 11:06
 * @Version 1.0
 */
@RestController
@CrossOrigin
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/getAllMenu")
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }
}
