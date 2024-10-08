package com.example.userservices.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userservices.domain.Users;
import com.example.userservices.dto.LoginDto;
import com.example.userservices.service.UsersService;
import com.example.userservices.mapper.UsersMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author wsk
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-08-28 14:50:22
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper,Users>
        implements UsersService{

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Integer getUserName(String userName) {
        return usersMapper.getUserName(userName);
    }

    @Override
    public String getPassword(String userName) {
        return usersMapper.getPassword(userName);
    }

    @Override
    public void updateUser(String newPassword , String userName) {
        Map<String, String> params = new HashMap<>();
        params.put("newPassword", newPassword);
        params.put("userName", userName);

        usersMapper.updateUser(params);
    }
}




