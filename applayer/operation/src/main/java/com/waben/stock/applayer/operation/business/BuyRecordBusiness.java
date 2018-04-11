package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.buyrecord.BuyRecordService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@Service
public class BuyRecordBusiness {

    @Autowired
    @Qualifier("buyRecordFeignService")
    private BuyRecordService buyRecordService;


    public BuyRecordDto fetchBuyRecord(Long buyRecord) {
        Response<BuyRecordDto> response = buyRecordService.fetchBuyRecord(buyRecord);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<BuyRecordDto> pages(BuyRecordQuery buyRecordQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByQuery(buyRecordQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        buyRecordService.delete(id);
    }

    public Map<String,Object> fetchBuyRecordProfitAndPosition() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String,Object> map = new HashMap<>();
        List<BuyRecordDto> result = buyRecordService.buyRecordsWithStatus(6).getResult();
        BigDecimal todayProfit = new BigDecimal(0);
        BigDecimal allProfit = new BigDecimal(0);
        int todayCount = 0;
        int allCount = 0;
        for(BuyRecordDto buyRecordDto : result) {
            if(sdf.format(new Date()).equals(sdf.format(buyRecordDto.getUpdateTime()))) {
                todayCount++;
                todayProfit = todayProfit.add(buyRecordDto.getSettlement().getInvestorProfitOrLoss());
            }
            allCount++;
            allProfit = allProfit.add(buyRecordDto.getSettlement().getInvestorProfitOrLoss());
        }
        map.put("todayProfit",todayProfit);
        map.put("allProfit",allProfit);
        map.put("todayCount",todayCount);
        map.put("allCount",allCount);
        return map;
    }
}
