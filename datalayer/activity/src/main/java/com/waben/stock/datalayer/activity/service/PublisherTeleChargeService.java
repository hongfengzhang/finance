package com.waben.stock.datalayer.activity.service;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.entity.PublisherTeleCharge;
import com.waben.stock.datalayer.activity.repository.PublisherTeleChargeDao;
import com.waben.stock.interfaces.dto.activity.PublisherTeleChargeDto;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherTeleChargeService {
    @Autowired
    private PublisherTeleChargeDao dao;

    public List<PublisherTeleChargeDto> getPublisherTeleChargeList(int pageno, Integer pagesize) {
        if(pagesize == null){
            PageAndSortQuery pq = new PageAndSortQuery();
            pagesize = pq.getSize();
        }
        List<PublisherTeleCharge> li = dao.getPublisherTeleChargeList(pageno, pagesize);
        List<PublisherTeleChargeDto> atolist = new ArrayList<>();
        if(li != null){
            for(PublisherTeleCharge a : li){
                PublisherTeleChargeDto ad  = CopyBeanUtils.copyBeanProperties(PublisherTeleChargeDto.class, a, false);
                atolist.add(ad);
            }
        }
        return atolist;
    }

    @Transactional
    public PublisherTeleChargeDto savePublisherTeleCharge(PublisherTeleChargeDto pdto){
        PublisherTeleCharge p = CopyBeanUtils.copyBeanProperties(PublisherTeleCharge.class, pdto, false);
        dao.savePublisherTeleCharge(p);
        return CopyBeanUtils.copyBeanProperties(PublisherTeleChargeDto.class, p, false);
    }

    public PublisherTeleCharge getPublisherTeleCharge(long publisherTeleChargeId) {
        PublisherTeleCharge publisherTeleCharge = dao.getPublisherTeleCharge(publisherTeleChargeId);
        return publisherTeleCharge;
    }

    @Transactional
    public void setPay(long publisherTeleChargeId) {
        PublisherTeleCharge publisherTeleCharge = dao.getPublisherTeleCharge(publisherTeleChargeId);
        if(!publisherTeleCharge.isIspay()) {
            publisherTeleCharge.setIspay(!publisherTeleCharge.isIspay());
        }
    }
}
