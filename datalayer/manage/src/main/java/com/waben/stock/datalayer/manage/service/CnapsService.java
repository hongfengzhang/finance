package com.waben.stock.datalayer.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.manage.entity.BankInfo;
import com.waben.stock.datalayer.manage.entity.CardBin;
import com.waben.stock.datalayer.manage.entity.Cnaps;
import com.waben.stock.datalayer.manage.repository.BankInfoDao;
import com.waben.stock.datalayer.manage.repository.CardBinDao;
import com.waben.stock.datalayer.manage.repository.CnapsDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;

/**
 * Cnaps Service
 * 
 * @author luomengan
 *
 */
@Service
public class CnapsService {

	@Autowired
	private CnapsDao cnapsDao;

	@Autowired
	private CardBinDao cardBinDao;

	@Autowired
	private BankInfoDao bankInfoDao;

	public List<Cnaps> findByCityCodeAndClsCode(String cityCode, String clsCode) {
		return cnapsDao.retrieveByCityCodeAndClsCode(cityCode, clsCode);
	}

	public BankInfo findBankInfo(String bankCard) {
		CardBin cardBin = cardBinDao.retrieveByBankCard(bankCard);
		if (cardBin == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		return bankInfoDao.retrieveByBankNameLike(cardBin.getBankName());
	}

	public List<BankInfo> listBankInfo() {
		return bankInfoDao.list();
	}

}
