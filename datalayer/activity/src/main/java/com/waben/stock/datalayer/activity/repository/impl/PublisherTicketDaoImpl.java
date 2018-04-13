package com.waben.stock.datalayer.activity.repository.impl;

import com.waben.stock.datalayer.activity.entity.PublisherTicket;
import com.waben.stock.datalayer.activity.repository.PublisherTicketDao;
import com.waben.stock.datalayer.activity.repository.jpa.PublisherTicketRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublisherTicketDaoImpl implements PublisherTicketDao {
	
	@Autowired
	private PublisherTicketRespository respository;


	@Override
	public void savePublisherTicket(PublisherTicket a) {
		respository.save(a);
	}

	@Override
	public List<PublisherTicket> getPublisherTicketList(int pageno, int pagesize) {
		Pageable p = new PageRequest(pageno-1, pagesize);
		Page<PublisherTicket> pt =  respository.findAll(p);
		return pt.getContent();
	}

	@Override
	public PublisherTicket getPublisherTicket(long PublisherTicketId) {
		return respository.findOne(PublisherTicketId);
	}

	@Override
	public List<PublisherTicket> getPublisherTicketsByApId(long apId) {
		return respository.findPublisherTicketsByApId(apId);
	}
}
