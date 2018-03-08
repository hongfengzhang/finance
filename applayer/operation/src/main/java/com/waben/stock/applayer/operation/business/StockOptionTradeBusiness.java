package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.StockOptionOrgService;
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
import com.waben.stock.interfaces.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StockOptionTradeBusiness {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    @Qualifier("stockoptiontradeFeignService")
    private StockOptionTradeService stockOptionTradeService;
    @Autowired
    private MailService mailService;
    @Autowired
    private StockOptionOrgService stockOptionOrgService;
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
        Response<List<StockOptionOrgDto>> lists = stockOptionOrgService.lists();
        StockOptionOrgDto org = lists.getResult().get(0);
        quotoInquiry.setUnderlying(org.getName());
        quotoInquiry.setCode(result.getStockCode());
        quotoInquiry.setStrike("100%");
        quotoInquiry.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        quotoInquiry.setPrice(String.valueOf(result.getRightMoneyRatio()));
        quotoInquiry.setTenor(result.getCycle());
        quotoInquiry.setDate(new Date());
        logger.info("数据组装成功:{}", JacksonUtil.encode(quotoInquiry));
        String file = ExcelUtil.renderInquiry(contextPath, quotoInquiry);
        mailService.send("询价单", Arrays.asList(file), org.getEmail());
        return true;
    }

    public Boolean purchase(Long id) {
        Response<StockOptionTradeDto> stockOptionTradeDtoResponse = stockOptionTradeService.fetchById(id);
        StockOptionTradeDto result = stockOptionTradeDtoResponse.getResult();
        Response<List<StockOptionOrgDto>> lists = stockOptionOrgService.lists();
        StockOptionOrgDto org = lists.getResult().get(0);
        QuotoPurchase quotoPurchase = new QuotoPurchase();
        quotoPurchase.setUnderlying(org.getName());
        quotoPurchase.setCode(result.getStockCode());
        quotoPurchase.setStrike("100%");
        quotoPurchase.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        Date date = new Date();
        quotoPurchase.setBegin(date);
        //到期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,result.getCycle());
        quotoPurchase.setEnd(calendar.getTime());
        quotoPurchase.setRate(String.valueOf(result.getRightMoneyRatio()));
        MailMessage mailMessage = new PurchaseMessage();
        mailService.send("申购单", mailMessage.message(quotoPurchase), org.getEmail());
        return true;
    }

    public Boolean exercise(Long id) {
        Response<StockOptionTradeDto> stockOptionTradeDtoResponse = stockOptionTradeService.fetchById(id);
        StockOptionTradeDto result = stockOptionTradeDtoResponse.getResult();
        StockOptionOrgDto org = result.getOfflineTradeDto().getOrg();
        QuotoExenise quotoExenise = new QuotoExenise();
        quotoExenise.setUnderlying(org.getName());
        quotoExenise.setCode(result.getStockCode());
        quotoExenise.setStrike("100%");
        quotoExenise.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        quotoExenise.setDueTo(result.getOfflineTradeDto().getExpireTime());
        quotoExenise.setExenise(result.getRightTime());
        MailMessage mailMessage = new ExeriseMessage();
        mailService.send("行权单", mailMessage.message(quotoExenise), org.getEmail());
        //修改订单状态
        stockOptionTradeService.exercise(id);
        return true;
    }

    public StockOptionTradeDto success(Long id) {
        logger.info("操作id:{}",id);
        Response<StockOptionTradeDto> response = stockOptionTradeService.success(id);
        logger.info("修改结果:{}",JacksonUtil.encode(response));
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StockOptionTradeDto fail(Long id) {
        Response<StockOptionTradeDto> response = stockOptionTradeService.fail(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StockOptionTradeDto findById(Long id) {
        Response<StockOptionTradeDto> response = stockOptionTradeService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StockOptionTradeDto settlement(Long id) {
        Response<StockOptionTradeDto> response = stockOptionTradeService.settlement(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
