package com.waben.stock.datalayer.activity.repository;

import com.waben.stock.datalayer.activity.entity.PublisherDeduTicket;
import com.waben.stock.datalayer.activity.entity.PublisherTeleCharge;

import java.util.List;

public interface PublisherTeleChargeDao {

	void savePublisherTeleCharge(PublisherTeleCharge a);

	List<PublisherTeleCharge> getPublisherTeleChargeList(int pageno, int pagesize);

	PublisherTeleCharge getPublisherTeleCharge(long publisherTeleChargeId);

    List<PublisherTeleCharge> getPublisherTeleChargesByApId(long apId);
}
