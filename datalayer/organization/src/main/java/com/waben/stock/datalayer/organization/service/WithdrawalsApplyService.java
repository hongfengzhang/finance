package com.waben.stock.datalayer.organization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.entity.WithdrawalsApply;
import com.waben.stock.datalayer.organization.repository.OrganizationDao;
import com.waben.stock.datalayer.organization.repository.WithdrawalsApplyDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.WithdrawalsApplyState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.WithdrawalsApplyQuery;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

/**
 * 提现申请 Service
 * 
 * @author luomengan
 *
 */
@Service
public class WithdrawalsApplyService {

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private WithdrawalsApplyDao withdrawalsApplyDao;

	@Autowired
	private OrganizationAccountService accountService;
	
	public WithdrawalsApply findById(Long id) {
		return withdrawalsApplyDao.retrieve(id);
	}
	
	public WithdrawalsApply findByApplyNo(String applyNo) {
		return withdrawalsApplyDao.retrieveByApplyNo(applyNo);
	}

	@Transactional
	public WithdrawalsApply addWithdrawalsApply(WithdrawalsApply withdrawalsApply, Long orgId) {
		Organization org = organizationDao.retrieve(orgId);
		if (org == null) {
			throw new ServiceException(ExceptionConstant.ORGANIZATION_NOTEXIST_EXCEPTION);
		}
		withdrawalsApply.setOrg(org);
		Date date = new Date();
		withdrawalsApply.setApplyTime(date);
		withdrawalsApply.setUpdateTime(date);
		withdrawalsApply.setState(WithdrawalsApplyState.TOBEAUDITED);
		withdrawalsApply.setApplyNo(UniqueCodeGenerator.generateWithdrawalsNo());
		withdrawalsApplyDao.create(withdrawalsApply);
		accountService.withdrawals(org, withdrawalsApply.getAmount(), withdrawalsApply.getId(), withdrawalsApply.getApplyNo());
		return withdrawalsApply;
	}

	public Page<WithdrawalsApply> pagesByQuery(final WithdrawalsApplyQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<WithdrawalsApply> pages = withdrawalsApplyDao.page(new Specification<WithdrawalsApply>() {
			@Override
			public Predicate toPredicate(Root<WithdrawalsApply> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (query.getApplyId() != null) {
					predicateList.add(criteriaBuilder.equal(root.get("id").as(Long.class), query.getApplyId()));
				}
				if (query.getOrgId() != null) {
					Join<WithdrawalsApply, Organization> join = root.join("org", JoinType.LEFT);
					predicateList.add(criteriaBuilder.equal(join.get("id").as(Long.class), query.getOrgId()));
				}
				if (query.getStates() != null && query.getStates().length > 0) {
					WithdrawalsApplyState[] stateArr = new WithdrawalsApplyState[query.getStates().length];
					for (int i = 0; i < query.getStates().length; i++) {
						stateArr[i] = WithdrawalsApplyState.getByIndex(query.getStates()[i]);
					}
					predicateList.add(root.get("state").in(stateArr));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("state").as(Integer.class)),
						criteriaBuilder.desc(root.get("applyTime").as(Date.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public WithdrawalsApply changeState(Long applyId, WithdrawalsApplyState state) {
		WithdrawalsApply withdrawalsApply = withdrawalsApplyDao.retrieve(applyId);
		WithdrawalsApplyState oldState = withdrawalsApply.getState();
		if (oldState != state) {
			withdrawalsApply.setState(state);
			withdrawalsApply.setUpdateTime(new Date());
			if (state == WithdrawalsApplyState.REFUSED || state == WithdrawalsApplyState.FAILURE) {
				accountService.withdrawalsFailure(withdrawalsApply.getOrg(), withdrawalsApply.getAmount(), withdrawalsApply.getId(), withdrawalsApply.getApplyNo());
			}
			withdrawalsApplyDao.update(withdrawalsApply);
		}
		return withdrawalsApply;
	}

}
