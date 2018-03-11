package com.waben.stock.applayer.promotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { SpringBootWebSecurityConfiguration.class, SecurityFilterAutoConfiguration.class })
// 服务发现客户端
@EnableDiscoveryClient
// 服务调用
@EnableFeignClients
// 断路器
@EnableHystrix
// 扫描包
@EnableScheduling
@ComponentScan(basePackages = { "com.waben.stock" })
public class PromotionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromotionApplication.class, args);
	}
}
