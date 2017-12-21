package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.dto.websocket.StockResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebSocketController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send")
    @SendTo("/topic/callback")
    public Map<String, StockResponseMessage> send(@RequestBody List<String> codes) {
        logger.info("接收客户端发送的消息");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Map<String, StockResponseMessage> responseMessage = new HashMap<>();
        for (String code : codes) {
            StockResponseMessage stockResponseMessage = new StockResponseMessage(code, "13.15", time);
            responseMessage.put(code, stockResponseMessage);
        }
        return responseMessage;
    }

    @Scheduled(fixedRate = 5000)
    @SendTo("/topic/callback")
    public void callback() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        StockResponseMessage responseMessage = new StockResponseMessage("000001", "13.15", time);
        simpMessagingTemplate.convertAndSend("/topic/callback", responseMessage);
        logger.info("响应请求消息,{}", time);
    }
}
