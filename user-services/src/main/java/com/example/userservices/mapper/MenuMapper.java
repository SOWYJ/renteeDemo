package com.example.userservices.mapper;

import com.example.userservices.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author wsk
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2024-08-18 19:46:14
* @Entity com.example.userservices.domain.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getAllMenu();
}




