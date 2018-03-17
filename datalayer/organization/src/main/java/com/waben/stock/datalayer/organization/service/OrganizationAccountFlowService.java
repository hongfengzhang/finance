package com.waben.stock.datalayer.organization.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.entity.OrganizationAccountFlow;
import com.waben.stock.datalayer.organization.repository.DynamicQuerySqlDao;
import com.waben.stock.datalayer.organization.repository.OrganizationAccountFlowDao;
import com.waben.stock.datalayer.organization.repository.impl.MethodDesc;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowWithTradeInfoDto;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;

/**
 * 机构账户流水 Service
 *
 * @author luomengan
 */
@Service
public class OrganizationAccountFlowService {

    @Autowired
    private OrganizationAccountFlowDao organizationAccountFlowDao;
    
    @Autowired
	private DynamicQuerySqlDao dynamicQuerySqlDao;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public OrganizationAccountFlow getOrganizationAccountFlowInfo(Long id) {
        return organizationAccountFlowDao.retrieve(id);
    }

    @Transactional
    public OrganizationAccountFlow addOrganizationAccountFlow(OrganizationAccountFlow organizationAccountFlow) {
        return organizationAccountFlowDao.create(organizationAccountFlow);
    }

    @Transactional
    public OrganizationAccountFlow modifyOrganizationAccountFlow(OrganizationAccountFlow organizationAccountFlow) {
        return organizationAccountFlowDao.update(organizationAccountFlow);
    }

    @Transactional
    public Page<OrganizationAccountFlow> pagesByQuery(final OrganizationAccountFlowQuery query) {
        Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<OrganizationAccountFlow> pages = organizationAccountFlowDao.page(new Specification<OrganizationAccountFlow>() {
            @Override
            public Predicate toPredicate(Root<OrganizationAccountFlow> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (query.getOrgId() != null) {
                    Join<Organization, OrganizationAccountFlow> join = root.join("org", JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get("id").as(Long.class), query.getOrgId()));
                }
                if (!StringUtils.isBlank(query.getOrgName())){
                    Join<Organization, OrganizationAccountFlow> join = root.join("org", JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get("name"), query.getOrgName()));
                }
                if (!StringUtils.isBlank(query.getOrgCode())){
                    Join<Organization, OrganizationAccountFlow> join = root.join("org", JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get("code"), query.getOrgCode()));
                }
                if (!StringUtils.isBlank(query.getFlowNo())) {
                    predicates.add(criteriaBuilder.equal(root.get("flowNo"), query.getFlowNo()));
                }
                if (query.getResourceType() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("resourceType").as(Long.class), query.getResourceType()));
                }
                if (query.getFlowType() != null && query.getFlowType().longValue() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("type").as(Long.class), query.getFlowType()));
                }
                if (query.getStartTime() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("occurrenceTime").as(Date.class),
                            query.getStartTime()));
                }
                if (query.getEndTime() != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get("occurrenceTime").as(Date.class),
                            query.getEndTime()));
                }
                if (query.getOrgIds()!= null && query.getOrgIds().size() > 0) {
                    Join<Organization, OrganizationAccountFlow> join = root.join("org", JoinType.LEFT);
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(join.get("id"));
                    for (Long id : query.getOrgIds()) {
                        in.value(id);
                    }
                    predicates.add(in);
                }
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("occurrenceTime").as(Date.class)));
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }
    
    public Page<OrganizationAccountFlowWithTradeInfoDto> pagesWithTradeInfoByQuery(OrganizationAccountFlowQuery query) {
    	String orgIdCondition = "";
    	if (query.getOrgId() != null) {
    		orgIdCondition = " and t1.org_id='" + query.getOrgId() + "' ";
		}
		String typeCondition = "";
		if (query.getFlowType() != null && query.getFlowType().longValue() != 0) {
			typeCondition = " and t1.type='" + query.getFlowType() + "' ";
		}
		String startTimeCondition = "";
		if (query.getStartTime() != null) {
			typeCondition = " and t1.occurrence_time>='" + sdf.format(query.getStartTime()) + "' ";
        }
		String endTimeCondition = "";
        if (query.getEndTime() != null) {
        	typeCondition = " and t1.occurrence_time<'" + sdf.format(query.getEndTime()) + "' ";
        }
		String sql = String
				.format("select t1.id, t1.amount, t1.flow_no, t1.occurrence_time, t1.origin_amount, t1.remark, t1.resource_id, t1.resource_trade_no, t1.resource_type, t1.type, t1.org_id, "
						+ "t2.publisher_id as b_publisher_id, t2.publisher_phone as b_publisher_phone, t2.stock_code as b_stock_code, t2.stock_name as b_stock_name, "
						+ "t3.publisher_id as s_publisher_id, t3.publisher_phone as s_publisher_phone, t3.stock_code as s_stock_code, t3.stock_name as s_stock_name from p_organization_account_flow t1 "
						+ "LEFT JOIN buy_record t2 on t1.resource_type=1 and t1.resource_id=t2.id "
						+ "LEFT JOIN stock_option_trade t3 on t1.resource_type=3 and t1.resource_id=t3.id where 1=1 %s %s %s %s order by t1.occurrence_time desc limit " + query.getPage() * query.getSize() + "," + query.getSize(),
						orgIdCondition, typeCondition, startTimeCondition, endTimeCondition);
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"), sql.indexOf("limit"));
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setAmount", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setFlowNo", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setOccurrenceTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setOriginAmount", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setRemark", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setResourceId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setResourceTradeNo", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(8), new MethodDesc("setResourceType", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(9), new MethodDesc("setType", new Class<?>[] { Integer.class }));
		setMethodMap.put(new Integer(10), new MethodDesc("setOrgId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(11), new MethodDesc("setbPublisherId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(12), new MethodDesc("setbPublisherPhone", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(13), new MethodDesc("setbStockCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(14), new MethodDesc("setbStockName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(15), new MethodDesc("setsPublisherId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(16), new MethodDesc("setsPublisherPhone", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(17), new MethodDesc("setsStockCode", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(18), new MethodDesc("setsStockName", new Class<?>[] { String.class }));
		List<OrganizationAccountFlowWithTradeInfoDto> content = dynamicQuerySqlDao.execute(OrganizationAccountFlowWithTradeInfoDto.class, sql, setMethodMap);
		BigInteger totalElements = dynamicQuerySqlDao.executeComputeSql(countSql);
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()), totalElements != null ? totalElements.longValue() : 0);
	}

    //查询所有流水
    @Transactional
    public Page<OrganizationAccountFlow> pagesByOrgParentQuery(final OrganizationAccountFlowQuery query) {
        final Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<OrganizationAccountFlow> pages = organizationAccountFlowDao.page(new Specification<OrganizationAccountFlow>() {
            @Override
            public Predicate toPredicate(Root<OrganizationAccountFlow> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isBlank(query.getFlowNo())) {
                    predicates.add(criteriaBuilder.equal(root.get("flowNo"), query.getFlowNo()));
                }
                if (query.getResourceType() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("resourceType"), query.getResourceType()));
                }
//                criteriaQuery.select(root.<Long>get("amount"));
//                criteriaQuery.multiselect(criteriaBuilder.tuple(criteriaBuilder.sum(root.get("amount")),""));
                criteriaQuery.groupBy(root.get("org"));
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }

    @Transactional
    public void deleteOrganizationAccountFlow(Long id) {
        organizationAccountFlowDao.delete(id);
    }

    @Transactional
    public void deleteOrganizationAccountFlows(String ids) {
        if (ids != null) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                if (!"".equals(id.trim())) {
                    organizationAccountFlowDao.delete(Long.parseLong(id.trim()));
                }
            }
        }
    }

    public Page<OrganizationAccountFlow> organizationAccountFlows(int page, int limit) {
        return organizationAccountFlowDao.page(page, limit);
    }

    public List<OrganizationAccountFlow> list() {
        return organizationAccountFlowDao.list();
    }

}
