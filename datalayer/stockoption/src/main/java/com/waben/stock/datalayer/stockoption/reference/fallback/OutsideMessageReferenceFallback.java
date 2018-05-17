package com.waben.stock.datalayer.stockoption.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.stockoption.reference.OutsideMessageReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.message.OutsideMessage;

@Component
public class OutsideMessageReferenceFallback implements OutsideMessageReference {

	@Override
	public Response<String> send(OutsideMessage message) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
