package com.waben.stock.applayer.tactics.business;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.dto.buyrecord.BuyRecordWithMarketDto;
import com.waben.stock.applayer.tactics.reference.CapitalAccountReference;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.WithdrawalsState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 资金账户 Business
 * 
 * @author luomengan
 *
 */
@Service
public class CapitalAccountBusiness {

	@Autowired
	@Qualifier("capitalAccountReference")
	private CapitalAccountReference service;

	@Autowired
	private BuyRecordBusiness buyRecordBusiness;

	public CapitalAccountDto findByPublisherId(Long publisherId) {
		Response<CapitalAccountDto> response = service.fetchByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto recharge(Long publisherId, BigDecimal amount) {
		Response<CapitalAccountDto> response = service.recharge(publisherId, amount);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}
	
	public CapitalAccountDto withdrawals(Long publisherId, String withdrawalsNo, WithdrawalsState state) {
		Response<CapitalAccountDto> response = service.withdrawals(publisherId, withdrawalsNo, state.getIndex());
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BigDecimal getHoldProfitOrLoss(Long publisherId) {
		BigDecimal result = BigDecimal.valueOf(0);

		BuyRecordQuery query = new BuyRecordQuery(0, Integer.MAX_VALUE, SecurityUtil.getUserId(),
				new BuyRecordState[] { BuyRecordState.POSTED, BuyRecordState.BUYLOCK, BuyRecordState.HOLDPOSITION,
						BuyRecordState.SELLAPPLY, BuyRecordState.SELLLOCK });
		PageInfo<BuyRecordDto> response = buyRecordBusiness.pages(query);
		List<BuyRecordDto> content = response.getContent();
		if (content != null && content.size() > 0) {
			List<BuyRecordWithMarketDto> marketContent = buyRecordBusiness.wrapMarketInfo(content);
			for (BuyRecordWithMarketDto market : marketContent) {
				result = result.add(market.getProfitOrLoss());
			}
		}

		return result;
	}

	public BigDecimal getTotalApplyAmount(Long publisherId) {
		BigDecimal result = BigDecimal.valueOf(0);

		BuyRecordQuery query = new BuyRecordQuery(0, Integer.MAX_VALUE, SecurityUtil.getUserId(),
				new BuyRecordState[] { BuyRecordState.POSTED, BuyRecordState.BUYLOCK, BuyRecordState.HOLDPOSITION,
						BuyRecordState.SELLAPPLY, BuyRecordState.SELLLOCK, BuyRecordState.UNWIND });
		PageInfo<BuyRecordDto> response = buyRecordBusiness.pages(query);
		List<BuyRecordDto> content = response.getContent();
		if (content != null && content.size() > 0) {
			for (BuyRecordDto buyRecord : content) {
				result = result.add(buyRecord.getApplyAmount());
			}
		}

		return result;
	}

	public BigDecimal getTodayApplyAmount(Long publisherId) {
		BigDecimal result = BigDecimal.valueOf(0);

		BuyRecordQuery query = new BuyRecordQuery(0, Integer.MAX_VALUE, SecurityUtil.getUserId(),
				new BuyRecordState[] { BuyRecordState.POSTED, BuyRecordState.BUYLOCK, BuyRecordState.HOLDPOSITION,
						BuyRecordState.SELLAPPLY, BuyRecordState.SELLLOCK, BuyRecordState.UNWIND });
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		query.setStartCreateTime(cal.getTime());
		PageInfo<BuyRecordDto> response = buyRecordBusiness.pages(query);
		List<BuyRecordDto> content = response.getContent();
		if (content != null && content.size() > 0) {
			for (BuyRecordDto buyRecord : content) {
				result = result.add(buyRecord.getApplyAmount());
			}
		}

		return result;
	}

	public BigDecimal getDeferredAmount(Long publisherId) {
		// TODO 获取递延费用
		return new BigDecimal(0);
	}

}
