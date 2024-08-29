package com.example.vehicle.service.impl;

import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.mapper.CarsdetailMapper;
import com.example.vehicle.service.CarsdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarsdetailServiceImpl implements CarsdetailService {
    @Autowired
    private CarsdetailMapper carsdetailMapper;
    @Override
    public List<Cardetail> getCarStateDetails() {
//        System.out.println("KKKKKKKKKKKKKKKKKK");
        System.out.println(carsdetailMapper.getCarStateDetails());
        return carsdetailMapper.getCarStateDetails();
    }
}
