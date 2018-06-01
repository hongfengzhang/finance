package com.waben.stock.datalayer.futures.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.entity.FuturesOvernightRecord;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.datalayer.futures.service.FuturesOvernightRecordService;
import com.waben.stock.interfaces.dto.admin.futures.FuturesOrderAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;
import com.waben.stock.interfaces.service.futures.FuturesTradeInterface;
import com.waben.stock.interfaces.util.PageToPageInfo;

@RestController
@RequestMapping("/futuresTrade")
public class FuturesTradeController implements FuturesTradeInterface {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FuturesOrderService futuresOrderService;
	
	@Autowired
	private FuturesOvernightRecordService overnightService;
	
	@Override
	public Response<PageInfo<FuturesOrderAdminDto>> adminPagesByQuery(@RequestBody FuturesTradeAdminQuery query) {
//		Page<FuturesOrderAdminDto> page = futuresOrderService.adminPagesByQuery(query);
		Page<FuturesOrder> page = futuresOrderService.pagesOrderAdmin(query);
		PageInfo<FuturesOrderAdminDto> result = PageToPageInfo.pageToPageInfo(page, FuturesOrderAdminDto.class);
		for (int i=0; i<page.getContent().size();i++) {
			List<FuturesOvernightRecord> recordList = overnightService.findAll(page.getContent().get(i));
			BigDecimal count = new BigDecimal(0);
			for (FuturesOvernightRecord futuresOvernightRecord : recordList) {
				count.add(futuresOvernightRecord.getOvernightDeferredFee());
			}
			result.getContent().get(i).setPublisherId(page.getContent().get(i).getPublisherId());
			result.getContent().get(i).setOvernightServiceFee(count);
			if(page.getContent().get(i).getWindControlType()!=null){
				result.getContent().get(i).setWindControlType(page.getContent().get(i).getWindControlType().getType());
			}
			if(page.getContent().get(i).getSellingTime()!=null){
				result.getContent().get(i).setPositionEndTime(page.getContent().get(i).getSellingTime());
			}
			if(page.getContent().get(i).getOrderType()!=null){
				result.getContent().get(i).setOrderType(page.getContent().get(i).getOrderType().getType());
			}
			if(page.getContent().get(i).getState()!=null){
				result.getContent().get(i).setState(page.getContent().get(i).getState().getType());
			}
			if(page.getContent().get(i).getContract()!=null){
				result.getContent().get(i).setName(page.getContent().get(i).getContract().getName());
			}
			if(page.getContent().get(i).getBuyingTime()!=null){
				Long date = page.getContent().get(i).getBuyingTime().getTime();
				Long current = new Date().getTime();
				Long hours = (date - current)/(60*60*1000);
				result.getContent().get(i).setPositionDays(hours.intValue());
			}
		}
		return new Response<>(result);
	}

}
