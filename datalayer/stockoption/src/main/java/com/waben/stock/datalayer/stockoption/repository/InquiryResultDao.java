package com.waben.stock.datalayer.stockoption.repository;

import com.waben.stock.datalayer.stockoption.entity.InquiryResult;

public interface InquiryResultDao extends BaseDao<InquiryResult, Long>  {
    InquiryResult retrieveByTrade(Long trade);
}
