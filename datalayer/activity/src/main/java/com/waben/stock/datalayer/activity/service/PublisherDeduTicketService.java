package com.waben.stock.datalayer.activity.service;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.entity.ActivityPublisher;
import com.waben.stock.datalayer.activity.entity.PublisherDeduTicket;
import com.waben.stock.datalayer.activity.entity.PublisherTeleCharge;
import com.waben.stock.datalayer.activity.repository.ActivityDao;
import com.waben.stock.datalayer.activity.repository.ActivityPublisherDao;
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

    @Autowired
    private ActivityPublisherDao activityPublisherDao;

    @Autowired
    private ActivityDao activityDao;
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

        for(PublisherDeduTicketDto publisherDeduTicket : atolist) {
            ActivityPublisher activityPublisher = activityPublisherDao.getActivityPublisher(publisherDeduTicket.getApId());
            Activity activity = activityDao.getActivity(activityPublisher.getActivityId());
            publisherDeduTicket.setActivityName(activity.getSubject());
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

    public List<PublisherDeduTicket> getPublisherDeduTicketsByApId(long apId) {
       return dao.getPublisherDeduTicketsByApId(apId);
    }

    @Transactional
    public PublisherDeduTicket setStatus(long id) {
        PublisherDeduTicket publisherDeduTicket = dao.getPublisherDeduTicket(id);
        if(publisherDeduTicket.getStatus()==1) {
            publisherDeduTicket.setStatus(2);
        }
        return publisherDeduTicket;
    }
}
