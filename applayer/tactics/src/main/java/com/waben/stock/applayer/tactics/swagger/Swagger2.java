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
		return new Docket(DocumentationType.SWAGGER_2).groupName("futuresApi")
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
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
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6IlJvbGVfQXBwLCIsInVzZXJJZCI6Nywic2VyaWFsQ29kZSI6ImY5MzBlMGFkLTE2NGYtNDUzNS1hYjBmLWZiYWU4Njg4M2M0MyIsInN1YiI6IjEzOTI4OTUyMjU0IiwiZXhwIjo0MzIwMDAxNTI3NDc1MzM1fQ.4pqQWcpsLTc0xM9didrSP_ePRH3dIszKkjp6bxgX9mv-jjhGjvwaYG8dKAteEJLbMgFICE7S06OEzAYwUD4fmg";
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