package com.waben.stock.interfaces.exception;

import com.waben.stock.interfaces.constants.ExceptionConstant;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMap {

    public static Map<String, String> exceptionMap = new HashMap<String, String>();

    static {
        exceptionMap.put(ExceptionConstant.UNKNOW_EXCEPTION, "服务器未知异常");
        exceptionMap.put(ExceptionConstant.DATANOTFOUND_EXCEPTION, "数据没有找到");
        exceptionMap.put(ExceptionConstant.ARGUMENT_EXCEPTION, "参数异常");
        exceptionMap.put(ExceptionConstant.SECURITY_METHOD_UNSUPPORT_EXCEPTION, "安全验证方法不支持异常");
        exceptionMap.put(ExceptionConstant.MENU_SERVICE_EXCEPTION, "菜单服务异常");

        exceptionMap.put(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION, "发送短信失败");
    }
}
