package com.waben.stock.applayer.tactics.business;

import com.waben.stock.applayer.tactics.reference.ActivityReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityBusiness {

    @Autowired
    @Qualifier("activityFeignService")
    private ActivityReference activityReference;

}
