package com.waben.stock.datalayer.activity.repository;

import java.util.List;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.entity.TicketAmount;

public interface TicketDao {
	
	void saveTicket(TicketAmount t);
	
	List<TicketAmount> getTicketList(int pageno,int pagesize);
	
	void deleteTicket(long ticketId);

	TicketAmount getTicketAmount(long ticketAmountId);

}
