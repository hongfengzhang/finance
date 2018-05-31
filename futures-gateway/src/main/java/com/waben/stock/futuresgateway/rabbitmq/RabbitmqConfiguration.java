package com.waben.stock.futuresgateway.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
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

	public static final String tickPriceQueueName = "futures-gateway-tickPrice";

	public static final String tickSizeQueueName = "futures-gateway-tickSize";

	public static final String historicalDataQueueName = "futures-gateway-historicalData";

	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		logger.info("host,username:{}{}", connectionFactory.getHost(), connectionFactory.getUsername());
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

	@Bean(name = { "historicalDataListenerContainerFactory" })
	public SimpleRabbitListenerContainerFactory historicalDataListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrentConsumers(2);
		factory.setMaxConcurrentConsumers(5);
		return factory;
	}

	/**
	 * 创建 处理行情价格 队列
	 */
	@Bean
	public Queue tickSizeQueue() {
		return new Queue(tickSizeQueueName);
	}

	/**
	 * 创建 处理行情价格 队列
	 */
	@Bean
	public Queue tickPriceQueue() {
		return new Queue(tickPriceQueueName);
	}

	/**
	 * 创建 处理历史数据 队列
	 */
	@Bean
	public Queue historicalDataQueue() {
		return new Queue(historicalDataQueueName);
	}

}
