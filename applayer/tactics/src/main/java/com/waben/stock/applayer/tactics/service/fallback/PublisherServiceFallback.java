package com.waben.stock.applayer.tactics.service.fallback;

import com.waben.stock.applayer.tactics.service.PublisherService;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/4.
 * @desc
 */
@Component
public class PublisherServiceFallback implements PublisherService {

    public String echo() {
        return "server error";
    }
}
