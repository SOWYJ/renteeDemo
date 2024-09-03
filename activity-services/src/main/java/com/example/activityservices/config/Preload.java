package com.example.activityservices.config;


import com.example.activityservices.domain.Coupons;
import com.example.activityservices.service.CouponsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


import java.util.List;


@Slf4j
@Configuration
public class Preload implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CouponsService couponsService;

    @Override
    public void run(String... args){
        log.info("预加载.....................");
        // 读取数据库导师的学生数量，然后导入redis
        // 实际项目加载全部
        List<Coupons> couponsList = couponsService.list();
        couponsList.forEach(coupons -> {
            redisTemplate.opsForValue().set(coupons.getCouponId(), coupons.getTotalNum() + "");
        });

    }
}
