package com.waben.stock.datalayer.investors.reference.fallback;

import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
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
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<String> dropBuyRecord(Long id) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<BuyRecordDto> sellLock(Long lockUserId, Long id, String windControlTypeIndex) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<BuyRecordDto> sellOut(Long investorId, Long id, BigDecimal sellingPrice) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<BuyRecordDto>> pagesByQuery(BuyRecordQuery buyRecordQuery) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<BuyRecordDto> fetchBuyRecord(Long buyrecord) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<BuyRecordDto> buyLock(Long investorId, Long id, String delegateNumber) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<BuyRecordDto> buyInto(Long investorId, Long id, BigDecimal buyingPrice) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
