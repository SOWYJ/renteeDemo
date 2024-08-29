package com.example.vehicle.controller;

import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.service.CarsdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class carStateController {
    @Autowired
    private CarsdetailService carsdetailService;

    @PostMapping("/queryState")
    public List<Cardetail> queryState(){
//        System.out.println("kkkkkkkkkkkkkkkkk");
        return carsdetailService.getCarStateDetails();
    }
}
