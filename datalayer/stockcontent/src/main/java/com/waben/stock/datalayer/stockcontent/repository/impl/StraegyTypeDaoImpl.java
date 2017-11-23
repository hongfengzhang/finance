package com.waben.stock.datalayer.stockcontent.repository.impl;

import com.waben.stock.datalayer.stockcontent.entity.StraegyType;
import com.waben.stock.datalayer.stockcontent.repository.StraegyTypeDao;
import com.waben.stock.datalayer.stockcontent.repository.impl.jpa.StraegyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
@Repository
public class StraegyTypeDaoImpl implements StraegyTypeDao {

    @Autowired
    private StraegyTypeRepository repository;

    @Override
    public List<StraegyType> retrieveWithEnable() {
        return repository.findAllByState(true);
    }

    @Override
    public StraegyType create(StraegyType straegyType) {
        return repository.save(straegyType);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public StraegyType update(StraegyType straegyType) {
        return null;
    }

    @Override
    public StraegyType retrieve(Long id) {
        return null;
    }

    @Override
    public Page<StraegyType> page(int page, int limit) {
        return null;
    }

    @Override
    public Page<StraegyType> page(Specification<StraegyType> specification, Pageable pageable) {
        return null;
    }

    @Override
    public List<StraegyType> list() {
        return repository.findAll();
    }
}
