package com.example.activityservices.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.activityservices.cache.CouponCache;
import com.example.activityservices.domain.Coupons;
import com.example.activityservices.http.HttpResult;
import com.example.activityservices.service.CouponsService;
import com.example.activityservices.until.UploadUntil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/activity")
public class CouponsController {

    @Autowired
    private CouponsService couponsService;

    @Autowired CouponCache couponCache;


    @RequestMapping("/upload")
    public String uploadFile(MultipartFile file) {
        System.out.println("######################:" + file);
        return UploadUntil.uploadFile(file);
    }


    @PostMapping("/saveCoupon")
    public HttpResult saveCoupon(@RequestBody Coupons coupons){
        coupons.setCouponId(UUID.randomUUID().toString().replace("-",""));
        System.out.println("VVVVVVVVVVVVVVVVVVV;" + coupons);
        Coupons coupons1 = couponsService.saveCoupon(coupons);
        List<Coupons> couponsList = couponCache.getCouponList("couponList");
        Coupons coupons2 = new Coupons();
        try {
            BeanUtils.copyProperties(coupons2,coupons1);
            if (couponsList.add(coupons2) && !"".equals(coupons2)){
                couponCache.setCouponList("couponList",couponsList);
                return HttpResult.ok("保存成功！");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return HttpResult.error();
    }

    @GetMapping("/getAllCoupons")
    public List<Coupons> getAllCoupons(){
        return couponCache.getCouponList("couponList");
    }

    @RequestMapping("/deleteCoupon")
    public HttpResult deleteCoupon(@RequestBody Coupons row){
        System.out.println("###################:" + row);
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
        HttpResult result = couponsService.decreaseNum(updateCoupon.getCouponId());
//        System.out.println("修改成功@@@@@@@@@@@@@@@@@@@@@@@");
        if (result.getCode() == 200){
            return HttpResult.ok("购买成功！");
        }
        return HttpResult.error("购买失败！");
    }

}
