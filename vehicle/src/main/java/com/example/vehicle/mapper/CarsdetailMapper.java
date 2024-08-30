package com.example.vehicle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.domain.Cars;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface CarsdetailMapper extends BaseMapper<Cars> {

    List<Cardetail> getCarStateDetails();

    List<Cardetail> obqueryCars(@Param("carName")String carName);
}
