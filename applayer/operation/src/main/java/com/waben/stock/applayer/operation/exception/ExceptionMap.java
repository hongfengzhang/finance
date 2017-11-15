package com.waben.stock.applayer.operation.exception;

import com.waben.stock.interfaces.constants.ExceptionConstant;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMap {

    public static Map<String, String> exceptionMap = new HashMap<String, String>();

    static {
        exceptionMap.put(ExceptionConstant.UNKNOW_EXCEPTION, "服务器未知异常");

    }
}
