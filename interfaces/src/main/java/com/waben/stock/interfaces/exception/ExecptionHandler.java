package com.waben.stock.interfaces.exception;

import com.waben.stock.interfaces.pojo.ArgumentInvalidResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyidi 2017-07-13 16:06:14
 * @class com.wangbei.exception.ExecptionHandler
 * @description 统一异常处理 第一种
 */
// @Component
public class ExecptionHandler implements HandlerExceptionResolver {
	Logger logger = LoggerFactory.getLogger(getClass());
	MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		ModelAndView mv = new ModelAndView();
		Object message = "未知错误";
		String code = "0000";
		String error = "404";
		try {
			if (ex instanceof ServiceException) {
				// 服务端服务不可用
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				ServiceException service = (ServiceException) ex;
				message = service.message();
				code = service.getType();
				error = "500";
			} else if (ex instanceof NoHandlerFoundException) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				message = "找不到处理器";
				code = "404";
				error = "404";
			} else if (ex instanceof DataNotFoundException) {
				response.setStatus(HttpServletResponse.SC_OK);
				DataNotFoundException dataNotFoundException = (DataNotFoundException) ex;
				message = StringUtils.isEmpty(dataNotFoundException.getMessage()) ? "没有响应数据"
						: dataNotFoundException.getMessage();
				code = "205";
			} else if (ex instanceof BindException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				BindException exception = (BindException) ex;
				List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
				// 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
				for (FieldError errorField : exception.getBindingResult().getFieldErrors()) {
					ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
					invalidArgument.setDefaultMessage(errorField.getDefaultMessage());
					invalidArgument.setField(errorField.getField());
					invalidArguments.add(invalidArgument);
				}
				message = invalidArguments;
				code = "400";
				error = "400";
			} else if (ex instanceof IllegalArgumentException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				IllegalArgumentException service = (IllegalArgumentException) ex;
				message = service.getMessage();
				code = "400";
				error = "400";
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				message = "系统异常";
				code = "500";
				error = "400";
			}
			logger.warn("请求：{},异常：{},堆栈：{}", request.getRequestURI(), message, ex);
		} finally {
			mv.addObject("message", message);
			mv.addObject("code", code);
			String contentType = request.getContentType();
			if (contentType != null && (contentType.indexOf("application/json") > -1
					|| contentType.indexOf("application/x-www-form-urlencoded") > -1)) {
				response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
				response.setCharacterEncoding("UTF-8"); // 避免乱码
				mv.setView(jsonView);
			} else {
				mv.setViewName(error);
			}
		}
		return mv;
	}
}
