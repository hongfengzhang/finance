package com.waben.stock.applayer.admin.business.futures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTermAdminDto;
import com.waben.stock.interfaces.dto.futures.FuturesExchangeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.futures.FutureContractTermInterface;
import com.waben.stock.interfaces.service.futures.FuturesTradeInterface;
/**
 * 期货合约
 * @author pzl
 *
 */
@Service
public class FuturesContractTermBusiness {

	@Autowired
	@Qualifier("futureContractTermInterface")
	private FutureContractTermInterface reference;

	
	public FuturesTermAdminDto save(FuturesTermAdminDto dto){
		Response<FuturesTermAdminDto> response = reference.addContractTerm(dto);
		String code = response.getCode();
		if("200".equals(code)){
			return response.getResult();
		}else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
	
	public FuturesTermAdminDto modify(FuturesTermAdminDto dto){
		Response<FuturesTermAdminDto> response = reference.modifyContractTerm(dto);
		String code = response.getCode();
		if("200".equals(code)){
			return response.getResult();
		}else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
	
	public void deleteContract(Long id){
		reference.deleteContractTerm(id);;
	}
}
