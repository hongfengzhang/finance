package com.waben.stock.datalayer.investors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//服务调用
@EnableFeignClients
//扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
public class InvestorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestorsApplication.class, args);
	}
}
