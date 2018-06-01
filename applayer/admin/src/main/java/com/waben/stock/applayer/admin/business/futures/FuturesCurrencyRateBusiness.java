package com.waben.stock.applayer.admin.business.futures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.futures.PutForwardDto;
import com.waben.stock.interfaces.dto.futures.FuturesCurrencyRateDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.futures.FuturesCurrencyRateInterface;
import com.waben.stock.interfaces.service.manage.PutForwardInterface;

@Service
public class FuturesCurrencyRateBusiness {

	@Autowired
	@Qualifier("futuresCurrencyRateInterface")
	private FuturesCurrencyRateInterface reference;
	
	@Autowired
	@Qualifier("putForwardInterface")
	private PutForwardInterface putReference;
	
	public Response<PageInfo<PutForwardDto>> pagesPutForward(){
		return putReference.pages();
	}
	
	public PutForwardDto saveAndModify(PutForwardDto dto){
		return putReference.saveAndModify(dto);
	}
	
	
	public FuturesCurrencyRateDto save(FuturesCurrencyRateDto query){
		Response<FuturesCurrencyRateDto> response = reference.addCurrencyRate(query);
		String code = response.getCode();
		if("200".equals(code)){
			return response.getResult();
		}else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
	
	
	public FuturesCurrencyRateDto modify(FuturesCurrencyRateDto dto){
		Response<FuturesCurrencyRateDto> response = reference.modifyCurrencyRate(dto);
		String code = response.getCode();
		if("200".equals(code)){
			return response.getResult();
		}else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
	
	public void deleteCurrencyRate(Long id){
		reference.deleteCurrencyRate(id);;
	}
	
	public PageInfo<FuturesCurrencyRateDto> list(){
		Response<PageInfo<FuturesCurrencyRateDto>> response = reference.list();
		if("200".equals(response.getCode())){
			return response.getResult();
		}else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(response.getCode())){
			throw new NetflixCircuitException(response.getCode());
		}
		throw new ServiceException(response.getCode());
	}
}
