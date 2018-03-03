package com.waben.stock.datalayer.stockoption.service;

import com.waben.stock.datalayer.stockoption.entity.InquiryResult;
import com.waben.stock.datalayer.stockoption.repository.InquiryResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquiryResultService {

    @Autowired
    private InquiryResultDao inquiryResultDao;

    public InquiryResult save(InquiryResult inquiryResult) {
        return inquiryResultDao.create(inquiryResult);
    }
}
