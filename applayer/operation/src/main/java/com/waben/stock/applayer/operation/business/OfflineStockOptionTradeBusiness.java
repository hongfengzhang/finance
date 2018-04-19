package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.OfflineStockOptionTradeService;
import com.waben.stock.applayer.operation.warpper.mail.ExcelUtil;
import com.waben.stock.applayer.operation.warpper.mail.QuotoInquiry;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.MailUrlInfoDto;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.enums.OfflineStockOptionTradeState;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OfflineStockOptionTradeBusiness {
    @Autowired
    @Qualifier("offlinestockoptiontradeFeignService")
    private OfflineStockOptionTradeService offlineStockOptionTradeService;
    @Autowired
    private StockOptionTradeBusiness stockOptionTradeBusiness;
    public OfflineStockOptionTradeDto add(OfflineStockOptionTradeDto offlineStockOptionTradeDto) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.add(offlineStockOptionTradeDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public OfflineStockOptionTradeDto settlement(Long id, BigDecimal sellingPrice) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.settlement(id, sellingPrice);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public OfflineStockOptionTradeDto find(Long id) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.find(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Map<String,BigDecimal> findMonthsProfit(String year) {
        SimpleDateFormat sdf = new SimpleDateFormat( "MM" );
        Response<List<OfflineStockOptionTradeDto>> response = offlineStockOptionTradeService.fetchMonthsProfit(year);
        String code = response.getCode();
        if ("200".equals(code)) {
            List<OfflineStockOptionTradeDto> result = response.getResult();
            Map<String,BigDecimal> map = new TreeMap<>();
            for(int i=1;i<=12;i++) {
                if(i<10) {
                    map.put("0"+i,new BigDecimal("0"));
                }else {
                    map.put(i+"",new BigDecimal("0"));
                }
            }
            for(OfflineStockOptionTradeDto dto : result) {
                String month = sdf.format(dto.getSellingTime());
                map.put(month,map.get(month).add(dto.getProfit()));
            }
            return map;
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
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
        return true;
    }

    public OfflineStockOptionTradeDto exercise(Long id) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.exercise(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
