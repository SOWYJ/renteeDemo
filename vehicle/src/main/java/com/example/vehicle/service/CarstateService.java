package com.example.vehicle.service;

import com.example.vehicle.domain.Carstate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author acer
* @description 针对表【carstate】的数据库操作Service
* @createDate 2024-08-30 16:08:59
*/
public interface CarstateService{

    void deleteCarSate(Carstate carstate);
}
