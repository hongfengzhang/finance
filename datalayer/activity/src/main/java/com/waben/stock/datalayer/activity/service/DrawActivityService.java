package com.waben.stock.datalayer.activity.service;


import com.waben.stock.datalayer.activity.entity.DrawActivity;
import com.waben.stock.datalayer.activity.entity.DrawActivityRadio;
import com.waben.stock.datalayer.activity.entity.TicketAmount;
import com.waben.stock.datalayer.activity.repository.DrawActivityDao;
import com.waben.stock.datalayer.activity.repository.DrawActivityRadioDao;
import com.waben.stock.datalayer.activity.repository.TicketDao;
import com.waben.stock.interfaces.exception.DataNotFoundException;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DrawActivityService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DrawActivityDao drawActivityDao;

    @Autowired
    private DrawActivityRadioDao drawActivityRadioDao;

    @Autowired
    private TicketDao ticketDao;

    @Transactional
    public TicketAmount lotteryDraw(long activity, long publisherId) {
        DrawActivity drawActivity = drawActivityDao.getDrawActivityByActivityIdAndPublisherId(activity, publisherId);
        if(drawActivity.getRemaintime()<=0) {
            //抽奖次数不足
            return null;
        }else {
            List<DrawActivityRadio> drawActivityRadios = drawActivityRadioDao.getDrawActivityRadioByActivitysId(drawActivity.getActivityId());
            TicketAmount ticket = draw(drawActivityRadios);
            logger.info("奖品 ：{}",JacksonUtil.encode(ticket));
            setRemaintime(drawActivity.getDrawId());
            setNumAndUsedNum(ticket.getTicketAmountId());
            return ticket;
        }
    }

    public TicketAmount draw(List<DrawActivityRadio> drawActivityRadios) {
        for(int i=0; i<drawActivityRadios.size(); i++) {
            boolean flag = true;
            DrawActivityRadio drawActivityRadioI = drawActivityRadios.get(i);
            TicketAmount ticketI = ticketDao.getTicketAmount(drawActivityRadioI.getTicketId());
            int surI = ticketI.getNum() - ticketI.getUsednum();
            if(surI>0) {
                BigDecimal surplusI = new BigDecimal(surI);//剩余数量
                BigDecimal radioI = drawActivityRadioI.getRadio();//占比
                BigDecimal resultI = surplusI.multiply(radioI);
                for(int j=0; j<drawActivityRadios.size()-i;j++) {
                    DrawActivityRadio drawActivityRadioJ = drawActivityRadios.get(j);
                    TicketAmount ticketJ = ticketDao.getTicketAmount(drawActivityRadioJ.getTicketId());
                    int surJ = ticketJ.getNum() - ticketJ.getUsednum();
                    BigDecimal surplusJ = new BigDecimal(surJ);//剩余数量
                    BigDecimal radioJ = drawActivityRadioJ.getRadio();//占比
                    BigDecimal resultJ = surplusJ.multiply(radioJ);
                    if(resultI.compareTo(resultJ)<0) {
                        flag = false;
                    }
                }
                if(flag) {
                    return ticketI;
                }
            }
        }
        throw new DataNotFoundException("已没有奖品");
    }

    @Transactional
    public void setRemaintime(long id) {
        DrawActivity drawActicity = drawActivityDao.getDrawActicity(id);
        drawActicity.setRemaintime(drawActicity.getRemaintime()-1);
    }

    @Transactional
    public void setNumAndUsedNum(long id) {
        TicketAmount ticketAmount = ticketDao.getTicketAmount(id);
        ticketAmount.setUsednum(ticketAmount.getUsednum()+1);
    }
}
