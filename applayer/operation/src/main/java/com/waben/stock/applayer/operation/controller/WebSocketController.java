package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.dto.websocket.StockRequestMessage;
import com.waben.stock.applayer.operation.dto.websocket.StockResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class WebSocketController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send")
    @SendTo("/topic/callback")
    public StockResponseMessage send(StockRequestMessage stockWebSocketMessage) {
        logger.info("接收客户端发送的消息");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time =  df.format(new Date());
        return new StockResponseMessage(stockWebSocketMessage.getCode(), "13.15", time);
    }

//    @Scheduled(fixedRate = 5000)
//    @SendTo("/topic/callback")
//    public StockResponseMessage callback() {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time =  df.format(new Date());
//        simpMessagingTemplate.convertAndSend("/topic/callback",time);
//        logger.info("响应请求消息,{}", time);
//        return new StockResponseMessage("000001", "13.15", time);
//    }
}
