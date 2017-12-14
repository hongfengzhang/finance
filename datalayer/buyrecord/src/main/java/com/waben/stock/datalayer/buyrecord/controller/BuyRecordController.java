package com.waben.stock.datalayer.buyrecord.controller;

import com.netflix.discovery.converters.Auto;
import com.waben.stock.datalayer.buyrecord.business.PublisherBusiness;
import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.service.BuyRecordService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.enums.WindControlType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.*;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 点买记录 Controller
 *
 * @author luomengan
 */
@RestController
@RequestMapping("/buyrecord")
public class BuyRecordController implements BuyRecordInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BuyRecordService buyRecordService;
	@Autowired
	private PublisherBusiness publisherBusiness;

	@Override
	public Response<BuyRecordDto> fetchBuyRecord(@PathVariable Long buyrecord) {
		BuyRecord buyRecord = buyRecordService.findBuyRecord(buyrecord);
		BuyRecordDto buyRecordDto = CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecord, false);
		return new Response<>(buyRecordDto);
	}

	@Override
	public Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto) {
		BuyRecord buyRecord = CopyBeanUtils.copyBeanProperties(BuyRecord.class, buyRecordDto, false);
		return new Response<>(
				CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecordService.save(buyRecord), false));
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByQuery(@RequestBody BuyRecordQuery buyRecordQuery) {
		Page<BuyRecord> page = buyRecordService.pagesByQuery(buyRecordQuery);
		PageInfo<BuyRecordDto> result = PageToPageInfo.pageToPageInfo(page, BuyRecordDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<BuyRecordDto> buyLock(@PathVariable Long investorId, @PathVariable Long id, String delegateNumber) {
		BuyRecord buyRecord = buyRecordService.buyLock(investorId, id, delegateNumber);
		return new Response<>(CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecord, false));
	}

	@Override
	public Response<BuyRecordDto> buyInto(@PathVariable Long investorId, @PathVariable Long id,
			BigDecimal buyingPrice) {
		BuyRecord buyRecord = buyRecordService.buyInto(investorId, id, buyingPrice);
		return new Response<>(CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecord, false));
	}

	@Override
	public Response<BuyRecordDto> sellApply(@PathVariable Long publisherId, @PathVariable Long id) {
		BuyRecord buyRecord = buyRecordService.sellApply(publisherId, id);
		return new Response<>(CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecord, false));
	}

	@Override
	public Response<BuyRecordDto> sellLock(@PathVariable Long investorId, @PathVariable Long id, String delegateNumber,
			String windControlTypeIndex) {
		BuyRecord buyRecord = buyRecordService.sellLock(investorId, id, delegateNumber,
				WindControlType.getByIndex(windControlTypeIndex));
		return new Response<>(CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecord, false));
	}

	@Override
	public Response<BuyRecordDto> sellOut(@PathVariable Long investorId, @PathVariable Long id,
			BigDecimal sellingPrice) {
		BuyRecord buyRecord = buyRecordService.sellOut(investorId, id, sellingPrice);
		return new Response<>(CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecord, false));
	}

	@Override
	public Response<Void> dropBuyRecord(@PathVariable Long id) {
		buyRecordService.remove(id);
		return new Response<>();
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByPostedQuery(StrategyPostedQuery strategyPostedQuery) {
		Page<BuyRecord> page = buyRecordService.pagesByPostedQuery(strategyPostedQuery);
		PageInfo<BuyRecordDto> result = PageToPageInfo.pageToPageInfo(page, BuyRecordDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByHoldingQuery(StrategyHoldingQuery strategyHoldingQuery) {
		Page<BuyRecord> page = buyRecordService.pagesByHoldingQuery(strategyHoldingQuery);
		PageInfo<BuyRecordDto> result = PageToPageInfo.pageToPageInfo(page, BuyRecordDto.class);
		return new Response<>(result);
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByUnwindQuery(StrategyUnwindQuery strategyUnwindQuery) {
		Page<BuyRecord> page = buyRecordService.pagesByUnwindQuery(strategyUnwindQuery);
		PageInfo<BuyRecordDto> result = PageToPageInfo.pageToPageInfo(page, BuyRecordDto.class);
		return new Response<>(result);
	}

}
