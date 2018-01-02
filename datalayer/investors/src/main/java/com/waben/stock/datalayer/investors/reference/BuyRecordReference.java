package com.waben.stock.datalayer.investors.reference;

import com.waben.stock.datalayer.investors.reference.fallback.BuyRecordReferenceFallBack;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc 投资人服务依赖点买服务
 */
@FeignClient(name = "buyrecord", path = "buyrecord", fallback = BuyRecordReferenceFallBack.class,qualifier = "buyRecordFeignReference")
public interface BuyRecordReference extends BuyRecordInterface {


}
