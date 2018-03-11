package com.waben.stock.datalayer.organization.wrapper;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.waben.stock.interfaces.warpper.converter.DateConverter;
import com.waben.stock.interfaces.warpper.converter.UniversalEnumConverterFactory;

/**
 * @author Created by yuyidi on 2017/6/27.
 * @desc
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

//	@Value("${custom.outer.resources}")
//	private String outerResources;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		super.addFormatters(registry);
		registry.addConverterFactory(new UniversalEnumConverterFactory());
		registry.addConverter(new DateConverter());
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new PromotionExecptionHandler());
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

//		File dir = new File(outerResources);
//		if (dir.exists()) {
//			File[] files = dir.listFiles();
//			if (files != null && files.length > 0) {
//				for (File file : files) {
//					if (file.isDirectory()) {
//						String fileName = file.getName();
//						String fileDir = "file:" + outerResources + fileName + "/";
//						registry.addResourceHandler("/" + fileName + "/**").addResourceLocations(fileDir);
//					}
//				}
//			}
//		}
		super.addResourceHandlers(registry);
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}

}
