package com.waben.stock.datalayer.investors.service;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.business.StockBusiness;
import com.waben.stock.datalayer.investors.container.InvestorContainer;
import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.entity.SecurityAccount;
import com.waben.stock.datalayer.investors.repository.InvestorDao;
import com.waben.stock.datalayer.investors.repository.rest.StockJyRest;
import com.waben.stock.datalayer.investors.warpper.ApplicationContextBeanFactory;
import com.waben.stock.datalayer.investors.warpper.messagequeue.rabbitmq.EntrustApplyProducer;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.enums.EntrustType;
import com.waben.stock.interfaces.enums.WindControlType;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.SecuritiesStockException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.stock.SecuritiesInterface;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockHolder;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockLoginInfo;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockMoney;
import com.waben.stock.interfaces.util.CopyBeanUtils;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private StockBusiness stockBusiness;
    @Autowired
    private BuyRecordBusiness buyRecordBusiness;
//    private InvestorContainer investorContainer = ApplicationContextBeanFactory.getBean
//            (InvestorContainer.class);
    @Autowired
    private InvestorContainer investorContainer;
    @Autowired
    private EntrustApplyProducer entrustProducer;
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
        logger.info("券商资金账户:{},密码:{}", securityAccount.getAccount(), securityAccount.getPassword());
        //根据系统保存的券商账户用户信息登录实时券商账户
        StockLoginInfo stockLoginInfo = null;
        try {
            stockLoginInfo = stockJyRest.login(securityAccount.getAccount(), securityAccount.getPassword());
            logger.info("登录成功，获取交易session:{}", stockLoginInfo.getTradeSession());
            result.setSecuritiesSession(stockLoginInfo.getTradeSession());
        } catch (SecuritiesStockException e) {
            logger.error("证券商户登录失败{}", e.getMessage());
//            e.printStackTrace();
        }
        return result;
    }

    public Investor findById(Long investor) {
        Investor result = investorDao.retrieve(investor);
        if (result == null) {
            throw new ServiceException(ExceptionConstant.INVESTOR_NOT_FOUND_EXCEPTION);
        }
        return result;
    }


    private String stockType(String exponent) {
        String type;
        logger.info("股票账户类型:{}", exponent);
        if (exponent.equals("4353")) {
            //上证
            type = "1";
        } else if (exponent.equals("4609")) {
            //深证
            type = "2";
        }else if(exponent.equals("4621")){
            type = "2";
        } else {
            throw new ServiceException(ExceptionConstant.INVESTOR_EXCHANGE_TYPE_NOT_SUPPORT_EXCEPTION);
        }
        return type;
    }

    /***
     * @author yuyidi 2017-12-01 14:48:21
     * @method buyRecordEntrust
     * @param securitiesStockEntrust
     * @return java.lang.String
     * @description 点买交易记录执行券商股票委托
     */
    @Transactional
    public String entrustApplyBuyIn(SecuritiesStockEntrust securitiesStockEntrust, String tradeSession) {
        //查询资金账户可用资金
        StockJyRest stockJyRest = (StockJyRest) securitiesInterface;
        StockMoney stockMoney = stockJyRest.money(tradeSession);
        //点买交易股票数量* 单价
        Double realStockPrice = securitiesStockEntrust.getEntrustNumber() * securitiesStockEntrust.getEntrustPrice()
                .doubleValue();
        //校检资金信息
        if (stockMoney.getEnableBalance() - realStockPrice < 0) {
            throw new ServiceException(ExceptionConstant.INVESTOR_STOCKACCOUNT_MONEY_NOT_ENOUGH);
        }
        //查询当前资金账户的股东账户信息
        List<StockHolder> stockHolders = stockJyRest.retrieveStockHolder(tradeSession);
        String type = stockType(securitiesStockEntrust.getExponent());
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

        if(securitiesStockEntrust.getBuyRecordState().equals(BuyRecordState.BUYLOCK)) {
            throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
        }
        //开始委托下单
        String enturstNo = stockJyRest.buyRecordEntrust(securitiesStockEntrust, tradeSession, stockAccount, type,
                EntrustType.BUY);
        return enturstNo;
    }


    @Transactional
    public String buyRecordApplySellOut(SecuritiesStockEntrust securitiesStockEntrust, String
            tradeSession) {
        //查询资金账户可用资金
        StockJyRest stockJyRest = (StockJyRest) securitiesInterface;
        //查询当前资金账户的股东账户信息
        List<StockHolder> stockHolders = stockJyRest.retrieveStockHolder(tradeSession);
        String type = stockType(securitiesStockEntrust.getExponent());
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

        if(securitiesStockEntrust.getBuyRecordState().equals(BuyRecordState.SELLLOCK)) {
            throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
        }
        //开始委托下单卖出
        String entrustNo = stockJyRest.buyRecordEntrust(securitiesStockEntrust, tradeSession, stockAccount, type,
                EntrustType
                        .SELL);
        return entrustNo;
    }

    /**
     * 自动委托买出
     * @param securitiesStockEntrust
     * @return
     */
    @Transactional
    public BuyRecordDto voluntarilyApplySellOut(SecuritiesStockEntrust securitiesStockEntrust) {
        List<InvestorDto> investorsContainer = investorContainer.getInvestorContainer();
        InvestorDto investorDto = investorsContainer.get(0);
        securitiesStockEntrust = buyRecordEntrust(investorDto.getId(), securitiesStockEntrust);
        String entrustNo = buyRecordApplySellOut(securitiesStockEntrust, investorDto.getSecuritiesSession());
        BuyRecordDto result = buyRecordBusiness.entrustApplySellOut(CopyBeanUtils.copyBeanProperties(Investor.class, investorDto, false), securitiesStockEntrust, entrustNo, WindControlType.PUBLISHERAPPLY.getIndex());
        //如果委托成功,加入委托卖出锁定队列
        if (result.getState().equals(BuyRecordState.BUYLOCK)) {
            securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
            securitiesStockEntrust.setTradeNo(result.getTradeNo());
            securitiesStockEntrust.setEntrustNo(result.getDelegateNumber());
            securitiesStockEntrust.setEntrustState(EntrustState.HASBEENREPORTED);
            securitiesStockEntrust.setEntrustTime(result.getUpdateTime());
            entrustProducer.entrustApplySellOut(securitiesStockEntrust);
        }
        return result;
    }
    public String buyRecordApplyWithdraw(SecuritiesStockEntrust securitiesStockEntrust) {
        StockJyRest stockJyRest = (StockJyRest) securitiesInterface;
        String enturstNo = stockJyRest.withdraw(securitiesStockEntrust.getTradeSession(), securitiesStockEntrust.getEntrustNo());
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

//                if(StringUtils.isEmpty(query.getUserName()) && StringUtils.isEmpty(query.getBeginTime())){
//                    criteriaQuery.orderBy(criteriaBuilder.desc(root.<Date>get("createTime").as(Date.class)));
//                    return criteriaQuery.getRestriction();
//                }
                List<Predicate> predicatesList = new ArrayList();
                if (!StringUtils.isEmpty(query.getUserName())) {
                    Predicate userNameQuery = criteriaBuilder.equal(root.get("userName").as(String.class), query
                            .getUserName());
                    predicatesList.add(userNameQuery);
                }

                if(query.getBeginTime() != null && query.getEndTime() != null){
                    Predicate createTimeQuery = criteriaBuilder.between(root.<Date>get("createTime").as(Date.class),query.getBeginTime(),query.getEndTime());
                    predicatesList.add(criteriaBuilder.and(createTimeQuery));
                }
                if (query.getState() != null&&query.getState()!=2) {
                    Predicate stateQuery = criteriaBuilder.equal(root.get("state").as(Integer.class), query
                            .getState());
                    predicatesList.add(stateQuery);
                }
                criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.<Date>get("createTime").as(Date.class)));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }
    
    public List<Investor> findAll(){
    	return investorDao.list();
    }


    public Integer revision(Investor investor) {
        return investorDao.updateById(investor.getUserName(),investor.getState(),investor.getId());
    }

    public void delete(Long id) {
        investorDao.delete(id);
    }

    private SecuritiesStockEntrust buyRecordEntrust(Long investorId, SecuritiesStockEntrust securitiesStockEntrust) {
        securitiesStockEntrust.setInvestor(investorId);
        StockDto stockDto = stockBusiness.fetchWithExponentByCode(securitiesStockEntrust.getStockCode());
        securitiesStockEntrust.setStockName(stockDto.getName());
        securitiesStockEntrust.setStockCode(stockDto.getCode());
        securitiesStockEntrust.setExponent(stockDto.getExponent().getExponentCode());
        return securitiesStockEntrust;
    }

    /**
     * 自动买入
     * @param securitiesStockEntrust
     * @return
     */
    public BuyRecordDto buyIn(SecuritiesStockEntrust securitiesStockEntrust) {
        //获取投资人对象
        List<InvestorDto> investorsContainer = investorContainer.getInvestorContainer();
        InvestorDto investorDto = investorsContainer.get(0);
        securitiesStockEntrust= buyRecordEntrust(investorDto.getId(), securitiesStockEntrust);
        //TODO 若没有接收到响应请求， 则回滚服务业务
        String entrustNo = entrustApplyBuyIn(securitiesStockEntrust, investorDto.getSecuritiesSession());
        Investor investor = CopyBeanUtils.copyBeanProperties(investorDto, new Investor(), false);
        BuyRecordDto result = buyRecordBusiness.buyRecordApplyBuyIn(investor, securitiesStockEntrust, entrustNo);
        //如果委托成功,加入委托买入锁定队列
        if (result.getState().equals(BuyRecordState.BUYLOCK)) {
            securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
            securitiesStockEntrust.setTradeNo(result.getTradeNo());
            securitiesStockEntrust.setEntrustNo(result.getDelegateNumber());
            securitiesStockEntrust.setEntrustState(EntrustState.HASBEENREPORTED);
            securitiesStockEntrust.setEntrustTime(result.getUpdateTime());
            entrustProducer.entrustApplyBuyIn(securitiesStockEntrust);
        }
        return result;
    }
}
