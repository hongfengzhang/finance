package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.investor.SecurityAccountService;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SecurityAccountQuery;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Component
public class SecurityAccountServiceFallback implements SecurityAccountService {
    @Override
    public Response<PageInfo<SecurityAccountDto>> pagesByQuery(SecurityAccountQuery securityAccountQuery) {
        return new Response<>("205","暂无券商账户列表数据");
    }
}
