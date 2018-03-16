package com.waben.stock.datalayer.stockoption.repository.impl.jpa;

import com.waben.stock.datalayer.stockoption.entity.InquiryResult;
import com.waben.stock.datalayer.stockoption.entity.StockOptionTrade;

public interface InquiryResultRepository extends CustomJpaRepository<InquiryResult,Long>{
    InquiryResult findByTrade(StockOptionTrade trade);
}
