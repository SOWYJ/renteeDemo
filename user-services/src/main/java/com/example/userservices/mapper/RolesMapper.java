package com.example.userservices.mapper;

import com.example.userservices.domain.Roles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wsk
* @description 针对表【roles】的数据库操作Mapper
* @createDate 2024-08-18 19:47:09
* @Entity com.example.userservices.domain.Roles
*/
@Mapper
public interface RolesMapper extends BaseMapper<Roles> {

}




