package com.waben.stock.risk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Created by yuyidi on 2017/11/1.
 * @desc
 */
@SpringBootApplication
//服务发现客户端
@EnableDiscoveryClient
//服务调用
@EnableFeignClients(basePackages = {"com.waben.stock"})
//断路器
@EnableHystrix
@ComponentScan(basePackages = {"com.waben.stock"})
public class RiskApplication {


    public static void main(String[] args) {
        System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
        SpringApplication.run(RiskApplication.class);
    }


}
