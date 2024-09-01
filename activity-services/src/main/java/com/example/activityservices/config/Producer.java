package com.example.activityservices.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @Author liwei
 * @Date 2023/8/12 11:29
 * @Version 1.0
 */
@Data
@Component
@Slf4j
public class Producer {

    private  DefaultMQProducer producer = new DefaultMQProducer("AIO");

    @PostConstruct
    public void init() throws MQClientException {
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        log.info("<<<<<<<<<<<<<,<<<<<<<<<<<<");
    }

    public  void newProducer(String teacherMes) throws Exception {
//        for (int i = 0; i < stuNum; i++) {
//            Message msg = new Message("AIO", "AIO-1", ("减库存消息：" + teacherId + "," +
//                    stuNum ).getBytes(StandardCharsets.UTF_8));
        Message msg = new Message("activity", "producer-1", teacherMes.getBytes(StandardCharsets.UTF_8));
        // 不同于同步通信只有这一步，多加了回调函数
        // 发送普通消息，实际秒杀的时候，发送的是《事务消息》
        SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                log.info("快快快：{}",mqs.get(index));
                return mqs.get(index);
            }
        },1111);
        System.out.println("！！！！！！！！！！！！！！！！！！！！:" + sendResult);
//    }
//        producer.send(msg, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.println("成功发送消息，" + sendResult.toString());
//                producer.shutdown();
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.println("消息发送失败，" + throwable.toString());
//            }
//        });
//        }
//        TimeUnit.SECONDS.sleep(3);
//        consumer.newConsumer();


    }

}
