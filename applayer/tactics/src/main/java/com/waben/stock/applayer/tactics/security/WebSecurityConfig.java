package com.waben.stock.applayer.tactics.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.waben.stock.applayer.tactics.security.jwt.JWTAuthenticationFilter;
import com.waben.stock.applayer.tactics.security.jwt.JWTLoginFilter;
import com.waben.stock.applayer.tactics.service.CapitalAccountService;
import com.waben.stock.applayer.tactics.service.PublisherService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private CapitalAccountService accountService;

	@Bean
	public JWTLoginFilter jwtLoginFilter() {
		try {
			JWTLoginFilter result = new JWTLoginFilter(authenticationManager());
			result.setPublisherService(publisherService);
			result.setAccountService(accountService);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("get AuthenticationManager exception!", e);
		}
	}

	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		// 静态页面
		http.authorizeRequests().antMatchers("/receiveSocket.html", "/js/**", "/bankIcon/**", "/bannerPic/**").permitAll();
		// websocket
		http.authorizeRequests().antMatchers("/socket/**").permitAll();
		// swagger页面
		http.authorizeRequests().antMatchers("/swagger-ui.html").permitAll();
		http.authorizeRequests().antMatchers("/webjars/**").permitAll();
		http.authorizeRequests().antMatchers("/swagger-resources/**").permitAll();
		http.authorizeRequests().antMatchers("/v2/api-docs").permitAll();
		http.authorizeRequests().antMatchers("/configuration/**").permitAll();
		// 开放接口
		http.authorizeRequests().antMatchers("/publisher/sendSms", "/publisher/register").permitAll();
		http.authorizeRequests().antMatchers("/system/getEnabledBannerList", "/system/getEnabledCircularsList",
				"/system/getAppHomeTopData").permitAll();
		http.authorizeRequests().antMatchers("/stock/getStockRecommendList", "/stock/selectStock").permitAll();
		// 其余接口
		http.authorizeRequests().antMatchers("/**").authenticated();

		// 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容
		http.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
		// 添加一个过滤器验证其他请求的Token是否合法
		http.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers("/css/**", "/image/**", "/js/**");
	}
}