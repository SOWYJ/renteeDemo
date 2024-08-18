package com.example.userservices.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userservices.domain.Menu;
import com.example.userservices.service.MenuService;
import com.example.userservices.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wsk
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2024-08-18 19:46:14
*/
@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }
}




