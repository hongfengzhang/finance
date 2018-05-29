package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.admin.futures.FuturesTradeAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;

/**
 * 期货交易 reference服务接口
 *
 * @author pengzhenliang
 */
@FeignClient(name = "futures", path = "futuresTrade", qualifier = "futuresTradeInterface")
public interface FuturesTradeInterface {

	
	/**
	 * 分页查询期货订单（管理后台）
	 * 
	 * @param query
	 *            查询条件
	 * @return 期货订单分页数据
	 */
	@RequestMapping(value = "/adminpages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<FuturesTradeAdminDto>> adminPagesByQuery(@RequestBody FuturesTradeAdminQuery query);
}
