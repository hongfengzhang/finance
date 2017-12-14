package com.waben.stock.datalayer.publisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.publisher.entity.WithdrawalsOrder;
import com.waben.stock.datalayer.publisher.service.WithdrawalsOrderService;
import com.waben.stock.interfaces.dto.publisher.WithdrawalsOrderDto;
import com.waben.stock.interfaces.enums.WithdrawalsState;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.WithdrawalsOrderInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 提现订单 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/withdrawalsorder")
public class WithdrawalsOrderController implements WithdrawalsOrderInterface {

	@Autowired
	private WithdrawalsOrderService service;

	@Override
	public Response<WithdrawalsOrderDto> addWithdrawalsOrder(@RequestBody WithdrawalsOrderDto withdrawalsOrderDto) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(WithdrawalsOrderDto.class,
				service.save(CopyBeanUtils.copyBeanProperties(WithdrawalsOrder.class, withdrawalsOrderDto, false)),
				false));
	}

	@Override
	public Response<WithdrawalsOrderDto> changeState(@PathVariable String withdrawalsNo, String stateIndex) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(WithdrawalsOrderDto.class,
				service.changeState(withdrawalsNo, WithdrawalsState.getByIndex(stateIndex)), false));
	}

	@Override
	public Response<WithdrawalsOrderDto> fetchByWithdrawalsNo(@PathVariable String withdrawalsNo) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(WithdrawalsOrderDto.class,
				service.findByWithdrawalsNo(withdrawalsNo), false));
	}

}
