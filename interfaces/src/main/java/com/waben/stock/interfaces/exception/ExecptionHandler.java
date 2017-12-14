package com.waben.stock.interfaces.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.waben.stock.interfaces.pojo.ExceptionInformation;

/**
 * @author yuyidi 2017-07-13 16:06:14
 * @class com.wangbei.exception.ExecptionHandler
 * @description 统一异常处理 第一种
 */
// @Component
public class ExecptionHandler implements HandlerExceptionResolver {
	Logger logger = LoggerFactory.getLogger(getClass());
	MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

	private List<ExceptionInformation> exceptions = new ArrayList<>();

	public ExecptionHandler() {
		// this.exceptions.add(
		// new ExceptionInformation(ServiceException.class,
		// HttpServletResponse.SC_SERVICE_UNAVAILABLE, "503"));
		this.exceptions
				.add(new ExceptionInformation(NoHandlerFoundException.class, HttpServletResponse.SC_NOT_FOUND, "404"));
		this.exceptions
				.add(new ExceptionInformation(DataNotFoundException.class, HttpServletResponse.SC_NO_CONTENT, "204"));
		this.exceptions.add(
				new ExceptionInformation(IllegalArgumentException.class, HttpServletResponse.SC_BAD_REQUEST, "400"));
		this.exceptions.add(new ExceptionInformation(NetflixCircuitException.class,
				HttpServletResponse.SC_SERVICE_UNAVAILABLE, "503"));
		this.exceptions.add(new ExceptionInformation(ServiceException.class, HttpServletResponse.SC_OK, "500"));
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();
		Object message = "未知错误";
		String code = "0000";
		String error = "503";
		try {
			Class exceptionClass = null;
			logger.info("当前异常:{}", ex.getClass());
			for (ExceptionInformation exception : exceptions) {
				exceptionClass = exception.getException();
				if (ex.getClass().equals(exceptionClass)) {
					response.setStatus(exception.getHttpStatus());
					code = ex.getMessage();
					message = message(code) != null ? message(code) : message;
					error = exception.getError();
					break;
				}
			}
			logger.error("请求：{},异常：{},{}", request.getRequestURI(), message, exceptionClass);
		} finally {
			mv.addObject("message", message);
			mv.addObject("code", code);
			logger.info("响应状态码:{}", response.getStatus());
			String contentType = request.getContentType();
			String isFeign = request.getHeader("feign");
			if (contentType != null && (contentType.indexOf("application/json") > -1
					|| contentType.indexOf("application/x-www-form-urlencoded") > -1)) {
				response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
				response.setCharacterEncoding("UTF-8"); // 避免乱码
				mv.setView(jsonView);
			} else if ("true".equals(isFeign) || ex instanceof ServiceException) {
				response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
				response.setCharacterEncoding("UTF-8"); // 避免乱码
				mv.setView(jsonView);
			} else {
				mv.setViewName(error);
			}
		}
		return mv;
	}

	public void extendException(List<ExceptionInformation> exceptions) {
		this.exceptions.addAll(exceptions);
	}

	private String message(String type) {
		String message = null;
		if (ExceptionMap.exceptionMap.containsKey(type)) {
			message = ExceptionMap.exceptionMap.get(type);
		}
		return message;
	}
}
