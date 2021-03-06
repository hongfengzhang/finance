package com.waben.stock.datalayer.manage.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.manage.entity.BankInfo;
import com.waben.stock.datalayer.manage.repository.BankInfoDao;
import com.waben.stock.datalayer.manage.repository.impl.jpa.BankInfoRepository;

/**
 * 银行信息 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class BankInfoDaoImpl implements BankInfoDao {

	@Autowired
	private BankInfoRepository repository;

	@Override
	public BankInfo create(BankInfo t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public BankInfo update(BankInfo t) {
		return repository.save(t);
	}

	@Override
	public BankInfo retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<BankInfo> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public Page<BankInfo> page(Specification<BankInfo> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	@Override
	public List<BankInfo> list() {
		return repository.findAll();
	}

	@Override
	public BankInfo retrieveByBankNameLike(String bankName) {
		return repository.findByBankNameLike("%" + bankName + "%");
	}

	@Override
	public List<BankInfo> retrieveByAppSupport(boolean appSupport) {
		return repository.findByAppSupportAndEnable(appSupport, true);
	}

	@Override
	public List<BankInfo> retrieveByPcSupport(boolean pcSupport) {
		return repository.findByPcSupportAndEnable(pcSupport, true);
	}

}
