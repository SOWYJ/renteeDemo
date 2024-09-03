package com.example.activityservices.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activityservices.cache.CouponCache;
import com.example.activityservices.config.Producer;
import com.example.activityservices.domain.Coupons;
import com.example.activityservices.http.HttpResult;
import com.example.activityservices.service.CouponsService;
import com.example.activityservices.mapper.CouponsMapper;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lenovo
 * @description 针对表【coupons】的数据库操作Service实现
 * @createDate 2024-08-23 17:06:08
 */
@Service
public class CouponsServiceImpl extends ServiceImpl<CouponsMapper, Coupons>
        implements CouponsService {

    @Autowired
    private CouponsMapper couponsMapper;

    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Producer producer;

    @Autowired
    private CouponCache couponCache;

    @Override
    public boolean deleteCouponById(String couponId) {
        return couponsMapper.deleteCouponById(couponId);
    }

    @Override
    public HttpResult decreaseNum(String couponId) {
        RLock lock = redisson.getLock("localhost");

        try {
            boolean tryLock = lock.tryLock(60, TimeUnit.SECONDS);
            if (tryLock) {
//                List<Coupons> couponsList = couponCache.getCouponList("couponList");
//
//                int totalNum = 0;
//                for (Coupons coupons : couponsList) {
////                    System.out.println("呜呜呜呜呜呜呜:" + coupons);
////                    System.out.println("IIIIIIIIIIIIIIIIIIIII:" + coupons.getCouponId());
//                    if (Objects.equals(couponId, coupons.getCouponId())) {
//                        totalNum = coupons.getTotalNum();
////                        System.out.println("#$$$$$$$$$$$$##$$###:" + totalNum );
//                    }
//                }
                // 从数据库获取导师数量、修改：瓶颈
                int totalNum = Integer.parseInt(redisTemplate.opsForValue().get(couponId));
                System.out.println("##################！库存！#############:" + totalNum);

//                System.out.println("学生数量:" + stuNum);
                if (totalNum > 0) {
                    totalNum = redisTemplate.opsForValue().decrement(couponId).intValue();

                    // 数据库同步减1，直接操作数据库，数据库响应速度跟不上，此时采用消息队列，把库存减1操作发送到消息队列，消费者慢慢把消费消息
                    // 消息队列可以堆积消息（有机制可以保证消息不丢失）：削峰

                    Map<String, Object> msgMap = new HashMap<>();
                    msgMap.put("couponId", couponId);
//                    msgMap.put("studentNum",totalNum);

//                    producer.setcouponId(couponId);
                    producer.newProducer(JSON.toJSONString(msgMap));

//                    System.out.println("学生数量2:" + stuNum);


//                    teacherMapper.checkTeacher(couponId);
//                   redisTemplate.opsForValue().set(couponId,stuNum+ "");

                    return HttpResult.ok("购买成功！");
                } else {
                    return HttpResult.error("购买失败！");
                }
            } else {
                return HttpResult.error("失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
//        return couponsMapper.decreaseNum(couponId);
    }

    @Override
    public Coupons saveCoupon(Coupons coupons) {
        System.out.println("AAAAAAAAAAAAA!");
        couponsMapper.insert(coupons);
        return coupons;
    }

}




