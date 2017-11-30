package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Component
public class InvestorServiceFallback implements InvestorService {

    @Override
    public Response<PageInfo<InvestorDto>> pagesByQuery(InvestorQuery investorQuery) {
        return new Response<>("205","暂无投资人列表数据");
    }
}
