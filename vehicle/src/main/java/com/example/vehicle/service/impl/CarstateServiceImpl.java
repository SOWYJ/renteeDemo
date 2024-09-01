package com.example.vehicle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vehicle.domain.Carstate;
import com.example.vehicle.service.CarstateService;
import com.example.vehicle.mapper.CarstateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author acer
* @description 针对表【carstate】的数据库操作Service实现
* @createDate 2024-08-30 16:08:59
*/
@Service
public class CarstateServiceImpl implements CarstateService{
    @Autowired
    private CarstateMapper carstateMapper;

    @Override
    public void deleteCarSate(Carstate carstate) {
        carstateMapper.deleteById(carstate);
    }

    @Override
    public List<Carstate> deliveryCars(Carstate carstate) {
        carstateMapper.insert(carstate);
        return null;
    }
}




