package com.waben.stock.datalayer.organization.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.organization.repository.DynamicQuerySqlDao;
import com.waben.stock.datalayer.organization.repository.impl.MethodDesc;
import com.waben.stock.interfaces.dto.organization.CustomerDto;
import com.waben.stock.interfaces.pojo.query.organization.CustomerQuery;

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

	public Page<CustomerDto> pagesByQuery(CustomerQuery query) {
		String publisherIdCondition = "";
		if (query.getPublisherId() != null && !"".equals(query.getPublisherId().trim())) {
			publisherIdCondition = " and t1.id='" + query.getPublisherId().trim() + "' ";
		}
		String publisherPhoneCondition = "";
		if (query.getPublisherPhone() != null && !"".equals(query.getPublisherPhone())) {
			publisherPhoneCondition = " and t1.phone like '%" + query.getPublisherPhone().trim() + "%' ";
		}
		String orgCodeCondition = "";
		if (query.getOrgCode() != null && !"".equals(query.getOrgCode().trim())) {
			orgCodeCondition = " and t4.code like '%" + query.getOrgCode() + "%' ";
		}
		String orgNameCondition = "";
		if (query.getOrgName() != null && !"".equals(query.getOrgName())) {
			orgNameCondition = " and t4.name like '%" + query.getOrgName() + "%' ";
		}
		String sql = String.format(
				"select t1.id, t1.phone, t4.code, t4.name, t1.create_time, t3.balance, t3.available_balance, t3.frozen_capital, t1.end_type from publisher t1 "
						+ "INNER JOIN p_organization_publisher t2 on t1.id=t2.publisher_id and t2.org_code like '%s%%' "
						+ "LEFT JOIN p_organization t4 on t2.org_code=t4.code "
						+ "LEFT JOIN capital_account t3 on t1.id=t3.publisher_id where 1=1 %s %s %s %s limit "
						+ query.getPage() * query.getSize() + "," + query.getSize(),
				query.getCurrentOrgCode(), publisherIdCondition, publisherPhoneCondition, orgCodeCondition,
				orgNameCondition);
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"), sql.indexOf("limit"));
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setPublisherId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setPublisherPhone", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setOrgCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setOrgName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setCreateTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setBalance", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setAvailableBalance", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setFrozenCapital", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(8), new MethodDesc("setEndType", new Class<?>[] { String.class }));
		List<CustomerDto> content = dynamicQuerySqlDao.execute(CustomerDto.class, sql, setMethodMap);
		BigInteger totalElements = dynamicQuerySqlDao.executeComputeSql(countSql);
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()), totalElements.longValue());
	}

}
