package com.waben.stock.datalayer.publisher.repository.impl;

import com.waben.stock.datalayer.publisher.entity.Publisher;
import com.waben.stock.datalayer.publisher.repository.PublisherDao;
import com.waben.stock.datalayer.publisher.repository.impl.jpa.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
@Repository
public class PublisherDaoImpl implements PublisherDao {

    @Autowired
    private PublisherRepository repository;

    public Publisher create(Publisher publisher) {
        return null;
    }

    public void delete(Long id) {

    }

    public Publisher update(Publisher publisher) {
        return null;
    }

    public Publisher retrieve(Long id) {
        return repository.findById(id);
    }

    public Page<Publisher> page(int page, int limit) {
        return null;
    }

    public List<Publisher> list() {
        return null;
    }

	@Override
	public Publisher findByPhone(String phone) {
		return repository.findByPhone(phone);
	}

	@Override
	public Publisher findBySerialCode(String serialCode) {
		return repository.findBySerialCode(serialCode);
	}
}
