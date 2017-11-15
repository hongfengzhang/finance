/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.waben.stock.applayer.operation.warpper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waben.stock.applayer.operation.warpper.auth.filter.LoginProcessingFilter;
import com.waben.stock.applayer.operation.warpper.auth.handler.*;
import com.waben.stock.applayer.operation.warpper.auth.provider.DaoAuthenticationProvider;
import com.waben.stock.applayer.operation.warpper.auth.provider.InMemoryAuthenticationProvider;
import com.waben.stock.applayer.operation.warpper.auth.provider.InvestorAuthenticationProvider;
import com.waben.stock.applayer.operation.warpper.auth.provider.ManagerAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Joe Grandja
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENTRY_POINT = "/login";
//    @Autowired
//    private DaoAuthenticationProvider daoAuthenticationProvider;
//    @Autowired
//    private InMemoryAuthenticationProvider memoryAuthenticationProvider;
    @Autowired
    private InvestorAuthenticationProvider investorAuthenticationProvider;
    @Autowired
    private ManagerAuthenticationProvider managerAuthenticationProvider;
    @Autowired
    private LoginSuccessHandler successHandler;
    @Autowired
    private LoginFailureHandler failureHandler;
    @Autowired
    private ObjectMapper objectMapper;

    protected LoginProcessingFilter processingFilter() throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter(LOGIN_ENTRY_POINT, successHandler, failureHandler,
                objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    /**
     * 配置通过拦截器请求
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, LOGIN_ENTRY_POINT,"/login-error").permitAll()
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage(LOGIN_ENTRY_POINT)
//                    .successForwardUrl("/index")
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandler())
                .and().exceptionHandling()
                                        .authenticationEntryPoint(new ForbiddenEntryPoint())
                                        .accessDeniedHandler(new ForbiddenAccessDeniedHandler())
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .addFilterBefore(processingFilter(), UsernamePasswordAuthenticationFilter.class)
        ;

    }

    /**
     * 配置 Security Filter 链
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/img/**", "/static/**", "**/*.css", "**/*.js", "/favicon.ico");
    }


    /**
     * 验证过程 若多个authenticationProvider 返回null 则交给下一个provider验证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(investorAuthenticationProvider)
                .authenticationProvider(managerAuthenticationProvider);

    }

}
