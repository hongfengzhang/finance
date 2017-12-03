package com.waben.stock.applayer.tactics.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.dto.publisher.CapitalFlowWithExtendDto;
import com.waben.stock.applayer.tactics.service.CapitalFlowService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowExtendDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.CapitalFlowExtendType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 资金流水 Business
 * 
 * @author luomengan
 *
 */
@Service
public class CapitalFlowBusiness {

	@Autowired
	private CapitalFlowService service;

	@Autowired
	private BuyRecordBusiness buyRecordBusiness;

	@Autowired
	private StockBusiness stockBusiness;

	public PageInfo<CapitalFlowWithExtendDto> pages(CapitalFlowQuery query) {
		Response<PageInfo<CapitalFlowDto>> response = service.pagesByQuery(query);
		if ("200".equals(response.getCode())) {
			List<CapitalFlowWithExtendDto> content = CopyBeanUtils
					.copyListBeanPropertiesToList(response.getResult().getContent(), CapitalFlowWithExtendDto.class);
			PageInfo<CapitalFlowWithExtendDto> result = new PageInfo<>(content, response.getResult().getTotalPages(),
					response.getResult().getLast(), response.getResult().getTotalElements(),
					response.getResult().getSize(), response.getResult().getNumber(), response.getResult().getFrist());
			// 获取股票代码和股票名称
			for (int i = 0; i < response.getResult().getContent().size(); i++) {
				CapitalFlowDto flow = response.getResult().getContent().get(i);
				CapitalFlowWithExtendDto flowWithExtend = content.get(i);
				if (flow.getExtendList() != null && flow.getExtendList().size() > 0) {
					for (CapitalFlowExtendDto extend : flow.getExtendList()) {
						if (extend.getExtendType() == CapitalFlowExtendType.BUYRECORD) {
							BuyRecordDto buyRecord = buyRecordBusiness.findById(extend.getExtendId());
							if (buyRecord != null) {
								flowWithExtend.setStockCode(buyRecord.getStockCode());
								StockDto stock = stockBusiness.findByCode(buyRecord.getStockCode());
								if (stock != null) {
									flowWithExtend.setStockName(stock.getName());
								}
							}
						}
					}
				}
			}
			return result;
		}
		throw new ServiceException(response.getCode());
	}

}
