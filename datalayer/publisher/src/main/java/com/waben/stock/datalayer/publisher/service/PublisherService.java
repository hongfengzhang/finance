package com.waben.stock.datalayer.publisher.service;

import com.waben.stock.datalayer.publisher.entity.Publisher;
import com.waben.stock.datalayer.publisher.repository.PublisherDao;
import com.waben.stock.interfaces.dto.PublisherDto;
import net.sf.cglib.beans.BeanCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
@Service
public class PublisherService {

    @Autowired
    private PublisherDao publisherDao;

    public PublisherDto findById(Long id) {
        Publisher publisher = publisherDao.retrieve(id);
        if (publisher == null) {
            throw new RuntimeException();
        }
        return publisher.copy();
    }
}
