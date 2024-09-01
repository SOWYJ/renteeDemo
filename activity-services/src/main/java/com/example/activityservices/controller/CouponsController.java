package com.example.activityservices.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.activityservices.domain.Coupons;
import com.example.activityservices.http.HttpResult;
import com.example.activityservices.service.CouponsService;
import com.example.activityservices.until.UploadUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/activity")
public class CouponsController {

    @Autowired
    private CouponsService couponsService;


    @RequestMapping("/upload")
    public String uploadFile(MultipartFile file) {
        System.out.println("######################:" + file);
        return UploadUntil.uploadFile(file);
    }


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

    @RequestMapping("/deleteCoupon")
    public HttpResult deleteCoupon(@RequestBody Coupons row){
//        System.out.println("###################:" + row);
        boolean result = couponsService.deleteCouponById(row.getCouponId());
        if (result){
            return HttpResult.ok("删除成功！");
        }
        return HttpResult.error("删除失败！");
    }

    @PostMapping("/updateCoupon")
    public HttpResult updateCoupon(@RequestBody Coupons updateCoupon){
//        System.out.println("%%%%%%%%%%%%%%%%%：" + updateCoupon);
        String couponId = updateCoupon.getCouponId();
        UpdateWrapper wrapper = new UpdateWrapper<>();
        wrapper.eq("coupon_id",couponId);
        boolean result = couponsService.update(updateCoupon,wrapper);;
//        System.out.println("修改成功@@@@@@@@@@@@@@@@@@@@@@@");
        if (result){
            return HttpResult.ok("修改成功！");
        }
        return HttpResult.error("修改失败！");
    }

    @PostMapping("/decreaseNum")
    public HttpResult decreaseNum(@RequestBody Coupons updateCoupon){
        System.out.println("@@@@@@@@@@@@@@@@@@@@:" + updateCoupon.getCouponId());
        HttpResult result = couponsService.decreaseNum(updateCoupon.getCouponId());;
//        System.out.println("修改成功@@@@@@@@@@@@@@@@@@@@@@@");
        if (result.getCode() == 200){
            return HttpResult.ok("修改成功！");
        }
        return HttpResult.error("修改失败！");
    }

}
