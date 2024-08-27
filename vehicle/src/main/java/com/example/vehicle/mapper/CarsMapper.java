package com.example.vehicle.mapper;

import com.example.vehicle.domain.Cars;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author acer
* @description 针对表【cars】的数据库操作Mapper
* @createDate 2024-08-19 21:43:53
* @Entity com.example.vehicle.domain.Cars
*/
@Mapper
public interface CarsMapper extends BaseMapper<Cars> {
    List<Cars> getAllVehicle();
}




