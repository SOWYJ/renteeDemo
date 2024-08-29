package com.example.userservices.mapper;

import com.example.userservices.domain.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userservices.dto.LoginDto;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wsk
* @description 针对表【users】的数据库操作Mapper
* @createDate 2024-08-28 14:50:22
* @Entity com.example.userservices.domain.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    Integer getUserName(String userName);

    String getPassword(String userName);

}




