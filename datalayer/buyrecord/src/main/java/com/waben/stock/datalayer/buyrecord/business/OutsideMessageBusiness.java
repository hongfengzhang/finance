package com.waben.stock.datalayer.buyrecord.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.buyrecord.reference.OutsideMessageReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.message.OutsideMessage;

@Service
public class OutsideMessageBusiness {

	@Autowired
	@Qualifier("outsideMessageReference")
	private OutsideMessageReference outsideMessageReference;

	public void send(OutsideMessage message) {
		Response<String> response = outsideMessageReference.send(message);
		String code = response.getCode();
		if ("200".equals(code)) {
			return;
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

}
