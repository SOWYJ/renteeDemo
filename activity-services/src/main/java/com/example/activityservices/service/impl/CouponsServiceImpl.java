package com.example.activityservices.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activityservices.domain.Coupons;
import com.example.activityservices.service.CouponsService;
import com.example.activityservices.mapper.CouponsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【coupons】的数据库操作Service实现
* @createDate 2024-08-23 17:06:08
*/
@Service
public class CouponsServiceImpl extends ServiceImpl<CouponsMapper, Coupons>
    implements CouponsService{

    @Autowired
    private CouponsMapper couponsMapper;

    @Override
    public boolean deleteCouponById(String couponId) {
        return couponsMapper.deleteCouponById(couponId);
    }

    @Override
    public boolean decreaseNum(String couponId) {
        return couponsMapper.decreaseNum(couponId);
    }
}




