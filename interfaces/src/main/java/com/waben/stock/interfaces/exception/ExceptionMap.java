package com.waben.stock.interfaces.exception;

import com.waben.stock.interfaces.constants.ExceptionConstant;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMap {

    public static Map<String, String> exceptionMap = new HashMap<String, String>();

    static {
        exceptionMap.put(ExceptionConstant.UNKNOW_EXCEPTION, "服务器未知异常");
        
        exceptionMap.put(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION, "发送短信失败");
    }
}
