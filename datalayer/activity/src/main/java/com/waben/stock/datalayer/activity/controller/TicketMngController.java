package com.waben.stock.datalayer.activity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.activity.TicketMngInterface;

/**
 * 
 * @author guowei 2018/4/11
 *
 */
public class TicketMngController implements TicketMngInterface{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Response<TicketAmountDto> saveTicketAmount(TicketAmountDto td) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<List<TicketAmountDto>> getTicketAmountList(int pageno, Integer pagesize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Void> deleteTicket(long ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

}
