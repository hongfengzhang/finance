package com.waben.stock.applayer.tactics.service.fallback;

import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.dto.PublisherDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/4.
 * @desc
 */
@Component
public class PublisherServiceFallback implements PublisherService {
    @Override
    public PublisherDto findById(Long id) {
        return new PublisherDto();
    }
}
