package com.waben.stock.datalayer.activity.repository;

import java.util.List;

import com.waben.stock.datalayer.activity.entity.TicketAmount;

public interface TicketDao {
	
	public void saveTicket(TicketAmount t);
	
	public List<TicketAmount> getTicketList(int pageno,int pagesize);
	
	public void deleteTicket(long ticketId);
}
