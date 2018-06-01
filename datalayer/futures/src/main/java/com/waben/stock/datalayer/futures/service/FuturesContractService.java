package com.waben.stock.datalayer.futures.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.entity.FuturesExchange;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.repository.DynamicQuerySqlDao;
import com.waben.stock.datalayer.futures.repository.FuturesContractDao;
import com.waben.stock.datalayer.futures.repository.impl.MethodDesc;
import com.waben.stock.interfaces.dto.admin.futures.FuturesContractAdminDto;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.enums.OrganizationState;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesContractAdminQuery;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;
import com.waben.stock.interfaces.util.StringUtil;

/**
 * 期货合约 service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesContractService {

	@Autowired
	private DynamicQuerySqlDao sqlDao;

	@Autowired
	private FuturesContractDao futuresContractDao;
	
	@Autowired
	private FuturesOrderService orderService;
	
	

	public Page<FuturesContract> pagesContract(final FuturesContractQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<FuturesContract> pages = futuresContractDao.page(new Specification<FuturesContract>() {

			@Override
			public Predicate toPredicate(Root<FuturesContract> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// Join<FuturesExchange, FuturesContract> parentJoin =
				// root.join("exchange", JoinType.LEFT);
				if (query.getContractId() != null && query.getContractId() != 0) {
					predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), query.getContractId()));
				}
				if (!StringUtil.isEmpty(query.getSymbol())) {
					predicateList.add(criteriaBuilder.equal(root.get("symbol").as(String.class), query.getSymbol()));
				}

				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}

				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public Page<FuturesContract> pagesContractAdmin(final FuturesContractAdminQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<FuturesContract> pages = futuresContractDao.page(new Specification<FuturesContract>() {

			@Override
			public Predicate toPredicate(Root<FuturesContract> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Join<FuturesContract, FuturesExchange> join = root.join("exchange", JoinType.LEFT);
				if(query.getExchangcode() != null && !"".equals(query.getExchangcode())){
					predicateList.add(criteriaBuilder.or(criteriaBuilder.like(join.get("code").as(String.class), query.getExchangcode()+"%"),criteriaBuilder.like(join.get("name").as(String.class), query.getExchangcode()+"%")));
				}
				

				if (query.getSymbol() != null && !"".equals(query.getSymbol())) {
					predicateList.add(criteriaBuilder.equal(root.get("symbol").as(String.class), query.getSymbol()));
				}

				if (query.getName() != null && !"".equals(query.getName())) {
					predicateList.add(criteriaBuilder.equal(root.get("name").as(String.class), query.getName()));
				}

				if (query.getProductType() != null && !"".equals(query.getProductType())) {
					predicateList.add(
							criteriaBuilder.equal(root.get("productType").as(String.class), query.getProductType()));
				}

				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}

				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public Page<FuturesContractAdminDto> pagesByAdminQuery(final FuturesContractAdminQuery query) {
		String exchangeCodeCondition = "";
		if (!StringUtil.isEmpty(query.getExchangcode())) {
			exchangeCodeCondition = " and f2.code like '%" + query.getExchangcode() + "%' ";
		}
		String exchangeNameCondition = "";
		if (!StringUtil.isEmpty(query.getExchangename())) {
			exchangeNameCondition = " and f2.name like '%" + query.getExchangename() + "%' ";
		}
		String contractCodeCondition = "";
		if (!StringUtil.isEmpty(query.getSymbol())) {
			contractCodeCondition = " and f1.symbol like '%" + query.getSymbol() + "%' ";
		}
		String productTypeCondition = "";
		if (!StringUtil.isEmpty(query.getProductType())) {
			productTypeCondition = " and f1.product_type like '%" + query.getProductType() + "%' ";
		}
		String sql = String.format(
				"select f1.id,f2.code as exchangecode,f2.name as exchangename,f2.exchange_type as exchangeType,f1.code,f1.name,"
						+ "f1.currency,f3.rate,f1.product_type as productType,f1.multiplier,f1.min_wave,f1.per_wave_money,f1.per_unit_reserve_fund,"
						+ "f1.per_unit_unwind_point,f1.unwind_point_type,f1.total_limit,f1.per_order_limit,f1.openwind_service_fee,"
						+ "f1.unwind_service_fee,f1.overnight_time,f1.overnight_per_unit_deferred_fee,f1.overnight_per_unit_reserve_fund,"
						+ "f1.enable "
						+ " from f_futures_contract f1 LEFT JOIN f_futures_exchange f2 on f1.exchange_id = f2.id"
						+ " LEFT JOIN futures_currency_rate f3 on f1.rate_id = f3.id " + " where 1=1 %s %s %s %s limit "
						+ query.getPage() * query.getSize() + "," + query.getSize(),
				exchangeCodeCondition, exchangeNameCondition, contractCodeCondition, productTypeCondition);
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"), sql.length() - 3);
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setExchangecode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setExchangename", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setExchangeType", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setCurrency", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setRate", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(8), new MethodDesc("setProductType", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(9), new MethodDesc("setMultiplier", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(10), new MethodDesc("setMinWave", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(11), new MethodDesc("setPerWaveMoney", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(12), new MethodDesc("setPerUnitReserveFund", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(13), new MethodDesc("setPerUnitUnwindPoint", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(14), new MethodDesc("setUnwindPointType", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(15), new MethodDesc("setTotalLimit", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(16), new MethodDesc("setPerOrderLimit", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(17), new MethodDesc("setOpenwindServiceFee", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(18), new MethodDesc("setUnwindServiceFee", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(19), new MethodDesc("setOvernightTime", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(20),
				new MethodDesc("setOvernightPerUnitDeferredFee", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(23),
				new MethodDesc("setOvernightPerUnitReserveFund", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(22), new MethodDesc("setEnable", new Class<?>[] { Boolean.class }));
		List<FuturesContractAdminDto> content = sqlDao.execute(FuturesContractAdminDto.class, sql, setMethodMap);
		BigInteger totalElements = sqlDao.executeComputeSql(countSql);
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()),
				totalElements != null ? totalElements.longValue() : 0);
	}

	public Page<FuturesContractDto> pagesByQuery(final FuturesContractQuery query) {

		String sql = String
				.format("select t4.id, t4.parent_id, t4.code, t4.name, t4.level, t4.state, t4.create_time, t5.promotion_count, IF(t6.pid is null, 0, t6.children_count) as children_count, IFNULL(t7.available_balance, 0) as available_balance, t8.name as bind_name, t8.phone as bing_phone, (SELECT settlement_type FROM settlement_method LIMIT 1) AS ws_type, t4.bill_charge from p_organization t4 "
						+ "LEFT JOIN "
						+ "(select t0.id, sum(t3.promotion_count) as promotion_count from p_organization t0, "
						+ "(select t1.id, t1.parent_id, IF(t2.id is null, 0, count(t1.id)) as promotion_count from p_organization t1 "
						+ "LEFT JOIN p_organization_publisher t2 on t1.code=t2.org_code group by t1.id) t3 where t0.level=1 or (t0.level>1 and (t0.id=t3.id or t0.id=t3.parent_id)) group by t0.id) as t5 on t4.id=t5.id "
						+ "LEFT JOIN "
						+ "((select parent_id as pid, count(parent_id) as children_count from p_organization where parent_id is not null group by parent_id having pid!=1) "
						+ "union all "
						+ "(select 1 as pid, (count(*)-1) as children_count from p_organization)) as t6 on t4.id=t6.pid "
						+ "LEFT JOIN p_organization_account t7 on t4.id=t7.org_id "
						+ "LEFT JOIN bind_card t8 on t8.resource_type=2 and t8.resource_id=t4.id "
						+ "where 1=1 %s %s %s order by t4.level desc, t4.create_time asc limit "
						+ query.getPage() * query.getSize() + "," + query.getSize());
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"), sql.indexOf("limit"));
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setParentId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setLevel", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setState", new Class<?>[] { OrganizationState.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setCreateTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setPromotionCount", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(8), new MethodDesc("setChildrenCount", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(9), new MethodDesc("setAvailableBalance", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(10), new MethodDesc("setBindName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(11), new MethodDesc("setBingPhone", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(12), new MethodDesc("setWsType", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(13), new MethodDesc("setBillCharge", new Class<?>[] { BigDecimal.class }));
		List<FuturesContractDto> content = sqlDao.execute(FuturesContractDto.class, sql, setMethodMap);
		BigInteger totalElements = sqlDao.executeComputeSql(countSql);
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()),
				totalElements != null ? totalElements.longValue() : 0);
	}

	public FuturesContract saveExchange(FuturesContract exchange) {
		return futuresContractDao.create(exchange);
	}

	public FuturesContract modifyExchange(FuturesContract exchange) {
		return futuresContractDao.update(exchange);
	}

	public FuturesContract findByContractId(Long contractId) {
		return futuresContractDao.retrieve(contractId);
	}
	
	public String deleteExchange(Long id){
		List<Long> contractId = new ArrayList<Long>();
		contractId.add(id);
		List<FuturesOrder> list = orderService.findByContractId(contractId);
		if(list.size()>0){
			return "该合约正在被订单使用，请不要删除";
		}

		futuresContractDao.delete(id);
		return "删除成功";
	}

	public List<FuturesContractTerm> findByListContractId(Long contractId) {
		return futuresContractDao.findByListContractId(contractId);
	}

	public Page<FuturesContract> pagesContractAdmin(final FuturesContract query, int page, int limit) {
		Pageable pageable = new PageRequest(page, limit);
		Page<FuturesContract> pages = futuresContractDao.page(new Specification<FuturesContract>() {

			@Override
			public Predicate toPredicate(Root<FuturesContract> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// Join<FuturesExchange, FuturesContract> parentJoin =
				// root.join("exchange", JoinType.LEFT);
				if (query.getSymbol() != null && !"".equals(query.getSymbol())) {
					predicateList.add(criteriaBuilder.equal(root.get("symbol").as(String.class), query.getSymbol()));
				}
				if (query.getName() != null && !"".equals(query.getName())) {
					predicateList.add(criteriaBuilder.equal(root.get("name").as(String.class), query.getName()));
				}
				if (query.getOvernightTime() != null && !"".equals(query.getOvernightTime())) {
					predicateList.add(criteriaBuilder.equal(root.get("overnightTime").as(String.class),
							query.getOvernightTime()));
				}
				if (query.getExchange() != null && query.getExchange().hashCode() > 0) {
					predicateList.add(
							criteriaBuilder.equal(root.get("exchange").as(FuturesExchange.class), query.getExchange()));
				}

				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}

				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}
}

