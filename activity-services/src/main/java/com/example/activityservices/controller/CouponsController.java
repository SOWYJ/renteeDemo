package com.example.activityservices.controller;

import com.example.activityservices.domain.Coupons;
import com.example.activityservices.http.HttpResult;
import com.example.activityservices.service.CouponsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class CouponsController {

    @Autowired
    private CouponsService couponsService;


    @PostMapping("/saveCoupon")
    public HttpResult saveCoupon(@RequestBody Coupons coupons){
        coupons.setCouponId(UUID.randomUUID().toString().replace("-",""));
        if (couponsService.save(coupons)){
            return HttpResult.ok(coupons);
        }
        return HttpResult.error();
    }

    @GetMapping("/getAllCoupons")
    public List<Coupons> getAllCoupons(){
        return couponsService.list();
    }
}
