package com.waben.stock.datalayer.manage.repository.impl;

import com.netflix.discovery.converters.Auto;
import com.waben.stock.datalayer.manage.entity.Staff;
import com.waben.stock.datalayer.manage.repository.StaffDao;
import com.waben.stock.datalayer.manage.repository.impl.jpa.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@Repository
public class StaffDaoImpl implements StaffDao {

    @Autowired
    private StaffRepository repository;

    @Override
    public Staff findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public Staff create(Staff staff) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Staff update(Staff staff) {
        return null;
    }

    @Override
    public Staff retrieve(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Staff> page(int page, int limit) {
        return null;
    }

    @Override
    public Page<Staff> page(Specification<Staff> specification, Pageable pageable) {
        return repository.findAll(specification,new PageRequest(pageable.getPageNumber(),pageable.getPageSize()));
    }

    @Override
    public List<Staff> list() {
        return null;
    }
}
