package com.example.vehicle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vehicle.domain.Cars;
import com.example.vehicle.dto.PeoductQueryDto;


import java.util.List;

/**
* @author acer
* @description 针对表【cars】的数据库操作Service
* @createDate 2024-08-19 21:43:53
*/
public interface CarsService extends IService<Cars> {

    IPage<Cars> viewProduct(PeoductQueryDto queryDto);

    void saveCars(Cars cars);

    void updateCars(Cars cars);

    void deleteCars(Cars cars);
}
