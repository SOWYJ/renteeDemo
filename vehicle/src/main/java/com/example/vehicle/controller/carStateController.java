package com.example.vehicle.controller;

import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.domain.Cars;
import com.example.vehicle.service.CarsdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class carStateController {
    @Autowired
    private CarsdetailService carsdetailService;

    @PostMapping("/queryState")
    public List<Cardetail> queryState(){
//        System.out.println("kkkkkkkkkkkkkkkkk");
        return carsdetailService.getCarStateDetails();
    }

    @GetMapping ("/obquery")
    public List<Cardetail> obquery(String carName){

//        System.out.println("OOOOOOOOOOO"+carName);
//        System.out.println("OOOOOOOOOOOOOOOOO"+carsdetailService.obqueryCars(carName));
        return carsdetailService.obqueryCars(carName);
    }

}
