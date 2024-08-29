package com.example.vehicle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.dto.CardetailQueryDto;
import com.example.vehicle.mapper.CarsMapper;
import com.example.vehicle.mapper.CarsdetailMapper;
import com.example.vehicle.service.CarsdetailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarsdetailServiceImpl implements CarsdetailService {
    @Autowired
    private CarsdetailMapper carsdetailMapper;
    @Autowired
    private CarsMapper carsMapper;
    @Override
    public List<Cardetail> getCarStateDetails() {
//        System.out.println("KKKKKKKKKKKKKKKKKK");
//        System.out.println(carsdetailMapper.getCarStateDetails());
        return carsdetailMapper.getCarStateDetails();

    }



}
