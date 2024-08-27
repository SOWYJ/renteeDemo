package com.example.vehicle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.vehicle.domain.Cars;
import com.example.vehicle.dto.PeoductQueryDto;
import com.example.vehicle.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

//@RestController
//@RequestMapping("/product")
//public class ProductContrpller {
//    @Autowired
//    private ProductService productService;
//
//    @PostMapping("/viewProduct")
//    public IPage<Product> viewProduct(@RequestBody PeoductQueryDto queryDto){
//        return productService.viewProduct(queryDto);
//    }
//}
@CrossOrigin
@RestController
public class vehicleController {
    @Autowired
    private CarsService carsService;

    @PostMapping("/viewProduct")
    public IPage<Cars> viewProduct(@RequestBody PeoductQueryDto queryDto){
        return carsService.viewProduct(queryDto);
    }
}
