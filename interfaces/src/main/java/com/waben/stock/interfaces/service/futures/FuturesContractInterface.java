package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "futuresContract", path = "futuresContract", qualifier = "futuresContractInterface")
public interface FuturesContractInterface {

}
