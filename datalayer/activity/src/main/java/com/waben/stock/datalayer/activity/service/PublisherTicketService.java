package com.waben.stock.datalayer.activity.service;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.entity.ActivityPublisher;
import com.waben.stock.datalayer.activity.entity.PublisherDeduTicket;
import com.waben.stock.datalayer.activity.entity.PublisherTicket;
import com.waben.stock.datalayer.activity.repository.ActivityDao;
import com.waben.stock.datalayer.activity.repository.ActivityPublisherDao;
import com.waben.stock.datalayer.activity.repository.PublisherTicketDao;
import com.waben.stock.interfaces.dto.activity.PublisherTeleChargeDto;
import com.waben.stock.interfaces.dto.activity.PublisherTicketDto;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherTicketService {
    @Autowired
    private PublisherTicketDao dao;
    @Autowired
    private ActivityPublisherDao activityPublisherDao;

    @Autowired
    private ActivityDao activityDao;
    public List<PublisherTicketDto> getPublisherTicketList(int pageno, Integer pagesize) {
        if(pagesize == null){
            PageAndSortQuery pq = new PageAndSortQuery();
            pagesize = pq.getSize();
        }
        List<PublisherTicket> li = dao.getPublisherTicketList(pageno, pagesize);
        List<PublisherTicketDto> atolist = new ArrayList<>();
        if(li != null){
            for(PublisherTicket a : li){
                PublisherTicketDto ad  = CopyBeanUtils.copyBeanProperties(PublisherTicketDto.class, a, false);
                atolist.add(ad);
            }
        }
        for(PublisherTicketDto publisherTicketDto : atolist) {
            ActivityPublisher activityPublisher = activityPublisherDao.getActivityPublisher(publisherTicketDto.getApId());
            Activity activity = activityDao.getActivity(activityPublisher.getActivityId());
            publisherTicketDto.setActivityName(activity.getSubject());
        }
        return atolist;
    }

    @Transactional
    public PublisherTicketDto savePublisherTicket(PublisherTicketDto pdto){
        PublisherTicket p = CopyBeanUtils.copyBeanProperties(PublisherTicket.class, pdto, false);
        dao.savePublisherTicket(p);
        return CopyBeanUtils.copyBeanProperties(PublisherTicketDto.class, p, false);
    }

    public PublisherTicket getPublisherTicket(long publisherTicketId) {
        PublisherTicket publisherTicket = dao.getPublisherTicket(publisherTicketId);
        return publisherTicket;
    }

    public List<PublisherTicket> getPublisherTicketsByApId(long apId) {
        return dao.getPublisherTicketsByApId(apId);
    }

    @Transactional
    public PublisherTicket setStatus(long id) {
        PublisherTicket publisherTicket = dao.getPublisherTicket(id);
        if(publisherTicket.getStatus()==1) {
            publisherTicket.setStatus(2);
        }
        return publisherTicket;
    }
}
