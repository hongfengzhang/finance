package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.StockOptionTradeService;
import com.waben.stock.applayer.operation.util.ExcelUtil;
import com.waben.stock.applayer.operation.warpper.mail.*;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class StockOptionTradeBusiness {
    @Autowired
    @Qualifier("stockoptiontradeFeignService")
    private StockOptionTradeService stockOptionTradeService;
    @Autowired
    private MailService mailService;

    @Value("${mail.contextPath}")
    private String contextPath;

    public PageInfo<StockOptionTradeDto> pages(StockOptionTradeQuery query) {
        Response<PageInfo<StockOptionTradeDto>> response = stockOptionTradeService.pagesByQuery(query);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Boolean inquiry(Long id) {
        Response<StockOptionTradeDto> stockOptionTradeDtoResponse = stockOptionTradeService.fetchById(id);
        StockOptionTradeDto result = stockOptionTradeDtoResponse.getResult();
        QuotoInquiry quotoInquiry = new QuotoInquiry();
        StockOptionOrgDto org = result.getOfflineTrade().getOrg();
        quotoInquiry.setUnderlying(org.getName());
        quotoInquiry.setCode(result.getStockCode());
        quotoInquiry.setStrike("100%");
        quotoInquiry.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        quotoInquiry.setPrice(String.valueOf(result.getRightMoneyRatio().intValue()));
        quotoInquiry.setTenor(result.getCycle());
        quotoInquiry.setDate(result.getBuyingTime());
        String file = ExcelUtil.renderInquiry(contextPath, quotoInquiry);
        mailService.send("询价单", Arrays.asList(file), org.getEmail());
        return true;
    }

    public Boolean purchase(Long id) {
        Response<StockOptionTradeDto> stockOptionTradeDtoResponse = stockOptionTradeService.fetchById(id);
        StockOptionTradeDto result = stockOptionTradeDtoResponse.getResult();
        StockOptionOrgDto org = result.getOfflineTrade().getOrg();
        QuotoPurchase quotoPurchase = new QuotoPurchase();
        quotoPurchase.setUnderlying(org.getName());
        quotoPurchase.setCode(result.getStockCode());
        quotoPurchase.setStrike("100%");
        quotoPurchase.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        quotoPurchase.setBegin(result.getBuyingTime());
        quotoPurchase.setEnd(result.getExpireTime());
        quotoPurchase.setRate(String.valueOf(result.getRightMoneyRatio().intValue()));
        MailMessage mailMessage = new PurchaseMessage();
        mailService.send("申购单", mailMessage.message(quotoPurchase), org.getEmail());
        return true;
    }

    public Boolean exercise(Long id) {
        Response<StockOptionTradeDto> stockOptionTradeDtoResponse = stockOptionTradeService.fetchById(id);
        StockOptionTradeDto result = stockOptionTradeDtoResponse.getResult();
        StockOptionOrgDto org = result.getOfflineTrade().getOrg();
        QuotoExenise quotoExenise = new QuotoExenise();
        quotoExenise.setUnderlying(org.getName());
        quotoExenise.setCode(result.getStockCode());
        quotoExenise.setStrike("100%");
        quotoExenise.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        quotoExenise.setDueTo(result.getOfflineTrade().getExpireTime());
        quotoExenise.setExenise(result.getRightTime());
        MailMessage mailMessage = new ExeriseMessage();
        mailService.send("行权单", mailMessage.message(quotoExenise), org.getEmail());
        return true;
    }
}
