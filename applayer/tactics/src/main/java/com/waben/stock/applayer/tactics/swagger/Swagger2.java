package com.waben.stock.applayer.tactics.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket futuresApi() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6IlJvbGVfQXBwLCIsInVzZXJJZCI6Nywic2VyaWFsQ29kZSI6ImQwYTNkNWY2LWQyMzYtNDcwNy05MGVjLWMxYjRjNTE1M2UxZSIsInN1YiI6IjEzMTQ1OTQyMjAwIiwiZXhwIjo0MzIwMDAxNTI3NzU5NDQwfQ.ypyNSqStX_Sl5t4h9NLijYGD81oWzwAUH8gHFUCsXRzLCUeTWNVLBpEDadPfJBuyEVFImcG9kMke8IqFJH79Kw";
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("Authorization").description("token令牌").modelRef(new ModelRef("string")).parameterType("header")
				.defaultValue(token).required(false).build();
		pars.add(tokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2).groupName("futuresApi")
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
				.globalOperationParameters(pars)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.waben.stock.applayer.tactics.controller.futures"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	@Bean
	public Docket activityApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("activityApi")
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.waben.stock.applayer.tactics.controller.activity"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	@Bean
	public Docket createRestApi() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6IlJvbGVfQXBwLCIsInVzZXJJZCI6Nywic2VyaWFsQ29kZSI6ImQwYTNkNWY2LWQyMzYtNDcwNy05MGVjLWMxYjRjNTE1M2UxZSIsInN1YiI6IjEzMTQ1OTQyMjAwIiwiZXhwIjo0MzIwMDAxNTI3NzU5NDQwfQ.ypyNSqStX_Sl5t4h9NLijYGD81oWzwAUH8gHFUCsXRzLCUeTWNVLBpEDadPfJBuyEVFImcG9kMke8IqFJH79Kw";
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("Authorization").description("token令牌").modelRef(new ModelRef("string")).parameterType("header")
				.defaultValue(token).required(false).build();
		pars.add(tokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).globalOperationParameters(pars).select()
				.apis(RequestHandlerSelectors.basePackage("com.waben.stock.applayer.tactics.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("wangbei", "https://github.com/wbfinance/finance.git", "");
		return new ApiInfoBuilder().title("Document Api").description("").license("Apache License Version 2.0")
				.contact(contact).version("1.0").build();
	}

}