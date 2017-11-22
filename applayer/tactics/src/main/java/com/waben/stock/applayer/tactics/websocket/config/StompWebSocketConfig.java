package com.waben.stock.applayer.tactics.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * WebSocket的相关配置信息
 * 
 * @author luomengan
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 服务器向客户端发送消息的地址前缀
		config.enableSimpleBroker("/topic", "/user");
		config.setUserDestinationPrefix("/user/");
		// 客户端向服务器发送消息的地址前缀
		config.setApplicationDestinationPrefixes("/server");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接；withSockJS()的作用是开启SockJS支持
		registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
	}
	
}