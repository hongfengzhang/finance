package com.waben.stock.applayer.promotion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.PromotionStockOptionTradeBusiness;
import com.waben.stock.applayer.promotion.util.PoiUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.PromotionStockOptionTradeDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.PromotionStockOptionTradeQuery;
import com.waben.stock.interfaces.service.organization.PromotionStockOptionTradeInterface;

import io.swagger.annotations.Api;

/**
 * 推广渠道产生的期权交易 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/promotionStockOptionTrade")
@Api(description = "推广渠道产生的期权交易")
public class PromotionStockOptionTradeController implements PromotionStockOptionTradeInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public PromotionStockOptionTradeBusiness business;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/adminPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<PageInfo<PromotionStockOptionTradeDto>> adminPage(
			@RequestBody PromotionStockOptionTradeQuery query) {
		return new Response<>(business.adminPage(query));
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(PromotionStockOptionTradeQuery query, HttpServletResponse svrResponse) {
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		PageInfo<PromotionStockOptionTradeDto> result = business.adminPage(query);
		File file = null;
		FileInputStream is = null;
		try {
			String fileName = "optiontrade_" + String.valueOf(System.currentTimeMillis());
			file = File.createTempFile(fileName, ".xls");
			List<String> columnDescList = columnDescList();
			List<List<String>> dataList = dataList(result.getContent());
			PoiUtil.writeDataToExcel("期权交易数据", file, columnDescList, dataList);

			is = new FileInputStream(file);
			svrResponse.setContentType("application/vnd.ms-excel");
			svrResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
			IOUtils.copy(is, svrResponse.getOutputStream());
			svrResponse.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION, "导出期权交易数据到excel异常：" + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			if (file != null) {
				file.delete();
			}
		}
	}

	private List<List<String>> dataList(List<PromotionStockOptionTradeDto> content) {
		List<List<String>> result = new ArrayList<>();
		for (PromotionStockOptionTradeDto trade : content) {
			List<String> data = new ArrayList<>();
			data.add(String.valueOf(trade.getTradeId()));
			data.add(trade.getTradeNo());
			data.add(String.valueOf(trade.getPublisherId()));
			data.add(trade.getPublisherPhone());
			data.add(trade.getStockCode() + "/" + trade.getStockName());
			data.add(trade.getCycleName());
			Integer state = trade.getState();
			String stateStr = String.valueOf(state);
			if (state == 1) {
				stateStr = "待确认";
			} else if (state == 2) {
				stateStr = "申购失败";
			} else if (state == 3) {
				stateStr = "持仓中";
			} else if (state == 4 || state == 5) {
				stateStr = "结算中";
			} else if (state == 6) {
				stateStr = "已结算";
			}
			data.add(stateStr);
			data.add(String.valueOf(trade.getNominalAmount()));
			data.add(String.valueOf(trade.getRightMoney()));
			data.add(trade.getBuyingTime() != null ? sdf.format(trade.getBuyingTime()) : "");
			data.add(String.valueOf(trade.getBuyingPrice() != null ? trade.getBuyingPrice() : ""));
			data.add(trade.getSellingTime() != null ? sdf.format(trade.getSellingTime()) : "");
			data.add(String.valueOf(trade.getSellingPrice() != null ? trade.getSellingPrice() : ""));
			data.add(String.valueOf(trade.getLastPrice() != null ? trade.getLastPrice() : ""));
			data.add(String.valueOf(trade.getProfit() != null ? trade.getProfit() : ""));
			data.add(trade.getOrgCode() + "/" + trade.getOrgName());
			result.add(data);
		}
		return result;
	}

	private List<String> columnDescList() {
		List<String> result = new ArrayList<>();
		result.add("交易ID");
		result.add("交易单号");
		result.add("用户ID");
		result.add("手机号码");
		result.add("股票");
		result.add("持仓时间");
		result.add("交易状态");
		result.add("名义本金");
		result.add("权利金");
		result.add("买入时间");
		result.add("买入价格");
		result.add("卖出时间");
		result.add("卖出价格");
		result.add("当前价格");
		result.add("盈利（浮动）");
		result.add("所属机构代码/名称");
		return result;
	}

}
