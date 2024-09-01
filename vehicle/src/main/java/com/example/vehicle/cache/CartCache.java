package com.example.vehicle.cache;

import com.example.vehicle.domain.Cars;
import com.example.vehicle.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CartCache {
    @Autowired
    private CarsService carsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        //Spring项目启动的时候，就能把数据加载到Redis
        System.out.println("汽车数据预热");
        List<Cars> carsList = carsService.list();
        setCarsList("carsList", carsList);
    }

    public void setCarsList(String key, List<Cars> carsList) {
        redisTemplate.delete(key); // 清除旧的列表
        for (Cars cars : carsList) {
            redisTemplate.opsForList().leftPush(key, cars);
        }
    }

    public List<Cars> getCarsList(String key) {
        List<Cars> carsList = (List<Cars>) redisTemplate.opsForList()
                .range("carsList", 0, -1);

        if (carsList.size() > 0) {
            //缓存有数据
            return carsList;
        } else {
            //缓存没数据，先从数据库取数据
            carsList = carsService.list();
            //把取到的数据存入redis
            setCarsList("carsList", carsList);
            return carsList;
        }
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Object.class);
    }
}
