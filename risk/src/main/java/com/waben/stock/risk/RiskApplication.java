package com.waben.stock.risk;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.risk.business.BuyRecordBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/1.
 * @desc
 */
@SpringBootApplication
//服务发现客户端
@EnableDiscoveryClient
//服务调用
@EnableFeignClients

@ComponentScan(basePackages = { "com.waben.stock" })
@Controller
public class RiskApplication {

    public static void main(String[] args) {
        System.setProperty("org.terracotta.quartz.skipUpdateCheck","true");
        SpringApplication.run(RiskApplication.class);
    }

    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    @RequestMapping("/buyrecords")
    @ResponseBody
    public Response<List<BuyRecordDto>> buyrecords() {
        return new Response<>(buyRecordBusiness.buyRecordsWithBuyLock());
    }


}
