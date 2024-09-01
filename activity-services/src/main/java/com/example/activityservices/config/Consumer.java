package com.example.activityservices.config;


import com.alibaba.fastjson2.JSON;
import com.example.activityservices.mapper.CouponsMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * @Author
 * @Component 把Consumer作为一个Bean作为到IOC容器
 * @Date 2023/8/15 09:32
 * @Version 1.0
 */
@Data
@Component
public class Consumer {

    @Autowired
    private CouponsMapper couponsMapper;

    @PostConstruct // 自动启动
    public void newConsumer() {

        try {
            System.out.println("消费者启动!!!");
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-01");
            // 指定消息服务器地址（去什么地方消费消息）
            consumer.setNamesrvAddr("localhost:9876");
            consumer.subscribe("activity", "*");


            consumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (Message message : list) {
                        String mess = new String(message.getBody());

                        System.out.println("消息：" + mess);
                        // 拿到teacherId和num后，开始操作mysql，真正的在数据库减库存
                        // 转格式
                        Map<String, Object> msgMap = JSON.parseObject(mess, Map.class);
                        System.out.println("msgMap:" + msgMap);

                        String couponId = String.valueOf(msgMap.get("couponId"));
//                        int studentNum = (int) msgMap.get("studentNum");


                        System.out.println("AAAAAAA:" + couponId);
//                        System.out.println("bbbbbbb:" + studentNum);
                        couponsMapper.decreaseNum((String) msgMap.get("couponId"));

                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            consumer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }

    }
}
