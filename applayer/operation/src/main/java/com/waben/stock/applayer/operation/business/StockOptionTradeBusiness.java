package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.MailUrlInfoService;
import com.waben.stock.applayer.operation.service.stockoption.StockOptionOrgService;
import com.waben.stock.applayer.operation.service.stockoption.StockOptionTradeService;
import com.waben.stock.applayer.operation.warpper.mail.*;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.InquiryResultDto;
import com.waben.stock.interfaces.dto.stockoption.MailUrlInfoDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.enums.OfflineStockOptionTradeState;
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
    @Qualifier("stockoptionorgFeignService")
    private StockOptionOrgService stockOptionOrgService;
    @Autowired
    private InquiryResultBusiness inquiryResultBusiness;
    @Value("${mail.contextPath}")
    private String contextPath;
    @Autowired
    @Qualifier("mailUrlInfoFeignService")
    private MailUrlInfoService mailUrlInfoService;

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
        quotoInquiry.setUnderlying(result.getStockName());
        quotoInquiry.setCode(result.getStockCode());
        quotoInquiry.setStrike("100%");
        quotoInquiry.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        quotoInquiry.setPrice(null);/*String.valueOf(result.getRightMoneyRatio())*/
        quotoInquiry.setTenor(result.getCycleMonth());
        quotoInquiry.setDate(new Date());
        logger.info("数据组装成功:{}", JacksonUtil.encode(quotoInquiry));
        String file = ExcelUtil.commonRender(contextPath, quotoInquiry);
        //添加邮件url信息
        MailUrlInfoDto mailUrlInfoDto = new MailUrlInfoDto();
        mailUrlInfoDto.setCode(result.getStockCode());
        mailUrlInfoDto.setUnderlying(result.getStockName());
        mailUrlInfoDto.setTemplateName("inquiry");
        mailUrlInfoDto.setLocalUrl(file);
        try {
            mailUrlInfoService.add(mailUrlInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailService.send("询价单", Arrays.asList(file), org.getEmail());
        if(!OfflineStockOptionTradeState.INQUIRY.equals(result.getStatus())) {
            modify(id);
        }
        return true;
    }

    public Boolean purchase(Long id) {
        Response<StockOptionTradeDto> response = stockOptionTradeService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            StockOptionTradeDto result = response.getResult();
            Long tradeId = result.getId();
            InquiryResultDto inquiryResultDto = inquiryResultBusiness.fetchByTrade(tradeId);
            Response<List<StockOptionOrgDto>> lists = stockOptionOrgService.lists();
            StockOptionOrgDto org = lists.getResult().get(0);
            QuotoPurchase quotoPurchase = new QuotoPurchase();
            quotoPurchase.setUnderlying(result.getStockName());
            quotoPurchase.setCode(result.getStockCode());
            quotoPurchase.setStrike("100%");
            quotoPurchase.setAmount(String.valueOf(result.getNominalAmount().intValue()));
            Date date = new Date();
            quotoPurchase.setBegin(date);
            //到期时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH,result.getCycle()-1);
            quotoPurchase.setEnd(calendar.getTime());
            quotoPurchase.setRate(String.valueOf(inquiryResultDto.getRightMoneyRatio()));
            MailMessage mailMessage = new PurchaseMessage();
            //添加邮件url信息
            String file = ExcelUtil.commonRender(contextPath, quotoPurchase);
            MailUrlInfoDto mailUrlInfoDto = new MailUrlInfoDto();
            mailUrlInfoDto.setCode(result.getStockCode());
            mailUrlInfoDto.setUnderlying(result.getStockName());
            mailUrlInfoDto.setTemplateName("purchase");
            mailUrlInfoDto.setLocalUrl(file);
            try {
                mailUrlInfoService.add(mailUrlInfoDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mailService.send("申购单", mailMessage.message(quotoPurchase), org.getEmail());

            return true;
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Boolean exercise(Long id) {
        Response<StockOptionTradeDto> stockOptionTradeDtoResponse = stockOptionTradeService.fetchById(id);
        StockOptionTradeDto result = stockOptionTradeDtoResponse.getResult();
        StockOptionOrgDto org = result.getOfflineTradeDto().getOrg();
        QuotoExenise quotoExenise = new QuotoExenise();
        quotoExenise.setUnderlying(result.getStockName());
        quotoExenise.setCode(result.getStockCode());
        quotoExenise.setStrike("100%");
        quotoExenise.setAmount(String.valueOf(result.getNominalAmount().intValue()));
        quotoExenise.setDueTo(result.getOfflineTradeDto().getExpireTime());
        quotoExenise.setExenise(result.getRightTime());
        MailMessage mailMessage = new ExeriseMessage();
        String file = ExcelUtil.commonRender(contextPath, quotoExenise);
        //添加邮件url信息
        MailUrlInfoDto mailUrlInfoDto = new MailUrlInfoDto();
        mailUrlInfoDto.setCode(result.getStockCode());
        mailUrlInfoDto.setUnderlying(result.getStockName());
        mailUrlInfoDto.setTemplateName("exercise");
        mailUrlInfoDto.setLocalUrl(file);
        try {
            mailUrlInfoService.add(mailUrlInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailService.send("行权单", mailMessage.message(quotoExenise), org.getEmail());
        //修改订单状态
        if(result.getRightTime()!=null) {
            stockOptionTradeService.exercise(id);
        }
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

    public StockOptionTradeDto modify(Long id){
        Response<StockOptionTradeDto> response  =   stockOptionTradeService.modify(id);
        if ("200".equals(response.getCode())){
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
