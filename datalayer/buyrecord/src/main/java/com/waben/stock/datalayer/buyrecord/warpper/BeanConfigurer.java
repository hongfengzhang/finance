package com.waben.stock.datalayer.buyrecord.warpper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
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
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
        logger.info("host:{},username:{}", connectionFactory.getHost(),connectionFactory.getUsername());
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }




    //创建上证 深证 创业板队列
    @Bean(name = "shangSecurity")
    public Queue shangSecurity() {
        return new Queue("shangSecurity");
    }

    @Bean(name = "shenSecurity")
    public Queue shenSecurity() {
        return new Queue("shenSecurity");
    }

    @Bean(name="developSecurity")
    public Queue developSecurity() {
        return new Queue("developSecurity");
    }

    //创建点买交易交换机
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("buyRecord");
    }


    @Bean
    public Binding bindingExchangeShangSecurity(@Qualifier("shangSecurity") Queue queue,TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("shang");
    }

    @Bean
    public Binding bindingExchangeShenSecurity(@Qualifier("shenSecurity") Queue queue,TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("shen");
    }

    @Bean
    public Binding bindingExchangeDevelopSecurity(@Qualifier("developSecurity") Queue queue,TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("shen");
    }

}
