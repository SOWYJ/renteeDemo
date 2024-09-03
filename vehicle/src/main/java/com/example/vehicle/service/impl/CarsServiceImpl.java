package com.example.vehicle.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vehicle.domain.Cars;
import com.example.vehicle.dto.PeoductQueryDto;
import com.example.vehicle.mapper.CarsMapper;
import com.example.vehicle.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author acer
* @description 针对表【cars】的数据库操作Service实现
* @createDate 2024-08-19 21:43:53
*/
@Service
public class CarsServiceImpl extends ServiceImpl<CarsMapper,Cars>
        implements CarsService{
    @Autowired
    private CarsMapper carsMapper;

    @Override
    public IPage<Cars> viewProduct(PeoductQueryDto queryDto) {
//        System.out.println("kkkkkkkk");
        //条件查询也不用写sql，直接java代码实现
        //无条件获取所有
        QueryWrapper<Cars> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("car_name",queryDto.getCarName());
        Page<Cars> page=new Page<>();
        page.setCurrent(queryDto.getCurrent());

        page.setSize(queryDto.getSize());
        return carsMapper.selectPage(page,queryWrapper);
    }

    @Override
    public void saveCars(Cars cars) {
        carsMapper.insert(cars);
    }

    @Override
    public void updateCars(Cars cars) {
        carsMapper.updateById(cars);
    }

    @Override
    public void deleteCars(Cars cars) {
        System.out.println("WWWWWWWWW"+cars);
//        carsMapper.deleteById(cars);
        System.out.println("KKKKKK"+cars.getId());
        carsMapper.deleteById(cars.getId());
//        carsMapper.deleteById(cars);
    }

}




