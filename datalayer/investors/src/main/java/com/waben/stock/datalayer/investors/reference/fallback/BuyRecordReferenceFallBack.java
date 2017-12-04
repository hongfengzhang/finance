package com.waben.stock.datalayer.investors.reference.fallback;

import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@Component
public class BuyRecordReferenceFallBack implements BuyRecordReference {

    @Override
    public Response<BuyRecordDto> addBuyRecord(BuyRecordDto buyRecordDto) {
        return null;
    }

    @Override
    public Response<String> dropBuyRecord(Long id) {
        return null;
    }

    @Override
    public Response<BuyRecordDto> buyLock(Long investorId, Long id) {
        return null;
    }

    @Override
    public Response<BuyRecordDto> buyInto(Long investorId, Long id, String delegateNumber, BigDecimal buyingPrice,
                                          Integer numberOfStrand) {
        return null;
    }

    @Override
    public Response<BuyRecordDto> sellLock(Long lockUserId, Long id, String windControlTypeIndex) {
        return null;
    }

    @Override
    public Response<BuyRecordDto> sellOut(Long investorId, Long id, BigDecimal sellingPrice, BigDecimal profitDistributionRatio) {
        return null;
    }

    @Override
    public Response<PageInfo<BuyRecordDto>> pagesByQuery(BuyRecordQuery buyRecordQuery) {
        return null;
    }

    @Override
    public Response<BuyRecordDto> fetchBuyRecord(Long buyrecord) {
        return null;
    }
}
