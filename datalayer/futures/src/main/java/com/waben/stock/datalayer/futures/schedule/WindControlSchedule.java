package com.waben.stock.datalayer.futures.schedule;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesWindControlType;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;

/**
 * 风控作业
 * 
 * @author luomengan
 *
 */
@Component
public class WindControlSchedule {

	/**
	 * 监控间隔
	 * <p>
	 * 如果是工作日，每间隔5秒中获取持仓中的股票，判断持仓中的股票
	 * </p>
	 */
	public static final long Execute_Interval = 5 * 1000;

	@Autowired
	private FuturesOrderService orderService;

	@PostConstruct
	public void initTask() {
		Timer timer = new Timer();
		timer.schedule(new WindControlTask(), Execute_Interval);
	}

	private class WindControlTask extends TimerTask {
		@Override
		public void run() {
			try {
				// step 1 : 获取所有持仓中的正式单
				FuturesOrderQuery query = new FuturesOrderQuery();
				query.setPage(0);
				query.setSize(Integer.MAX_VALUE);
				query.setState(FuturesOrderState.Position);
				query.setIsTest(false);
				Page<FuturesOrder> pages = orderService.pagesOrder(query);
				List<FuturesOrder> content = pages.getContent();
				// step 2 : 遍历所有订单，判断是否达到风控平仓条件
				if (content != null && content.size() > 0) {
					for (FuturesOrder order : content) {
						FuturesWindControlType windControlType = null;
						// step 3 : 获取合约行情
						FuturesContractMarket market = RetriveFuturesOverHttp.market(order.getContractSymbol());
						// 
					}
				}
			} finally {
				initTask();
			}
		}
	}

	private boolean isReachContractExpiration(FuturesOrder order) {
		return false;
	}

	private boolean isReachProfitPoint(FuturesOrder order, FuturesContractMarket market) {
		return false;
	}

	private boolean isReachLossPoint(FuturesOrder order, FuturesContractMarket market) {
		return false;
	}

}
