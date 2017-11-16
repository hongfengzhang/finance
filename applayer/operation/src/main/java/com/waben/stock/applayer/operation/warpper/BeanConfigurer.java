package com.waben.stock.applayer.operation.warpper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.waben.stock.applayer.operation.service.security.InvestorUserDetailService;
import com.waben.stock.applayer.operation.service.security.ManagerUserDetailService;
import com.waben.stock.applayer.operation.warpper.auth.provider.InvestorAuthenticationProvider;
import com.waben.stock.applayer.operation.warpper.auth.provider.ManagerAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private InvestorUserDetailService investorDetailService;

    @Autowired
    private ManagerUserDetailService managerUserDetailService;

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
        InvestorAuthenticationProvider investorAuthenticationProvider = new InvestorAuthenticationProvider(passwordEncoder());
        investorAuthenticationProvider.setUserDetailsService(investorDetailService);
        return investorAuthenticationProvider;
    }
//
    @Bean
    public ManagerAuthenticationProvider managerAuthenticationProvider() {
        ManagerAuthenticationProvider managerAuthenticationProvider = new ManagerAuthenticationProvider(passwordEncoder());
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


}
