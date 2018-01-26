package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stock.StockService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StockServiceFallbackFactory implements FallbackFactory<StockService> {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public StockService create(final Throwable throwable) {
        return new StockService() {
            @Override
            public Response<StockDto> fetchById(Long id) {
                return null;
            }

            @Override
            public Response<PageInfo<StockDto>> pagesByQuery(StockQuery stockQuery) {
                try {
                    logger.info("stockService 异常抛出：{}",throwable);
                    throw throwable;
                } catch (Throwable throwable1) {
                    logger.info("捕获异常");
                    throwable1.printStackTrace();
                }
                throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
            }

            @Override
            public Response<StockDto> fetchWithExponentByCode(String code) {
                return null;
            }

            @Override
            public Response<Integer> modify(StockDto stockDto) {
                return null;
            }

            @Override
            public void delete(Long id) {

            }
        };
    }
}
