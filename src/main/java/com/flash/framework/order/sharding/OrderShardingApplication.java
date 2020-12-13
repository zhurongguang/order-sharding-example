package com.flash.framework.order.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhurg
 * @date 2020/11/24 - 下午5:44
 */
@MapperScan(basePackages = "com.flash.framework.order.sharding.mapper")
@SpringBootApplication
public class OrderShardingApplication {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            SpringApplication.run(OrderShardingApplication.class, args);
            countDownLatch.await();
        } catch (Exception e) {
            countDownLatch.countDown();
        }
    }
}