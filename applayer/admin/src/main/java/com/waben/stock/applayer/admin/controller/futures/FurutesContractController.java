package com.waben.stock.applayer.admin.controller.futures;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期货设置Controller
 * @author pengzhenliang
 *
 */
@RestController
@RequestMapping("/futuresContract")
@Api(description="期货设置")
public class FurutesContractController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ApiOperation(value = "导出期货订单信息")
	public void export(FuturesTradeAdminQuery query, HttpServletResponse svrResponse){
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		File file = null;
		FileInputStream is = null;
		
		List<String> columnDescList = null;
		try {
			String fileName = "futurestrade_" + String.valueOf(System.currentTimeMillis());
			file = File.createTempFile(fileName, ".xls");
//			if(query.getQueryType()==0){
//				columnDescList = columnDescList();//成交订单
//			}else if(query.getQueryType()==1){
//				columnDescList = positionDescList();//持仓中订单
//			}else if(query.getQueryType()==2){
//				columnDescList = eveningDescList();//平仓结算订单
//			}else if(query.getQueryType()==3){
//				columnDescList = deputeDescList();//委托记录
//			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
