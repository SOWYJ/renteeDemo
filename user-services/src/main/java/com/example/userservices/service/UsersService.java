package com.example.userservices.service;

import com.example.userservices.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.userservices.dto.LoginDto;

/**
* @author wsk
* @description 针对表【users】的数据库操作Service
* @createDate 2024-08-28 14:50:22
*/
public interface UsersService{

    Integer getUserName(String userName);
    String getPassword(String password);

}
