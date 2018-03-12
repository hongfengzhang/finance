package com.waben.stock.applayer.promotion.service.fallback;

import com.waben.stock.applayer.promotion.service.organization.PromotionBuyRecordService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.PromotionBuyRecordDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.PromotionBuyRecordQuery;
import org.springframework.stereotype.Component;

/**
 * 推广渠道产生的策略 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class PromotionBuyRecordServiceFallback implements PromotionBuyRecordService {

    @Override
    public Response<PageInfo<PromotionBuyRecordDto>> adminPage(PromotionBuyRecordQuery query) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

}
