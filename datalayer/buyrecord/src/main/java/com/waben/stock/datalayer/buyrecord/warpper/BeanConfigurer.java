package com.waben.stock.datalayer.buyrecord.warpper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/9/19.
 * @desc
 */
@Configuration
public class BeanConfigurer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        jackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return jackson2HttpMessageConverter;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        logger.info("host,username:{}{}", connectionFactory.getHost(), connectionFactory.getUsername());
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    /**
     * 创建 委托申请买入队列
     */
    @Bean(name = "entrustApplyBuyIn")
    public Queue entrustBuyInQueue() {
        return new Queue("entrustApplyBuyIn");
    }

    @Bean("buyRecord")
    public TopicExchange buyRecordExchange() {
        return new TopicExchange("buyRecord");
    }

    @Bean
    public Binding bindingExchangEntrustBuyIn(@Qualifier("entrustApplyBuyIn") Queue queue,
                                              @Qualifier("buyRecord") TopicExchange buyRecordExchange) {
        return BindingBuilder.bind(queue).to(buyRecordExchange).with("applyBuyIn");
    }
}
