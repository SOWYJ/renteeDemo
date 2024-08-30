package com.example.vehicle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.vehicle.domain.Cars;
import com.example.vehicle.dto.PeoductQueryDto;
import com.example.vehicle.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class vehicleController {
    @Autowired
    private CarsService carsService;

    @PostMapping("/viewProduct")
    public IPage<Cars> viewProduct(@RequestBody PeoductQueryDto queryDto){
//        System.out.println("KKKKKKKKKKKK"+carsService.viewProduct(queryDto));
        return carsService.viewProduct(queryDto);
    }

    @PostMapping("/saveCars")
    public List<Cars> saveCars(@RequestBody Cars cars){
//        System.out.println("hhhhhhhhhhhhhhh");
        carsService.saveCars(cars);
        return null;
    }

    @PostMapping("/updateCars")
    public List<Cars> updateCars(@RequestBody Cars cars){
        carsService.updateCars(cars);
        return null;
    }

    @PostMapping("/deleteCars")
    public List<Cars> deleteCars(@RequestBody Cars cars){
        System.out.println("KKKKKKKKKK"+cars);
//        carsService.deleteCars(cars);
        return null;
    }

    @RequestMapping("/getAllCarts")
    public List<Cars> getAllCarts(){
        return carsService.list();
    }


}
