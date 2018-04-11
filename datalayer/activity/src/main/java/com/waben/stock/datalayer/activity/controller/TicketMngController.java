package com.waben.stock.datalayer.activity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.waben.stock.datalayer.activity.service.TicketMngService;
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
	
	@Autowired
	private TicketMngService ts;
	
	@Override
	public Response<TicketAmountDto> saveTicketAmount(TicketAmountDto td) {
		return new Response<>(ts.saveTicket(td));
	}

	@Override
	public Response<List<TicketAmountDto>> getTicketAmountList(int pageno, Integer pagesize) {
		return new Response<>(ts.getTicketList(pageno, pagesize));
	}

	@Override
	public Response<Void> deleteTicket(long ticketId) {
		ts.deleteTicket(ticketId);
		return new Response<>();
	}

}
