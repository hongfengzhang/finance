package com.waben.stock.datalayer.activity.repository.impl;

import com.waben.stock.datalayer.activity.entity.PublisherDeduTicket;
import com.waben.stock.datalayer.activity.repository.PublisherDeduTicketDao;
import com.waben.stock.datalayer.activity.repository.jpa.PublisherDeduTicketRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublisherDeduTicketDaoImpl implements PublisherDeduTicketDao{
	
	@Autowired
	private PublisherDeduTicketRespository respository;


	@Override
	public void savePublisherDeduTicket(PublisherDeduTicket a) {
		respository.save(a);
	}

	@Override
	public List<PublisherDeduTicket> getPublisherDeduTicketList(int pageno, int pagesize) {
		Pageable p = new PageRequest(pageno-1, pagesize);
		Page<PublisherDeduTicket> pt =  respository.findAll(p);
		return pt.getContent();
	}

	@Override
	public PublisherDeduTicket getPublisherDeduTicket(long publisherDeduTicketId) {
		return respository.findOne(publisherDeduTicketId);
	}

	@Override
	public PublisherDeduTicket getPublisherDeduTicketByActivityPublisherId(long activityPublisherId) {
		return respository.findPublisherDeduTicketByApId(activityPublisherId);
	}
}
