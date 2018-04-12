package com.waben.stock.datalayer.activity.repository.jpa;

import com.waben.stock.datalayer.activity.entity.PublisherTeleCharge;
import com.waben.stock.datalayer.activity.entity.PublisherTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherTicketRespository extends JpaRepository<PublisherTicket, Long>{

}
