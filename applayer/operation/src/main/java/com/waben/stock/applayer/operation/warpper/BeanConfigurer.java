package com.waben.stock.applayer.operation.warpper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.waben.stock.applayer.operation.exception.AuthMethodNotSupportedException;
import com.waben.stock.applayer.operation.service.security.InvestorUserDetailService;
import com.waben.stock.applayer.operation.service.security.ManagerUserDetailService;
import com.waben.stock.applayer.operation.warpper.auth.provider.InvestorAuthenticationProvider;
import com.waben.stock.applayer.operation.warpper.auth.provider.ManagerAuthenticationProvider;
import com.waben.stock.interfaces.exception.ExecptionHandler;
import com.waben.stock.interfaces.pojo.ExceptionInformation;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/9/19.
 * @desc
 */
@Configuration
public class BeanConfigurer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InvestorUserDetailService investorDetailService;

    @Autowired
    private ManagerUserDetailService managerUserDetailService;

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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(investorDetailService);
//        return  daoAuthenticationProvider;
//    }

    @Bean
    public InvestorAuthenticationProvider investorAuthenticationProvider() {
        InvestorAuthenticationProvider investorAuthenticationProvider = new InvestorAuthenticationProvider
                (passwordEncoder());
        investorAuthenticationProvider.setUserDetailsService(investorDetailService);
        return investorAuthenticationProvider;
    }

    //
    @Bean
    public ManagerAuthenticationProvider managerAuthenticationProvider() {
        ManagerAuthenticationProvider managerAuthenticationProvider = new ManagerAuthenticationProvider
                (passwordEncoder());
        managerAuthenticationProvider.setUserDetailsService(managerUserDetailService);
        return managerAuthenticationProvider;
    }

//    @Bean
//    public RoleVoter roleVoter() {
//        RoleVoter roleVoter = new RoleVoter();
//        roleVoter.setRolePrefix("");
//        return roleVoter;
//    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages_zh_CN");
        return messageSource;
    }

    @Bean
    public ExecptionHandler execptionHandler() {
        ExecptionHandler execptionHandler = new ExecptionHandler();
        execptionHandler.extendException(Arrays.asList(new ExceptionInformation(AuthMethodNotSupportedException.class,
                HttpServletResponse.SC_FORBIDDEN, "403")));
        return execptionHandler;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        logger.info("host:{},username:{}", connectionFactory.getHost(), connectionFactory.getUsername());
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

    @Bean(name = "developSecurity")
    public Queue developSecurity() {
        return new Queue("developSecurity");
    }

    /**
     * 创建 委托队列
     *
     * @return
     */
    @Bean(name = "entrustQueue")
    public Queue entrustQueue() {
        return new Queue("entrust");
    }


    //创建点买交易风控交换机
    @Bean("risk")
    public TopicExchange riskExchange() {
        return new TopicExchange("risk");
    }

    @Bean("buyRecord")
    public TopicExchange buyRecordExchange() {
        return new TopicExchange("buyRecord");
    }


    @Bean
    public Binding bindingExchangeShangSecurity(@Qualifier("shangSecurity") Queue queue,
                                                @Qualifier("risk")TopicExchange riskExchange) {
        return BindingBuilder.bind(queue).to(riskExchange).with("shang");
    }

    @Bean
    public Binding bindingExchangeShenSecurity(@Qualifier("shenSecurity") Queue queue,
                                               @Qualifier("risk") TopicExchange riskExchange) {
        return BindingBuilder.bind(queue).to(riskExchange).with("shen");
    }

    @Bean
    public Binding bindingExchangeDevelopSecurity(@Qualifier("developSecurity") Queue queue,
                                                  @Qualifier("risk") TopicExchange riskExchange) {
        return BindingBuilder.bind(queue).to(riskExchange).with("develop");
    }


    @Bean
    public Binding bindingExchangEntrust(@Qualifier("entrustQueue") Queue queue,
                                         @Qualifier("buyRecord") TopicExchange buyRecordExchange) {
        return BindingBuilder.bind(queue).to(buyRecordExchange).with("securities");
    }
}
