package com.waben.stock.datalayer.activity.service;

import com.waben.stock.datalayer.activity.entity.ActivityPublisher;
import com.waben.stock.datalayer.activity.repository.ActivityPublisherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityPublisherService {

    @Autowired
    private ActivityPublisherDao dao;

    public List<ActivityPublisher> getActivityPublisherByActivityId(long activityId) {
        return dao.getActivityPublisherByAId(activityId);
    }
}
