package com.waben.stock.applayer.tactics.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.dto.publisher.PromotionBaseDto;
import com.waben.stock.applayer.tactics.service.CapitalFlowService;
import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 推广 Business
 * 
 * @author luomengan
 *
 */
@Service
public class PromotionBusiness {

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private CapitalFlowService capitalFlowService;

	public PromotionBaseDto getPromotionBase(Long publisherId) {
		PromotionBaseDto result = new PromotionBaseDto();
		result.setPromotionCode(findById(publisherId).getPromotionCode());
		result.setPromotionCount(promotionCount(publisherId));
		result.setPromotionProfit(promotionTotalAmount(publisherId));
		return result;
	}

	public PublisherDto findById(Long publisherId) {
		Response<PublisherDto> publisherResp = publisherService.fetchById(publisherId);
		if ("200".equals(publisherResp.getCode())) {
			return publisherResp.getResult();
		}
		throw new ServiceException(publisherResp.getCode());
	}

	public Integer promotionCount(Long publisherId) {
		Response<Integer> promotionCountResp = publisherService.promotionCount(publisherId);
		if ("200".equals(promotionCountResp.getCode())) {
			return promotionCountResp.getResult();
		}
		throw new ServiceException(promotionCountResp.getCode());
	}

	public BigDecimal promotionTotalAmount(Long publisherId) {
		Response<BigDecimal> promotionTotalAmountResp = capitalFlowService.promotionTotalAmount(publisherId);
		if ("200".equals(promotionTotalAmountResp.getCode())) {
			return promotionTotalAmountResp.getResult();
		}
		throw new ServiceException(promotionTotalAmountResp.getCode());
	}

	public PageInfo<PublisherDto> pagePromotionUser(Long publisherId, int page, int size) {
		Response<PageInfo<PublisherDto>> pagePromotionUserResp = publisherService.pagePromotionUser(publisherId, page,
				size);
		if ("200".equals(pagePromotionUserResp.getCode())) {
			return pagePromotionUserResp.getResult();
		}
		throw new ServiceException(pagePromotionUserResp.getCode());
	}

}
