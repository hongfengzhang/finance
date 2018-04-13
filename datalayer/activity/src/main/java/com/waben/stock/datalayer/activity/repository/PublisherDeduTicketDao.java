package com.waben.stock.datalayer.activity.repository;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.entity.PublisherDeduTicket;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PublisherDeduTicketDao {

	void savePublisherDeduTicket(PublisherDeduTicket a);

	List<PublisherDeduTicket> getPublisherDeduTicketList(int pageno, int pagesize);

	PublisherDeduTicket getPublisherDeduTicket(long publisherDeduTicketId);

    List<PublisherDeduTicket> getPublisherDeduTicketsByApId(long activityPublisherId);
}
