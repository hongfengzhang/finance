package com.waben.stock.applayer.tactics.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.tactics.dto.buyrecord.BuyRecordWithMarketDto;
import com.waben.stock.applayer.tactics.retrivestock.RetriveStockOverHttp;
import com.waben.stock.applayer.tactics.retrivestock.bean.StockMarket;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.service.BuyRecordService;
import com.waben.stock.applayer.tactics.service.CapitalAccountService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;

@Service
public class BuyRecordBusiness {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BuyRecordService buyRecordService;

	@Autowired
	private CapitalAccountService accountService;

	public BuyRecordDto buy(BuyRecordDto buyRecordDto) {
		Response<BuyRecordDto> response = buyRecordService.addBuyRecord(buyRecordDto);
		if (response.getCode().equals("200")) {
			// 扣去金额、冻结保证金，增加流水记录
			Response<CapitalAccountDto> accountOperationResp = accountService.serviceFeeAndReserveFund(
					SecurityUtil.getUserId(), response.getResult().getId(), response.getResult().getSerialCode(),
					response.getResult().getServiceFee(), response.getResult().getReserveFund());
			if (!"200".equals(accountOperationResp.getCode())) {
				// 扣款失败，删除点买记录，TODO 删除失败，再处理
				buyRecordService.dropBuyRecord(response.getResult().getId());
				throw new ServiceException(accountOperationResp.getCode());
			}
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<BuyRecordDto> pages(BuyRecordQuery buyRecordQuery) {
		Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByQuery(buyRecordQuery);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BuyRecordWithMarketDto wrapMarketInfo(BuyRecordDto buyRecord) {
		List<BuyRecordDto> list = new ArrayList<>();
		list.add(buyRecord);
		List<BuyRecordWithMarketDto> marketList = wrapMarketInfo(list);
		return marketList.get(0);
	}

	public List<BuyRecordWithMarketDto> wrapMarketInfo(List<BuyRecordDto> list) {
		List<BuyRecordWithMarketDto> result = CopyBeanUtils.copyListBeanPropertiesToList(list,
				BuyRecordWithMarketDto.class);
		List<String> codes = new ArrayList<>();
		for (BuyRecordWithMarketDto record : result) {
			codes.add(record.getStockCode());
		}
		if (codes.size() > 0) {
			List<StockMarket> stockMarketList = RetriveStockOverHttp.listStockMarket(restTemplate, codes);
			if (stockMarketList != null && stockMarketList.size() > 0) {
				for (int i = 0; i < stockMarketList.size(); i++) {
					StockMarket market = stockMarketList.get(i);
					BuyRecordWithMarketDto record = result.get(i);
					record.setStockName(market.getName());
					record.setLastPrice(market.getLastPrice());
					record.setUpDropPrice(market.getUpDropPrice());
					record.setUpDropSpeed(market.getUpDropSpeed());
				}
			}
		}
		return result;
	}

	public BuyRecordDto sellLock(Long lockUserId, Long id, Boolean isPublisher) {
		Response<BuyRecordDto> response = buyRecordService.sellLock(lockUserId, id, isPublisher);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BuyRecordDto sellOut(Long investorId, Long id, BigDecimal sellingPrice) {
		Response<BuyRecordDto> response = buyRecordService.sellOut(investorId, id, sellingPrice, new BigDecimal(0.9));
		if (response.getCode().equals("200")) {
			accountService.returnReserveFund(response.getResult().getPublisherId(), response.getResult().getId(),
					response.getResult().getSerialCode(), response.getResult().getPublisherProfitOrLoss());
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
