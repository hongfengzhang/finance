package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.applayer.operation.warpper.messagequeue.rabbitmq.EntrustApplyProducer;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class InvestorBusiness {

    @Autowired
    @Qualifier("investerFeignService")
    private InvestorService investorService;
    @Autowired
    private StockBusiness stockBusiness;
    @Autowired
    private EntrustApplyProducer entrustProducer;

    public PageInfo<InvestorDto> investors(InvestorQuery investorQuery) {
        Response<PageInfo<InvestorDto>> response = investorService.pagesByQuery(investorQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    /***
    * @author yuyidi 2017-12-02 20:38:31
    * @method buyRecordEntrust
     * @param investorDto
     * @param buyRecordDto
    * @return com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust
    * @description 根据投资人及点买信息初始化券商股票委托信息并委托买入或卖出
    */
    private SecuritiesStockEntrust buyRecordEntrust(InvestorDto investorDto, BuyRecordDto buyRecordDto) {
        SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
        securitiesStockEntrust.setBuyRecordId(buyRecordDto.getId());
        securitiesStockEntrust.setSerialCode(buyRecordDto.getSerialCode());
        securitiesStockEntrust.setInvestor(investorDto.getId());
        StockDto stockDto = stockBusiness.fetchWithExponentByCode(buyRecordDto.getStockCode());
        securitiesStockEntrust.setStockName(stockDto.getName());
        securitiesStockEntrust.setStockCode(stockDto.getCode());
        securitiesStockEntrust.setExponent(stockDto.getStockExponentDto().getExponentCode());
        securitiesStockEntrust.setEntrustNumber(buyRecordDto.getNumberOfStrand());
        securitiesStockEntrust.setEntrustPrice(buyRecordDto.getDelegatePrice());
        securitiesStockEntrust.setBuyRecordState(buyRecordDto.getState());
        return securitiesStockEntrust;
    }

    public SecuritiesStockEntrust buyIn(InvestorDto investorDto,BuyRecordDto buyRecordDto) {
        SecuritiesStockEntrust securitiesStockEntrust= buyRecordEntrust(investorDto, buyRecordDto);
        Response<BuyRecordDto> response = investorService.stockApplyBuyIn(investorDto.getId(), securitiesStockEntrust,
                investorDto.getSecuritiesSession());
        if ("200".equals(response.getCode())) {
            securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
            securitiesStockEntrust.setTradeNo(response.getResult().getTradeNo());
            securitiesStockEntrust.setEntrustNo(response.getResult().getDelegateNumber());
            securitiesStockEntrust.setEntrustState(EntrustState.HASBEENREPORTED);
            entrustProducer.entrustApplyBuyIn(securitiesStockEntrust);
            return securitiesStockEntrust;
        }
        throw new ServiceException(response.getCode());
    }

    public SecuritiesStockEntrust sellOut(InvestorDto investorDto,BuyRecordDto buyRecordDto) {
        SecuritiesStockEntrust securitiesStockEntrust= buyRecordEntrust(investorDto, buyRecordDto);
        //申请卖出，更新数据库
        Response<BuyRecordDto> response = investorService.stockApplySellOut(investorDto.getId(), securitiesStockEntrust,
                investorDto.getSecuritiesSession());
        if ("200".equals(response.getCode())) {
            securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
            securitiesStockEntrust.setTradeNo(response.getResult().getTradeNo());
            securitiesStockEntrust.setEntrustNo(response.getResult().getDelegateNumber());
            securitiesStockEntrust.setEntrustState(EntrustState.HASBEENSUCCESS);
            entrustProducer.entrustApplySellOut(securitiesStockEntrust);
            return securitiesStockEntrust;
        }
        throw new ServiceException(response.getCode());
    }


}
