package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.stockjy.SecuritiesStockEntrust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class InvestorBusiness {

    @Autowired
    private InvestorService investorService;
    @Autowired
    private StockBusiness stockBusiness;

    public PageInfo<InvestorDto> investors(InvestorQuery investorQuery) {
        Response<PageInfo<InvestorDto>> response = investorService.pagesByQuery(investorQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    /***
    * @author yuyidi 2017-12-02 20:38:31
    * @method buyRecordBuyIn
     * @param investorDto
     * @param buyRecordDto
    * @return com.waben.stock.interfaces.pojo.stock.stockjy.SecuritiesStockEntrust
    * @description 根据投资人及点买信息初始化券商股票委托信息并委托买入
    */
    public SecuritiesStockEntrust buyRecordBuyIn(InvestorDto investorDto, BuyRecordDto buyRecordDto) {
        SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
        securitiesStockEntrust.setBuyRecordId(buyRecordDto.getId());
        securitiesStockEntrust.setSerialCode(buyRecordDto.getSerialCode());
        StockDto stockDto = stockBusiness.fetchWithExponentByCode(buyRecordDto.getStockCode());
        securitiesStockEntrust.setStockName(stockDto.getName());
        securitiesStockEntrust.setStockCode(stockDto.getCode());
        securitiesStockEntrust.setExponent(stockDto.getStockExponentDto().getExponentCode());
        securitiesStockEntrust.setBuyingNumber(buyRecordDto.getNumberOfStrand());
        securitiesStockEntrust.setBuyingPrice(buyRecordDto.getBuyingPrice());
        securitiesStockEntrust.setBuyRecordState(buyRecordDto.getState());
        Response<BuyRecordDto> response = investorService.stockBuyIn(investorDto.getId(), securitiesStockEntrust,
                investorDto
                .getSecuritiesSession());
        if ("200".equals(response.getCode())) {
            securitiesStockEntrust.setEntrustNumber(response.getResult().getDelegateNumber());
            return securitiesStockEntrust;
        }
        throw new ServiceException(response.getCode());
    }


}
