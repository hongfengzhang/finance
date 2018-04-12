package com.waben.stock.datalayer.activity.service;

import com.waben.stock.datalayer.activity.entity.PublisherDeduTicket;
import com.waben.stock.datalayer.activity.repository.PublisherDeduTicketDao;
import com.waben.stock.interfaces.dto.activity.PublisherDeduTicketDto;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherDeduTicketService {
    @Autowired
    private PublisherDeduTicketDao dao;

    public List<PublisherDeduTicketDto> getPublisherDeduTicketList(int pageno, Integer pagesize) {
        if(pagesize == null){
            PageAndSortQuery pq = new PageAndSortQuery();
            pagesize = pq.getSize();
        }
        List<PublisherDeduTicket> li = dao.getPublisherDeduTicketList(pageno, pagesize);
        List<PublisherDeduTicketDto> atolist = new ArrayList<>();
        if(li != null){
            for(PublisherDeduTicket a : li){
                PublisherDeduTicketDto ad  = CopyBeanUtils.copyBeanProperties(PublisherDeduTicketDto.class, a, false);
                atolist.add(ad);
            }
        }
        return atolist;
    }

    @Transactional
    public PublisherDeduTicketDto savePublisherDeduTicket(PublisherDeduTicketDto pdto){
        PublisherDeduTicket p = CopyBeanUtils.copyBeanProperties(PublisherDeduTicket.class, pdto, false);
        dao.savePublisherDeduTicket(p);
        return CopyBeanUtils.copyBeanProperties(PublisherDeduTicketDto.class, p, false);
    }

    public PublisherDeduTicket getPublisherDeduTicket(long publisherDeduTicketId) {
        PublisherDeduTicket publisherDeduTicket = dao.getPublisherDeduTicket(publisherDeduTicketId);
        return publisherDeduTicket;
    }
}
