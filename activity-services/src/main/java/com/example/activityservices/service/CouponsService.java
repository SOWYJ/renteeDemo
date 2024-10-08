package com.example.activityservices.service;

import com.example.activityservices.domain.Coupons;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activityservices.http.HttpResult;

/**
* @author Lenovo
* @description 针对表【coupons】的数据库操作Service
* @createDate 2024-08-23 17:06:08
*/
public interface CouponsService extends IService<Coupons> {

    boolean deleteCouponById(String couponId);

    HttpResult decreaseNum(String couponId);

    Coupons saveCoupon(Coupons coupons);
}
