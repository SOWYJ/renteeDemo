package com.example.vehicle.controller;

import com.example.vehicle.domain.Cardetail;
import com.example.vehicle.domain.Cars;
import com.example.vehicle.domain.Carstate;
import com.example.vehicle.service.CarsdetailService;
import com.example.vehicle.service.CarstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class carStateController {
    @Autowired
    private CarsdetailService carsdetailService;
    @Autowired
    private CarstateService carstateService;

    @PostMapping("/queryState")
    public List<Cardetail> queryState(){
//        System.out.println("kkkkkkkkkkkkkkkkk");
        return carsdetailService.getCarStateDetails();
    }

    @PostMapping("/qCarState")
    public Carstate qCarState(@RequestBody Cars cars){
//        System.out.println("kkkkkkkkkkkkkkkkk");
        System.out.println("cars数据"+cars);
        return carstateService.qCarState(cars);
    }

    @GetMapping ("/obquery")
    public List<Cardetail> obquery(String carName){

//        System.out.println("OOOOOOOOOOO"+carName);
//        System.out.println("OOOOOOOOOOOOOOOOO"+carsdetailService.obqueryCars(carName));
        return carsdetailService.obqueryCars(carName);
    }

    @PostMapping("/deliveryCars")
    public List<Carstate> deliveryCars(@RequestBody Carstate carstate){
        System.out.println("kkkkkkkkkkkkkkkkk"+carstate);
        return carstateService.deliveryCars(carstate);
    }

}
