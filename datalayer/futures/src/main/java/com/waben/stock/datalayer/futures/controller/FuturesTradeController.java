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

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.entity.FuturesOvernightRecord;
import com.waben.stock.datalayer.futures.service.FuturesCurrencyRateService;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.datalayer.futures.service.FuturesOvernightRecordService;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.dto.admin.futures.FutresOrderEntrustDto;
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
	
	@Autowired
	private FuturesCurrencyRateService rateService;
	
	@Override
	public Response<PageInfo<FutresOrderEntrustDto>> pagesOrderEntrust(@RequestBody FuturesTradeAdminQuery query){
		Page<FuturesOrder> page = futuresOrderService.pagesOrderAdmin(query);
		PageInfo<FutresOrderEntrustDto> result = PageToPageInfo.pageToPageInfo(page, FutresOrderEntrustDto.class);
		List<FuturesOrder> orderList = page.getContent();
		for(int i=0;i<orderList.size();i++){
			FuturesOrder order = orderList.get(i);
			result.getContent().get(i).setPublisherId(order.getPublisherId());
			if(order.getOrderType()!=null){
				result.getContent().get(i).setOrderType(order.getOrderType().getType());
			}
			if(order.getState()!=null){
				result.getContent().get(i).setState(order.getState().getType());
			}
			if(order.getContract()!=null){
				result.getContent().get(i).setName(order.getContract().getName());
			}
			if(order.getContractSymbol()!=null && !"".equals(order.getContractSymbol())){
				String symbol = order.getContractSymbol();
				FuturesContractMarket market = RetriveFuturesOverHttp.market(symbol);
				if(market!=null){
					result.getContent().get(i).setLastPrice(market.getLastPrice());
				}
			}
			if(order.getState().getIndex().equals("8")){
				result.getContent().get(i).setEntrustAppointPrice(order.getSellingEntrustPrice());
				result.getContent().get(i).setDealTime(order.getSellingTime());
			}else{
				result.getContent().get(i).setEntrustAppointPrice(order.getBuyingEntrustPrice());
				result.getContent().get(i).setDealTime(order.getBuyingTime());
			}
		}
		return new Response<>(result);
	}
	
	@Override
	public Response<PageInfo<FuturesOrderAdminDto>> adminPagesByQuery(@RequestBody FuturesTradeAdminQuery query) {
//		Page<FuturesOrderAdminDto> page = futuresOrderService.adminPagesByQuery(query);
		Page<FuturesOrder> page = futuresOrderService.pagesOrderAdmin(query);
		PageInfo<FuturesOrderAdminDto> result = PageToPageInfo.pageToPageInfo(page, FuturesOrderAdminDto.class);
		for (int i=0; i<page.getContent().size();i++) {
			FuturesOrder order = page.getContent().get(i);
			List<FuturesOvernightRecord> recordList = overnightService.findAll(order);
			BigDecimal count = new BigDecimal(0);
			for (FuturesOvernightRecord futuresOvernightRecord : recordList) {
				count.add(futuresOvernightRecord.getOvernightDeferredFee());
			}
			result.getContent().get(i).setPublisherId(order.getPublisherId());
			result.getContent().get(i).setOvernightServiceFee(count);
			if(order.getWindControlType()!=null){
				result.getContent().get(i).setWindControlType(order.getWindControlType().getType());
			}
			if(order.getSellingTime()!=null){
				result.getContent().get(i).setPositionEndTime(order.getSellingTime());
			}
			if(order.getOrderType()!=null){
				result.getContent().get(i).setOrderType(order.getOrderType().getType());
			}
			if(order.getState()!=null){
				result.getContent().get(i).setState(order.getState().getType());
			}
			if(order.getContract()!=null){
				result.getContent().get(i).setName(order.getContract().getName());
			}
			if(order.getBuyingTime()!=null){
				Long date = order.getBuyingTime().getTime();
				Long current = new Date().getTime();
				Long hours = (date - current)/(60*60*1000);
				result.getContent().get(i).setPositionDays(hours.intValue());
			}
			FuturesCurrencyRate rate = rateService.queryByName(order.getContractCurrency());
			if(order.getProfitOrLoss()!=null && !"".equals(order.getProfitOrLoss())){
				if(rate!=null && rate.getRate()!=null){
					result.getContent().get(i).setSellingProfit(order.getProfitOrLoss().multiply(rate.getRate()));
					result.getContent().get(i).setProfit(order.getProfitOrLoss().multiply(rate.getRate()));
				}
			}else{
				FuturesContractMarket market = RetriveFuturesOverHttp.market(order.getContractSymbol());
				if(market!=null){
					BigDecimal lastPrice = market.getLastPrice();
					if(lastPrice!=null){
						BigDecimal profit = futuresOrderService.computeProfitOrLoss(order.getOrderType(), order.getTotalQuantity(), order.getBuyingPrice(), lastPrice, order.getContract().getMinWave(), order.getContract().getPerWaveMoney());
						if(rate!=null && rate.getRate()!=null){
							result.getContent().get(i).setProfit(profit.multiply(rate.getRate()));
						}
					}
				}
			}
		}
		return new Response<>(result);
	}

}
