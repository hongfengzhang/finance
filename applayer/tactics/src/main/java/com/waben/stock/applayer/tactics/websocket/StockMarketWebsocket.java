package com.waben.stock.applayer.tactics.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.dto.system.StockMarketExponentDto;

@Component
public class StockMarketWebsocket {

	public SimpMessagingTemplate template;

	@Autowired
	public StockMarketWebsocket(SimpMessagingTemplate template) {
		this.template = template;
	}

	/**
	 * 推送股票大盘指数
	 */
	public void sendStockMarketIndex(List<StockMarketExponentDto> stockMarketIndexList) {
		template.convertAndSend(WebsocketDestination.StockMarketIndex.getDestination(), stockMarketIndexList);
	}

}
