package com.waben.stock.datalayer.stockcontent.repository;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/9/20.
 * @desc
 */
public interface BaseDao<T, S extends Serializable> {
    T create(T t);

    void delete(S id);

    T update(T t);

    T retrieve(S id);

    Page<T> page(int page, int limit);

    List<T> list();
}
