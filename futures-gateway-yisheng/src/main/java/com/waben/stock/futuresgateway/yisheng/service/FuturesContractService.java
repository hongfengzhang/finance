package com.waben.stock.futuresgateway.yisheng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.futuresgateway.yisheng.dao.FuturesContractDao;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesContract;

/**
 * 期货合约 Service
 * 
 * @author luomengan
 *
 */
@Service
public class FuturesContractService {

	@Autowired
	private FuturesContractDao futuresContractDao;

	public FuturesContract getFuturesContractInfo(Long id) {
		return futuresContractDao.retrieveFuturesContractById(id);
	}

	@Transactional
	public FuturesContract addFuturesContract(FuturesContract futuresContract) {
		return futuresContractDao.createFuturesContract(futuresContract);
	}

	@Transactional
	public FuturesContract modifyFuturesContract(FuturesContract futuresContract) {
		return futuresContractDao.updateFuturesContract(futuresContract);
	}

	@Transactional
	public void deleteFuturesContract(Long id) {
		futuresContractDao.deleteFuturesContractById(id);
	}
	
	@Transactional
	public void deleteFuturesContracts(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					futuresContractDao.deleteFuturesContractById(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<FuturesContract> futuresContracts(int page, int limit) {
		return futuresContractDao.pageFuturesContract(page, limit);
	}
	
	public List<FuturesContract> list() {
		return futuresContractDao.listFuturesContract();
	}

	public FuturesContract getByCommodityNoAndContractNo(String commodityNo, String contractNo) {
		return futuresContractDao.retrieveByCommodityNoAndContractNo(commodityNo, contractNo);
	}

	public List<FuturesContract> getByEnable(boolean enable) {
		return futuresContractDao.retriveByEnable(enable);
	}

}
