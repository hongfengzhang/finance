package com.waben.stock.datalayer.activity.repository;

import com.waben.stock.datalayer.activity.entity.PublisherTicket;

import java.util.List;

public interface PublisherTicketDao {

	void savePublisherTicket(PublisherTicket a);

	List<PublisherTicket> getPublisherTicketList(int pageno, int pagesize);

	PublisherTicket getPublisherTicket(long PublisherTicketId);
}
