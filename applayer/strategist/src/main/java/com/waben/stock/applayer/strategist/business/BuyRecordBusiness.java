package com.waben.stock.applayer.strategist.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.applayer.strategist.dto.buyrecord.BuyRecordWithMarketDto;
import com.waben.stock.applayer.strategist.dto.buyrecord.TradeDynamicDto;
import com.waben.stock.applayer.strategist.retrivestock.RetriveStockOverHttp;
import com.waben.stock.applayer.strategist.retrivestock.bean.StockMarket;
import com.waben.stock.applayer.strategist.service.BuyRecordService;
import com.waben.stock.applayer.strategist.service.DeferredRecordService;
import com.waben.stock.applayer.strategist.service.PublisherService;
import com.waben.stock.applayer.strategist.service.SettlementService;
import com.waben.stock.applayer.strategist.service.StockService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.buyrecord.DeferredRecordDto;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;

@Service
public class BuyRecordBusiness {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BuyRecordService buyRecordService;

	@Autowired
	private SettlementService settlementService;

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private StockService stockService;

	@Autowired
	private DeferredRecordService deferredRecordService;

	public BuyRecordDto findById(Long id) {
		Response<BuyRecordDto> response = buyRecordService.fetchBuyRecord(id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BuyRecordDto buy(BuyRecordDto buyRecordDto) {
		// 获取股票名称
		Response<StockDto> stock = stockService.fetchWithExponentByCode(buyRecordDto.getStockCode());
		if ("200".equals(stock.getCode())) {
			buyRecordDto.setStockName(stock.getResult().getName());
		} else {
			throw new ServiceException(stock.getCode());
		}
		// 获取策略发布人
		Response<PublisherDto> publisher = publisherService.fetchById(buyRecordDto.getPublisherId());
		if ("200".equals(publisher.getCode())) {
			buyRecordDto.setPublisherPhone(publisher.getResult().getPhone());
		} else {
			throw new ServiceException(stock.getCode());
		}
		// 请求点买
		Response<BuyRecordDto> response = buyRecordService.addBuyRecord(buyRecordDto);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<BuyRecordDto> pages(BuyRecordQuery buyRecordQuery) {

		Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByQuery(buyRecordQuery);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<BuyRecordWithMarketDto> pagesSettlement(SettlementQuery query) {
		Response<PageInfo<SettlementDto>> response = settlementService.pagesByQuery(query);
		if ("200".equals(response.getCode())) {
			List<BuyRecordWithMarketDto> content = new ArrayList<>();
			List<SettlementDto> settlementContent = response.getResult().getContent();
			if (settlementContent != null && settlementContent.size() > 0) {
				for (SettlementDto settlement : settlementContent) {
					BuyRecordWithMarketDto buyRecord = wrapMarketInfo(settlement.getBuyRecord());
					buyRecord.setProfitOrLoss(settlement.getProfitOrLoss());
					buyRecord.setPublisherProfitOrLoss(settlement.getPublisherProfitOrLoss());
					DeferredRecordDto deferredRecordDto = deferredRecordService
							.fetchByPublisherIdAndBuyRecordId(buyRecord.getPublisherId(), buyRecord.getId())
							.getResult();
					if (deferredRecordDto != null) {
						buyRecord.setDeferredDays(deferredRecordDto.getCycle());
						buyRecord.setDeferredCharges(deferredRecordDto.getFee());
					}
					content.add(buyRecord);
				}
			}
			return new PageInfo<>(content, response.getResult().getTotalPages(), response.getResult().getLast(),
					response.getResult().getTotalElements(), response.getResult().getSize(),
					response.getResult().getNumber(), response.getResult().getFrist());
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

	public BuyRecordDto sellApply(Long userId, Long id) {
		Response<BuyRecordDto> response = buyRecordService.sellApply(userId, id);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<TradeDynamicDto> tradeDynamic(int page, int size) {
		SettlementQuery sQuery = new SettlementQuery(page, size / 2);
		sQuery.setOnlyProfit(true);
		Response<PageInfo<SettlementDto>> sResponse = settlementService.pagesByQuery(sQuery);
		if ("200".equals(sResponse.getCode())) {
			BuyRecordQuery bQuery = new BuyRecordQuery(page, size - sResponse.getResult().getContent().size(), null,
					new BuyRecordState[] { BuyRecordState.HOLDPOSITION, BuyRecordState.SELLAPPLY,
							BuyRecordState.SELLLOCK });
			PageInfo<BuyRecordDto> pageInfo = pages(bQuery);

			int total = sResponse.getResult().getContent().size() + pageInfo.getContent().size();
			int sSize = sResponse.getResult().getContent().size();
			int bSize = pageInfo.getContent().size();
			int i = sSize;
			int j = bSize;

			List<TradeDynamicDto> content = new ArrayList<>();
			for (int n = 0; n < total; n++) {
				TradeDynamicDto inner = new TradeDynamicDto();
				if (n % 2 == 0 && i > 0) {
					SettlementDto settlement = sResponse.getResult().getContent().get(sSize - i);
					inner.setTradeType(2);
					inner.setPublisherId(settlement.getBuyRecord().getPublisherId());
					inner.setStockCode(settlement.getBuyRecord().getStockCode());
					inner.setStockName(settlement.getBuyRecord().getStockName());
					inner.setPhone(settlement.getBuyRecord().getPublisherPhone());
					inner.setProfit(settlement.getPublisherProfitOrLoss());
					inner.setTradePrice(settlement.getBuyRecord().getSellingPrice());
					inner.setTradeTime(settlement.getBuyRecord().getSellingTime());
					i--;
				} else {
					BuyRecordDto buyRecord = pageInfo.getContent().get(bSize - j);
					inner.setTradeType(1);
					inner.setPublisherId(buyRecord.getPublisherId());
					inner.setStockCode(buyRecord.getStockCode());
					inner.setStockName(buyRecord.getStockName());
					inner.setPhone(buyRecord.getPublisherPhone());
					inner.setTradePrice(buyRecord.getBuyingPrice());
					inner.setTradeTime(buyRecord.getBuyingTime());
					j--;
				}
				content.add(inner);
			}
			return new PageInfo<TradeDynamicDto>(content, 0, false, 0L, size, page, false);
		}
		throw new ServiceException(sResponse.getCode());
	}

}
