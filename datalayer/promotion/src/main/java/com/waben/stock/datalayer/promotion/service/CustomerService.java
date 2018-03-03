package com.waben.stock.datalayer.promotion.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waben.stock.datalayer.promotion.pojo.bean.CustomerBean;
import com.waben.stock.datalayer.promotion.pojo.bean.MethodDesc;
import com.waben.stock.datalayer.promotion.pojo.bean.PromotionBuyRecordBean;
import com.waben.stock.datalayer.promotion.pojo.query.CustomerQuery;
import com.waben.stock.datalayer.promotion.pojo.query.PromotionBuyRecordQuery;
import com.waben.stock.datalayer.promotion.repository.DynamicQuerySqlDao;
import com.waben.stock.datalayer.promotion.retrivestock.RetriveStockOverHttp;
import com.waben.stock.datalayer.promotion.retrivestock.bean.StockMarket;

/**
 * 客户 Service
 * 
 * @author luomengan
 *
 */
@Service
public class CustomerService {

	@Autowired
	private DynamicQuerySqlDao dynamicQuerySqlDao;
	
	@Autowired
	private RestTemplate restTemplate;

	public Page<PromotionBuyRecordBean> pagesByQuery(PromotionBuyRecordQuery query) {
		String buyRecordIdCondition = "";
		if(query.getBuyRecordId() != null && !"".equals(query.getBuyRecordId().trim())) {
			buyRecordIdCondition = " and t1.buy_record_id='" + query.getBuyRecordId().trim() + "' ";
		}
		String stateCondition = "";
		if(query.getState() != null && !"".equals(query.getState()) && !"0".equals(query.getState())) {
			if("8-1".equals(stateCondition)) {
				stateCondition = " and t2.state='8' and t2.wind_control_type is null ";
			} else {
				stateCondition = " and t2.state='" + query.getState() + "' ";
			}
		}
		String publisherPhoneCondition = "";
		if(query.getPublisherPhone() != null && !"".equals(query.getPublisherPhone().trim())) {
			publisherPhoneCondition = " and t1.publisher_phone like '%" + query.getPublisherPhone() + "%' ";
		}
		String orgCodeCondition = "";
		if(query.getOrgCode() != null && !"".equals(query.getOrgCode())) {
			orgCodeCondition = " and t4.code='" + query.getOrgCode() + "' ";
		}
		String sql = String
				.format("select t1.buy_record_id, t1.publisher_id, t1.publisher_phone, t1.stock_code, t1.stock_name, t2.strategy_type_id, "
						+ "t3.name as strategy_type_name, t1.apply_amount, t1.number_of_strand, t2.state, t2.wind_control_type, "
						+ "t2.buying_time, t2.buying_price, t2.selling_price, t2.selling_time, t4.code as org_code, t4.name as org_name from p_buy_record t1 "
						+ "LEFT JOIN buy_record t2 on t1.buy_record_id=t2.id "
						+ "LEFT JOIN strategy_type t3 on t2.strategy_type_id=t3.id "
						+ "LEFT JOIN p_organization t4 on t1.org_id=t4.id where 1=1 %s %s %s %s limit " + query.getPage() * query.getSize() + "," + query.getSize(), buyRecordIdCondition, stateCondition, publisherPhoneCondition, orgCodeCondition);
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"));
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setBuyRecordId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setPublisherId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setPublisherPhone", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setStockCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setStockName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setStrategyTypeId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setStrategyTypeName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setApplyAmount", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(8), new MethodDesc("setNumberOfStrand", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(9), new MethodDesc("setState", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(10), new MethodDesc("setWindControlType", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(11), new MethodDesc("setBuyingTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(12), new MethodDesc("setBuyingPrice", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(13), new MethodDesc("setSellingPrice", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(14), new MethodDesc("setSellingTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(15), new MethodDesc("setOrgCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(16), new MethodDesc("setOrgName", new Class<?>[] { String.class }));
		List<PromotionBuyRecordBean> content = dynamicQuerySqlDao.execute(PromotionBuyRecordBean.class, sql, setMethodMap);
		BigInteger totalElements = dynamicQuerySqlDao.executeComputeSql(countSql);
		// 获取股票的最新价
		List<String> codes = new ArrayList<>();
		for (PromotionBuyRecordBean record : content) {
			codes.add(record.getStockCode());
		}
		if (codes.size() > 0) {
			List<StockMarket> stockMarketList = RetriveStockOverHttp.listStockMarket(restTemplate, codes);
			if (stockMarketList != null && stockMarketList.size() > 0) {
				for (int i = 0; i < stockMarketList.size(); i++) {
					StockMarket market = stockMarketList.get(i);
					PromotionBuyRecordBean record = content.get(i);
					record.setLastPrice(market.getLastPrice());
				}
			}
		}
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()), totalElements.longValue());
	}

	public Page<CustomerBean> pagesByQuery(CustomerQuery query) {
		String publisherIdCondition = "";
		if(query.getPublisherId() != null && !"".equals(query.getPublisherId().trim())) {
			publisherIdCondition = " and t1.id='" + query.getPublisherId().trim() + "' ";
		}
		String publisherPhoneCondition = "";
		if(query.getPublisherPhone() != null && !"".equals(query.getPublisherPhone())) {
			publisherPhoneCondition = " and t1.phone like '%" + query.getPublisherPhone().trim() + "%' ";
		}
		String orgCodeCondition = "";
		if(query.getOrgCode() != null && !"".equals(query.getOrgCode().trim())) {
			orgCodeCondition = " and t2.code like '%" + query.getOrgCode() + "%' ";
		}
		String orgNameCondition = "";
		if(query.getOrgName() != null && !"".equals(query.getOrgName())) {
			orgNameCondition = " and t2.name='" + query.getOrgName() + "' ";
		}
		String sql = String
				.format("select t1.id, t1.phone, t2.code, t2.name, t1.create_time, t3.balance, t3.available_balance, t3.frozen_capital from publisher t1 "
						+ "INNER JOIN p_organization t2 on t1.promotion_org_code=t2.code "
						+ "LEFT JOIN capital_account t3 on t1.id=t3.publisher_id where 1=1 %s %s %s %s limit " + query.getPage() * query.getSize() + "," + query.getSize(), publisherIdCondition, publisherPhoneCondition, orgCodeCondition, orgNameCondition);
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"));
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setPublisherId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setPublisherPhone", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setOrgCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setOrgName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setCreateTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setBalance", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setAvailableBalance", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setFrozenCapital", new Class<?>[] { BigDecimal.class }));
		List<CustomerBean> content = dynamicQuerySqlDao.execute(CustomerBean.class, sql, setMethodMap);
		BigInteger totalElements = dynamicQuerySqlDao.executeComputeSql(countSql);
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()), totalElements.longValue());
	}

}
