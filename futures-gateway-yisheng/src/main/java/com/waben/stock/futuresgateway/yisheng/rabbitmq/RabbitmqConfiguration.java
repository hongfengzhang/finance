package com.waben.stock.futuresgateway.yisheng.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitmqConfiguration {

	Logger logger = LoggerFactory.getLogger(getClass());

	public static final String commodityQueueName = "futures-gateway-yisheng-commodity";
	
	public static final String contractQueueName = "futures-gateway-yisheng-contract";

	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		logger.info("host,username:{}{}", connectionFactory.getHost(), connectionFactory.getUsername());
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

	/**
	 * 创建 品种 队列
	 */
	@Bean
	public Queue commodityQueue() {
		return new Queue(commodityQueueName);
	}
	
	/**
	 * 创建 合约 队列
	 */
	@Bean
	public Queue contractQueue() {
		return new Queue(contractQueueName);
	}

}
