package com.example.userservices.service;

import com.example.userservices.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wsk
* @description 针对表【menu】的数据库操作Service
* @createDate 2024-08-18 19:46:14
*/
public interface MenuService{
    List<Menu> getAllMenu();
}
