package com.waben.stock.datalayer.futures.service;

import java.util.ArrayList;
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

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.repository.FuturesContractTermDao;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTermAdminQuery;

/**
 * 期货合约期限 service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesContractTermService {

	@Autowired
	private FuturesContractTermDao termDao;

	public FuturesContractTerm addContractTerm(FuturesContractTerm dto) {
		return termDao.create(dto);
	}

	public FuturesContractTerm modifyContractTerm(FuturesContractTerm dto) {
		return termDao.update(dto);
	}

	public void deleteContractTerm(Long id) {
		termDao.delete(id);
	}
	
	public int deleteBycontractId(Long contractId){
		return termDao.deleteBycontractId(contractId);
	}

	public FuturesContractTerm getContractCurrentTerm(FuturesContract contract) {
		List<FuturesContractTerm> termList = termDao.retrieveByContractAndCurrent(contract, true);
		if (termList != null && termList.size() > 0) {
			return termList.get(0);
		}
		return null;
	}
	
	public Page<FuturesContractTerm> pagesTermAdmin(final FuturesTermAdminQuery query){
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<FuturesContractTerm> pages = termDao.page(new Specification<FuturesContractTerm>() {
			
			@Override
			public Predicate toPredicate(Root<FuturesContractTerm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Join<FuturesContractTerm, FuturesContract> join = root.join("contract",JoinType.LEFT);
				
				if(query.getSymbol() != null && !"".equals(query.getSymbol())){
					predicateList.add(criteriaBuilder.equal(join.get("symbol").as(String.class), query.getSymbol()));
				}
				if(query.getName() != null && !"".equals(query.getName())){
					predicateList.add(criteriaBuilder.equal(join.get("name").as(String.class), query.getName()));
				}
				if(query.getContractNo() != null && !"".equals(query.getContractNo())){
					predicateList.add(criteriaBuilder.equal(root.get("contractNo").as(String.class), query.getContractNo()));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}

				return criteriaQuery.getRestriction();
			}
		}, pageable);
		
		return pages;
	}

	public List<FuturesContractTerm> findByListContractId(Long contractId) {
		return termDao.findByListContractId(contractId);
	}

}
