package com.waben.stock.applayer.tactics.security;

import com.waben.stock.applayer.tactics.wrapper.filter.HiddenParamProcessFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.waben.stock.applayer.tactics.reference.CapitalAccountReference;
import com.waben.stock.applayer.tactics.reference.PublisherReference;
import com.waben.stock.applayer.tactics.security.jwt.JWTAuthenticationFilter;
import com.waben.stock.applayer.tactics.security.jwt.JWTLoginFilter;
import com.waben.stock.applayer.tactics.service.RedisCache;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PublisherReference publisherReference;

	@Autowired
	private CapitalAccountReference accountService;

	@Autowired
	private RedisCache redisCache;

	public JWTAuthenticationFilter jWTAuthenticationFilter() {
		JWTAuthenticationFilter result = new JWTAuthenticationFilter();
		result.setJedisCache(redisCache);
		return result;
	}

	public JWTLoginFilter jwtLoginFilter() {
		try {
			JWTLoginFilter result = new JWTLoginFilter(authenticationManager());
			result.setPublisherService(publisherReference);
			result.setAccountService(accountService);
			result.setJedisCache(redisCache);
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
		http.authorizeRequests().antMatchers("/receiveSocket.html", "/js/**", "/bankIcon/**", "/bannerPic/**")
				.permitAll();
		// websocket
		http.authorizeRequests().antMatchers("/socket/**").permitAll();
		// 页面
		http.authorizeRequests().antMatchers("/swagger-ui.html").permitAll();
		http.authorizeRequests().antMatchers("/webjars/**").permitAll();
		http.authorizeRequests().antMatchers("/swagger-resources/**").permitAll();
		http.authorizeRequests().antMatchers("/v2/api-docs").permitAll();
		http.authorizeRequests().antMatchers("/configuration/**").permitAll();
		http.authorizeRequests().antMatchers("/guide/**").permitAll();
		http.authorizeRequests().antMatchers("/buy/**").permitAll();
		http.authorizeRequests().antMatchers("/returnUrl.html").permitAll();
		// 开放接口
		http.authorizeRequests().antMatchers("/publisher/sendSms", "/publisher/register", "/publisher/modifyPassword")
				.permitAll();
		http.authorizeRequests()
				.antMatchers("/system/getEnabledBannerList", "/system/banner/lists", "/system/getEnabledCircularsList",
						"/system/stockMarketExponent", "/system/getAppHomeTopData", "/system/serverTime")
				.permitAll();
		http.authorizeRequests().antMatchers("/strategytype/lists").permitAll();
		http.authorizeRequests().antMatchers("/buyRecord/tradeDynamic", "/buyRecord/isTradeTime").permitAll();
		http.authorizeRequests().antMatchers("/stock/stockRecommend", "/stock/selectStock", "/stock/kLine",
				"/stock/timeLine/{code}", "/stock/market/{code}", "/stock/disc/{code}", "/stock/{exponent}/ranking")
				.permitAll();
		http.authorizeRequests()
				.antMatchers("/payment/tbfpaycallback", "/payment/tbfpayreturn", "/payment/czpaycallback",
						"/payment/czpayreturn", "/payment/czwithholdcallback", "/payment/recharge",
						"/payment/quickpaynotify")
				.permitAll();
		http.authorizeRequests().antMatchers("/alipay/callback").permitAll();
		http.authorizeRequests().antMatchers("/cnaps/lists/{cityCode}", "/cnaps/bankinfo/applists").permitAll();
		http.authorizeRequests()
				.antMatchers("/jsonp/experienceSta", "/jsonp/{publisherId}/strategyqualify/{strategyTypeId}")
				.permitAll();
		http.authorizeRequests().antMatchers("/crawler/**").permitAll();
		http.authorizeRequests().antMatchers("/aliturnpay-page/**", "/paymentorder/**").permitAll();
		http.authorizeRequests().antMatchers("/appversion/currentAppVersion", "/appversionupgrade/checkUpgrade")
				.permitAll();
		http.authorizeRequests().antMatchers("/turbine/**").permitAll();
		http.authorizeRequests().antMatchers("/stockoptiontrade/cyclelists", "/stockoptiontrade/tradeDynamic").permitAll();
		//回调放权
		http.authorizeRequests().antMatchers("/quickpay/sdpaycallback").permitAll();
		http.authorizeRequests().antMatchers("/quickpay/sdpayreturn").permitAll();
		http.authorizeRequests().antMatchers("/quickpay/qqcallback").permitAll();
		http.authorizeRequests().antMatchers("/quickpay/jdcallback").permitAll();
		http.authorizeRequests().antMatchers("/quickpay/qqpayreturn").permitAll();
		http.authorizeRequests().antMatchers("/quickpay/jdpayreturn").permitAll();

		// 其余接口
		http.authorizeRequests().antMatchers("/**").authenticated();

		// 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容
		http.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class);

		// 添加一个过滤器验证其他请求的Token是否合法
		http.addFilterBefore(new HiddenParamProcessFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class);

		http.logout().logoutSuccessHandler(new CustomLogoutSuccessHandler());
		http.sessionManagement().maximumSessions(1);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers("/css/**", "/image/**", "/js/**","/static/js/**");
	}
}