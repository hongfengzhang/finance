package com.waben.stock.datalayer.investors.service;

import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.entity.SecurityAccount;
import com.waben.stock.datalayer.investors.repository.InvestorDao;
import com.waben.stock.datalayer.investors.repository.rest.SecuritiesInterface;
import com.waben.stock.datalayer.investors.repository.rest.StockJyRest;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.EntrustType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockHolder;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockLoginInfo;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class InvestorService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private InvestorDao investorDao;
    @Autowired
    private SecuritiesInterface securitiesInterface;


    /***
     * @author yuyidi 2017-11-30 19:37:27
     * @method findByUserName
     * @param userName
     * @return com.waben.stock.datalayer.investors.entity.Investor
     * @description 登录接口，根据投资人用户名获取用户信息
     */
    public Investor findByUserName(String userName) {
        Investor result = investorDao.retieveWithUserName(userName);
        if (result == null) {
            throw new ServiceException(ExceptionConstant.INVESTOR_NOT_FOUND_EXCEPTION);
        }
        //获取券商接口
        StockJyRest stockJyRest = (StockJyRest) securitiesInterface;
        SecurityAccount securityAccount = result.getSecurityAccount();
        //根据系统保存的券商账户用户信息登录实时券商账户
        StockLoginInfo stockLoginInfo = stockJyRest.login(securityAccount.getAccount(), securityAccount.getPassword());
        logger.info("登录成功，获取交易session:{}", stockLoginInfo.getTradeSession());
        result.setSecuritiesSession(stockLoginInfo.getTradeSession());
        return result;
    }

    public Investor findById(Long investor) {
        Investor result = investorDao.retrieve(investor);
        if (result == null) {
            throw new ServiceException(ExceptionConstant.INVESTOR_NOT_FOUND_EXCEPTION);
        }
        return result;
    }

    /***
     * @author yuyidi 2017-12-01 14:48:21
     * @method buyRecordEntrust
     * @param buyRecordDto
     * @return java.lang.String
     * @description 点买交易记录执行券商股票委托
     */
    @Transactional
    public String buyRecordEntrust(Investor investor, BuyRecordDto buyRecordDto, String tradeSession) {
        //查询资金账户可用资金
        StockJyRest stockJyRest = (StockJyRest) securitiesInterface;
         StockMoney stockMoney = stockJyRest.money(tradeSession);
        //点买交易股票数量* 单价
        Double realStockPrice = buyRecordDto.getNumberOfStrand() * buyRecordDto.getBuyingPrice().doubleValue();
        //校检资金信息
        if (stockMoney.getEnableBalance() - realStockPrice < 0) {
            throw new ServiceException(ExceptionConstant.INVESTOR_STOCKACCOUNT_MONEY_NOT_ENOUGH);
        }
        //查询当前资金账户的股东账户信息
        List<StockHolder> stockHolders = stockJyRest.retrieveStockHolder(tradeSession);
        String type = buyRecordDto.getStockDto().getStockExponentDto().getExponentCode();
        logger.info("股票账户类型:{}",type);
        if (type.equals("4353")) {
            //上证
            type = "1";
        } else if (type.equals("4609")) {
            //深证
            type = "2";
        } else {
            throw new ServiceException(ExceptionConstant.INVESTOR_EXCHANGE_TYPE_NOT_SUPPORT_EXCEPTION);
        }
        String stockAccount = null;
        for (StockHolder stockHolder : stockHolders) {
            if (stockHolder.getExchangeType().equals(type)) {
                stockAccount = stockHolder.getStockAccount();
                break;
            }
        }
        if (stockAccount == null) {
            throw new ServiceException(ExceptionConstant.INVESTOR_STOCKACCOUNT_NOT_EXIST);
        }
        //开始委托下单
        String enturstNo = stockJyRest.buyRecordEntrust(buyRecordDto, tradeSession, stockAccount, type, EntrustType
                .BUY);
        System.out.println("委托单号：" + enturstNo);
        return enturstNo;
    }


    @Transactional
    public Investor save(Investor investor) {
        return investorDao.create(investor);
    }

    public Page<Investor> pagesByQuery(final InvestorQuery query) {
        Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<Investor> pages = investorDao.page(new Specification<Investor>() {
            @Override
            public Predicate toPredicate(Root<Investor> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                if (!StringUtils.isEmpty(query.getUserName())) {
                    Predicate userNameQuery = criteriaBuilder.equal(root.get("userName").as(String.class), query
                            .getUserName());
                    criteriaQuery.where(criteriaBuilder.and(userNameQuery));
                }
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }

}
