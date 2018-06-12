package com.waben.stock.futuresgateway.yisheng.esapi.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 行情-分钟K组合作业
 * 
 * @author luomengan
 *
 */
@Component
@EnableScheduling
public class QuoteMinuteKGroupSchedule {

	/**
	 * 每分钟计算上一分钟的分钟K
	 */
	@Scheduled(cron = "0 20 5/1 * * ?")
	public void run() {
		
	}

}
