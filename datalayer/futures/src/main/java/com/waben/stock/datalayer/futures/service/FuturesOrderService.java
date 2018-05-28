package com.waben.stock.datalayer.futures.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.futures.repository.FuturesOrderDao;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTradeAdminDto;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;
import com.waben.stock.interfaces.util.StringUtil;

@Service
public class FuturesOrderService {

	Logger logger = LoggerFactory.getLogger(getClass());

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private FuturesOrderDao futuresOrderDao;
	
	public Page<FuturesTradeAdminDto> adminPagesByQuery(FuturesTradeAdminQuery query) {
		String publisherNameCondition = "";
		if (!StringUtil.isEmpty(query.getPublisherName())) {
			publisherNameCondition = " and t4.name like '%" + query.getPublisherName() + "%' ";
		}
		String publisherPhoneCondition = "";
		if (!StringUtil.isEmpty(query.getPublisherPhone())) {
			publisherPhoneCondition = " and t3.phone like '%" + query.getPublisherPhone() + "%' ";
		}
		List<FuturesTradeAdminDto> content = new ArrayList<FuturesTradeAdminDto>();
		BigInteger totalElements = new BigInteger("10");
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()),
				totalElements != null ? totalElements.longValue() : 0);
	}
}
