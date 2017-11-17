package com.waben.stock.interfaces.exception;

import com.waben.stock.interfaces.pojo.ExceptionInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
 * @description 统一异常处理  第一种
 */
//@Component
public class ExecptionHandler implements HandlerExceptionResolver {
    Logger logger = LoggerFactory.getLogger(getClass());
    MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

    private List<ExceptionInformation> exceptions = new ArrayList<>();

    public ExecptionHandler() {
        this.exceptions.add(new ExceptionInformation(ServiceException.class, HttpServletResponse
                .SC_SERVICE_UNAVAILABLE, "503"));
        this.exceptions.add(new ExceptionInformation(NoHandlerFoundException.class, HttpServletResponse.SC_NOT_FOUND,
                "404"));
        this.exceptions.add(new ExceptionInformation(DataNotFoundException.class, HttpServletResponse.SC_OK, "205"));
        this.exceptions.add(new ExceptionInformation(IllegalArgumentException.class, HttpServletResponse
                .SC_BAD_REQUEST, "400"));
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        ModelAndView mv = new ModelAndView();
        Object message = "未知错误";
        String code = "0000";
        String error = "404";
        try {
            for (ExceptionInformation exception : exceptions) {
                logger.info(exception.getException().getName());
                Class exceptionClass = exception.getException();
                if (ex.getClass().equals(exceptionClass)) {
                    response.setStatus(exception.getHttpStatus());
                    code = ex.getMessage();
                    message = message(code);
                    error = exception.getError();
                }
            }
            logger.warn("请求：{},异常：{}", request.getRequestURI(), message);
        } finally {
            mv.addObject("message", message);
            mv.addObject("code", code);
            logger.info("响应状态码:{}", response.getStatus());
            if (request.getContentType() != null
                    && request.getContentType().indexOf("application/json") > -1) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
                response.setCharacterEncoding("UTF-8"); //避免乱码
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

    private String message(String type){
        String message = null;
        if (ExceptionMap.exceptionMap.containsKey(type)) {
            message = ExceptionMap.exceptionMap.get(type);
        }
        return message;
    }
}
