package com.example.userservices.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userservices.domain.Roles;
import com.example.userservices.service.RolesService;
import com.example.userservices.mapper.RolesMapper;
import org.springframework.stereotype.Service;

/**
* @author wsk
* @description 针对表【roles】的数据库操作Service实现
* @createDate 2024-08-18 19:47:09
*/
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles>
    implements RolesService{

}




