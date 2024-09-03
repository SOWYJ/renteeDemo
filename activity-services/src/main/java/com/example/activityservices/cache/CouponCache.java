package com.example.activityservices.cache;


import com.example.activityservices.domain.Coupons;
import com.example.activityservices.service.CouponsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponCache {

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 构造函数执行后执行
     */
    @PostConstruct
    public void init() {
        // Spring项目启动时，把数据加载到redis
        System.out.println("数据预热！！！！！！！！！！");
        List<Coupons> couponsList = couponsService.list();
        setCouponList("couponList", couponsList);
    }

    public void setCouponList(String key, List<Coupons> couponsList) {
        redisTemplate.delete(key); // 清除旧的列表
        for (Coupons coupons : couponsList) {
            redisTemplate.opsForList().leftPush(key, coupons);
        }
    }

    public List<Coupons> getCouponList(String key) {
        // 先从缓存取数据
        List<Coupons> couponList = (List<Coupons>) redisTemplate.opsForList()
                .range("couponList", 0, -1);
        if (couponList.size() > 0) {
            // 缓存有数据
            return couponList;
        } else {
            // 缓存没数据
            couponList = couponsService.list();
            // 把取到的数据存入redis
            setCouponList("couponList", couponList);
            return couponList;
        }
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Object.class);
    }

}
