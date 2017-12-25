package com.waben.stock.applayer.tactics.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.service.CnapsService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.BankInfoDto;
import com.waben.stock.interfaces.dto.manage.CnapsDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * Cnaps Business
 * 
 * @author luomengan
 *
 */
@Service
public class CnapsBusiness {

	@Autowired
	private CnapsService cnapsService;

	public List<CnapsDto> findByCityCodeAndClsCode(String cityCode, String clsCode) {
		Response<List<CnapsDto>> response = cnapsService.fetchByCityCodeAndClsCode(cityCode, clsCode);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public BankInfoDto findBankInfo(String bankCard) {
		Response<BankInfoDto> response = cnapsService.fetchBankInfo(bankCard);
		if ("200".equals(response.getCode())) {
			if (response.getResult() == null) {
				throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
			}
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<BankInfoDto> listBankInfo() {
		Response<List<BankInfoDto>> response = cnapsService.listBankInfo();
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<BankInfoDto> appBankinfo() {
		Response<List<BankInfoDto>> response = cnapsService.listAppBankInfo();
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
