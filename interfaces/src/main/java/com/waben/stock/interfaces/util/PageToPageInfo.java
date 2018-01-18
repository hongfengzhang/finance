package com.waben.stock.interfaces.util;

import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/20.
 * @desc
 */
public class PageToPageInfo {

    public static <T> PageInfo<T> pageToPageInfo(Page<?> page, Class<T> targetType) {
        PageInfo<T> result = new PageInfo<>(page, targetType);
        return result;
    }
}
