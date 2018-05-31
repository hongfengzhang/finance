package com.waben.stock.applayer.admin.controller.futures;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.futures.FuturesOrderBusiness;
import com.waben.stock.interfaces.dto.admin.futures.FuturesOrderAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期货订单Controller
 * @author pengzhenliang
 *
 */
@RestController
@RequestMapping("/futures")
@Api(description="期货订单")
public class FuturesOrderController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FuturesOrderBusiness business;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@GetMapping("/pages")
	@ApiOperation(value = "查询期货成交订单")
	public Response<PageInfo<FuturesOrderAdminDto>> pages(FuturesTradeAdminQuery query) {
		return new Response<>(business.adminPagesByQuery(query));
	}
	
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
			if(query.getQueryType()==0){
				columnDescList = columnDescList();//成交订单
			}else if(query.getQueryType()==1){
				columnDescList = positionDescList();//持仓中订单
			}else if(query.getQueryType()==2){
				columnDescList = eveningDescList();//平仓结算订单
			}else if(query.getQueryType()==3){
				columnDescList = deputeDescList();//委托记录
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//成交订单
	private List<String> columnDescList() {
		List<String> result = new ArrayList<>();
		result.add("客户姓名");
		result.add("交易账户");
		result.add("合约名称");
		result.add("订单编号");
		result.add("成交编号");
		result.add("交易方向");
		result.add("开平");
		result.add("交易状态");
		result.add("成交手数");
		result.add("买入时间");
		result.add("买入价格");
		result.add("浮动盈亏");
		result.add("开仓手续费");
		result.add("保证金");
		result.add("隔夜手续费");
		result.add("止损金额");
		result.add("止盈金额");
		result.add("持仓天数");
		result.add("持仓截止日期");
		result.add("平仓时间");
		result.add("平仓价格");
		result.add("平仓盈亏");
		result.add("平仓手续费");
		result.add("风控状态");
		return result;
	}
	
	//持仓中订单
	private List<String> positionDescList() {
		List<String> result = new ArrayList<>();
		result.add("客户姓名");
		result.add("交易账户");
		result.add("合约名称");
		result.add("订单编号");
		result.add("成交编号");
		result.add("交易方向");
		result.add("交易状态");
		result.add("成交手数");
		result.add("买入时间");
		result.add("买入价格");
		result.add("浮动盈亏");
		result.add("开仓手续费");
		result.add("保证金");
		result.add("交易币种");
		result.add("止损金额");
		result.add("止盈金额");
		result.add("隔夜手续费");
		result.add("持仓截止日期");
		result.add("持仓天数");
		result.add("风控状态");
		return result;
	}
	
	//平仓结算记录
	private List<String> eveningDescList() {
		List<String> result = new ArrayList<>();
		result.add("客户姓名");
		result.add("交易账户");
		result.add("合约名称");
		result.add("订单编号");
		result.add("成交编号");
		result.add("交易方向");
		result.add("交易状态");
		result.add("成交手数");
		result.add("买入时间");
		result.add("买入价格");
		result.add("开仓手续费");
		result.add("保证金");
		result.add("止损金额");
		result.add("止盈金额");
		result.add("隔夜手续费");
		result.add("持仓天数");
		result.add("持仓截止日期");
		result.add("平仓时间");
		result.add("平仓价格");
		result.add("平仓盈亏");
		result.add("平仓手续费");
		result.add("平仓类型");
		return result;
	}
	
	//委托记录
	private List<String> deputeDescList() {
		List<String> result = new ArrayList<>();
		result.add("客户姓名");
		result.add("交易账户");
		result.add("合约名称");
		result.add("委托编号");
		result.add("成交编号");
		result.add("委托方向");
		result.add("委托状态");
		result.add("委托指定价格");
		result.add("当前价");
		result.add("委托手数");
		result.add("手续费");
		result.add("保证金");
		result.add("止损金额");
		result.add("止盈金额");
		result.add("委托时间");
		result.add("成交时间");
		return result;
	}
	
	//委托/退款记录
		private List<String> accountantList() {
			List<String> result = new ArrayList<>();
			result.add("客户姓名");
			result.add("交易账户");
			result.add("合约名称");
			result.add("委托编号");
			result.add("成交编号");
			result.add("委托方向");
			result.add("委托状态");
			result.add("委托指定价格");
			result.add("委托手数");
			result.add("手续费");
			result.add("隔夜手续费");
			result.add("保证金");
			result.add("止损金额");
			result.add("止盈金额");
			result.add("委托时间");
			result.add("状态");
			return result;
		}
}
