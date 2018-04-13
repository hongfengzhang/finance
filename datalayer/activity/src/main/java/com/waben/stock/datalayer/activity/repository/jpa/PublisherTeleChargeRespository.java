package com.waben.stock.datalayer.activity.repository.jpa;

import com.waben.stock.datalayer.activity.entity.PublisherTeleCharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherTeleChargeRespository extends JpaRepository<PublisherTeleCharge, Long>{
    PublisherTeleCharge findPublisherTeleChargeByApId(long apId);
}
