package com.waben.stock.interfaces.service.buyrecord;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

public interface BuyRecordInterface {

	@RequestMapping(value = "/{buyrecord}", method = RequestMethod.GET)
	Response<BuyRecordDto> fetchBuyRecord(@PathVariable("buyrecord") Long buyrecord);

	/**
	 * 添加点买记录，此时状态为“发布”
	 *
	 * @param buyRecordDto
	 *            点买记录
	 * @return 点买记录
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto);

	/**
	 * 删除点买记录
	 *
	 * @param id
	 *            点买记录ID
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Response<String> dropBuyRecord(@PathVariable("id") Long id);

	/**
	 * 投资人买入股票锁定，此时状态为“买入锁定”
	 *
	 * @param investorId
	 *            投资人ID
	 * @param id
	 *            点买记录id
	 * @return 点买记录
	 */
	@RequestMapping(value = "/{investorId}/buylock/{id}", method = RequestMethod.PUT)
	Response<BuyRecordDto> buyLock(@PathVariable("investorId") Long investorId, @PathVariable("id") Long id,
			@RequestParam(name = "delegateNumber") String delegateNumber);

	/**
	 * 投资人买入股票，此时状态为“持仓中”
	 *
	 * @param investorId
	 *            投资人ID
	 * @param id
	 *            点买记录id
	 * @param buyingPrice
	 *            买入价格
	 * @return 点买记录
	 */
	@RequestMapping(value = "/{investorId}/buyinto/{id}", method = RequestMethod.PUT)
	Response<BuyRecordDto> buyInto(@PathVariable("investorId") Long investorId, @PathVariable("id") Long id,
			@RequestParam(name = "buyingPrice") BigDecimal buyingPrice);

	/**
	 * 用户申请卖出或者投资人卖出股票锁定，此时状态为“卖出锁定”
	 *
	 * @param lockUserId
	 *            发布人ID或者投资人ID
	 * @param id
	 *            点买记录id
	 * @return 点买记录
	 */
	@RequestMapping(value = "/{lockUserId}/selllock/{id}", method = RequestMethod.PUT)
	Response<BuyRecordDto> sellLock(@PathVariable("lockUserId") Long lockUserId, @PathVariable("id") Long id,
			@RequestParam(name = "windControlTypeIndex") String windControlTypeIndex);

	/**
	 * 投资人卖出股票，此时状态为“已平仓”
	 *
	 * @param investorId
	 *            投资人ID
	 * @param id
	 *            点买记录id
	 * @param sellingPrice
	 *            卖出价格
	 * @param profitDistributionRatio
	 *            盈利分配比例
	 * @return 点买记录
	 */
	@RequestMapping(value = "/{investorId}/sellout/{id}", method = RequestMethod.PUT)
	Response<BuyRecordDto> sellOut(@PathVariable("investorId") Long investorId, @PathVariable("id") Long id,
			@RequestParam(name = "sellingPrice") BigDecimal sellingPrice,
			@RequestParam(name = "profitDistributionRatio") BigDecimal profitDistributionRatio);

	/**
	 * 分页查询点买记录
	 *
	 * @param buyRecordQuery
	 *            查询条件
	 * @return 点买记录
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<BuyRecordDto>> pagesByQuery(@RequestBody BuyRecordQuery buyRecordQuery);

}
