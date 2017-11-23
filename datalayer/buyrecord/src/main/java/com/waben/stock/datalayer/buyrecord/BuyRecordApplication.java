package com.waben.stock.datalayer.buyrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BuyRecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuyRecordApplication.class, args);
	}
}
