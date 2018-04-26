package com.waben.stock.applayer.admin.controller.stockoption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.organization.PriceMarkupConfigBusiness;
import com.waben.stock.applayer.admin.business.stockcontent.StockBusiness;
import com.waben.stock.applayer.admin.business.stockoption.StockOptionCycleBusiness;
import com.waben.stock.applayer.admin.business.stockoption.StockOptionTradeBusiness;
import com.waben.stock.applayer.admin.util.PoiUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionAdminDto;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionBlacklistAdminDto;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionRiskAdminDto;
import com.waben.stock.interfaces.dto.organization.PriceMarkupConfigDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionAmountLimitDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionQuoteDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.PriceMarkupForm;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.stockoption.StockOptionAdminQuery;
import com.waben.stock.interfaces.pojo.query.admin.stockoption.StockOptionRiskAdminQuery;
import com.waben.stock.interfaces.util.JacksonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期权管理 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/stockOption")
@Api(description = "期权管理")
public class StockOptionTradeController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StockOptionTradeBusiness business;

	@Autowired
	private StockOptionCycleBusiness cycleBusiness;

	@Autowired
	private StockBusiness stockBusiness;

	@Autowired
	private PriceMarkupConfigBusiness markupBusiness;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@ApiOperation(value = "获取期权权利金加价比例")
	@GetMapping(value = "/pricemarkup")
	public Response<List<PriceMarkupConfigDto>> fetchPriceMarkup() {
		return new Response<>(markupBusiness.priceMarkupConfigList(1L, 2));
	}

	@ApiOperation(value = "设置期权权利金加价比例")
	@PostMapping(value = "/pricemarkup")
	public Response<String> settingPriceMarkup(String data) {
		Response<String> response = new Response<>();
		List<PriceMarkupForm> formList = JacksonUtil.decode(data,
				JacksonUtil.getGenericType(List.class, PriceMarkupForm.class));
		if (formList != null && formList.size() > 0) {
			for (PriceMarkupForm form : formList) {
				form.setOrgId(1L);
				form.setResourceType(2);
			}
			String result = markupBusiness.priceMarkupConfig(formList);
			response.setResult(result);
		} else {
			response.setResult("failed");
		}
		return response;
	}

	@ApiOperation(value = "设置单只股票额度")
	@PutMapping(value = "/single/amountlimit")
	public Response<StockOptionAmountLimitDto> singleAmountLimit(@RequestParam("stockCode") String stockCode,
			@RequestParam("amountLimit") BigDecimal amountLimit) {
		return new Response<>(business.modifyStockOptionLimit(stockCode, stockBusiness.fetchByCode(stockCode).getName(),
				false, amountLimit));
	}

	@ApiOperation(value = "设置全局股票额度")
	@PutMapping(value = "/global/amountlimit")
	public Response<StockOptionAmountLimitDto> globalAmountLimit(@RequestParam("amountLimit") BigDecimal amountLimit) {
		return new Response<>(business.modifyStockOptionLimit(null, null, true, amountLimit));
	}

	@ApiOperation(value = "获取全局期权交易限额")
	@GetMapping(value = "/amountlimit/global")
	public Response<StockOptionAmountLimitDto> fetchGlobalStockOptionLimit() {
		return new Response<>(business.fetchGlobalStockOptionLimit());
	}

	@ApiOperation(value = "单只股票使用全局限额设置")
	@PostMapping(value = "/amountlimit/delete")
	public Response<String> deleteStockOptionLimit(String stockCode) {
		business.deleteStockOptionLimit(stockCode);
		Response<String> result = new Response<>();
		result.setResult(stockCode);
		return result;
	}

	@ApiOperation(value = "设置平台期权报价")
	@PutMapping(value = "/modify/quote")
	public Response<StockOptionQuoteDto> modifyStockOptionQuote(@RequestParam("stockCode") String stockCode,
			@RequestParam("cycleId") Long cycleId, @RequestParam("rightMoneyRatio") BigDecimal rightMoneyRatio) {
		return new Response<>(business.modifyStockOptionQuote(stockCode, stockBusiness.fetchByCode(stockCode).getName(),
				cycleBusiness.fetchById(cycleId).getCycle(), rightMoneyRatio));
	}

	@GetMapping("/risk/normal/adminpages")
	@ApiOperation(value = "期权股票风控管理（正常股票）")
	public Response<PageInfo<StockOptionRiskAdminDto>> adminNormalRiskPagesByQuery(StockOptionRiskAdminQuery query) {
		return new Response<>(business.adminNormalRiskPagesByQuery(query));
	}

	@GetMapping("/risk/abnormal/adminpages")
	@ApiOperation(value = "期权股票风控管理（异常股票）")
	public Response<PageInfo<StockOptionRiskAdminDto>> adminAbnormalRiskPagesByQuery(StockOptionRiskAdminQuery query) {
		return new Response<>(business.adminAbnormalRiskPagesByQuery(query));
	}

	@GetMapping("/risk/black/adminpages")
	@ApiOperation(value = "期权股票风控管理（黑名单股票）")
	public Response<PageInfo<StockOptionBlacklistAdminDto>> adminBlackRiskPagesByQuery(
			StockOptionRiskAdminQuery query) {
		return new Response<>(business.adminBlackRiskPagesByQuery(query));
	}

	@GetMapping("/orglist")
	@ApiOperation(value = "期权机构列表")
	public Response<List<StockOptionOrgDto>> orgList() {
		return new Response<>(business.orgList());
	}

	@GetMapping("/pages")
	@ApiOperation(value = "查询期权交易")
	public Response<PageInfo<StockOptionAdminDto>> pages(StockOptionAdminQuery query) {
		return new Response<>(business.adminPagesByQuery(query));
	}

	@PutMapping(value = "/fail/{id}")
	@ApiOperation(value = "撤单")
	public Response<StockOptionTradeDto> fail(@PathVariable("id") Long id) {
		return new Response<>(business.fail(id));
	}

	@PutMapping(value = "/turnover/{id}/org/{orgid}")
	@ApiOperation(value = "申购成交")
	public Response<StockOptionTradeDto> turnover(@PathVariable("id") Long id, @PathVariable("orgid") Long orgid,
			@RequestParam("orgRightMoneyRatio") BigDecimal orgRightMoneyRatio,
			@RequestParam("buyingPrice") BigDecimal buyingPrice) {
		return new Response<>(business.turnover(id, orgid, orgRightMoneyRatio, buyingPrice));
	}

	@PutMapping(value = "/mark/{id}")
	@ApiOperation(value = "标记")
	public Response<StockOptionTradeDto> mark(@PathVariable("id") Long id, @RequestParam("isMark") Boolean isMark) {
		return new Response<>(business.mark(id, isMark));
	}

	@PutMapping(value = "/dosettlement/{id}")
	@ApiOperation(value = "结算")
	public Response<StockOptionTradeDto> settlement(@PathVariable("id") Long id,
			@RequestParam("sellingPrice") BigDecimal sellingPrice) {
		return new Response<>(business.settlement(id, sellingPrice));
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(StockOptionAdminQuery query, HttpServletResponse svrResponse) {
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		PageInfo<StockOptionAdminDto> result = business.adminPagesByQuery(query);
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

	private List<List<String>> dataList(List<StockOptionAdminDto> content) {
		List<List<String>> result = new ArrayList<>();
		for (StockOptionAdminDto trade : content) {
			List<String> data = new ArrayList<>();
			data.add(trade.getPublisherName());
			data.add(trade.getPublisherPhone());
			data.add(trade.getTradeNo());
			data.add(trade.getStockCode() + "/" + trade.getStockName());
			data.add(String.valueOf(trade.getNominalAmount()));
			data.add(trade.getCycleName());
			data.add(String.valueOf(trade.getRightMoneyRatio()));
			data.add(String.valueOf(trade.getOrgRightMoneyRatio()));

			// data.add(String.valueOf(trade.getTradeId()));
			// data.add(trade.getTradeNo());
			// data.add(String.valueOf(trade.getPublisherId()));
			// data.add(trade.getPublisherPhone());
			// data.add(trade.getStockCode() + "/" + trade.getStockName());
			// data.add(trade.getCycleName());
			// Integer state = trade.getState();
			// String stateStr = String.valueOf(state);
			// if (state == 1) {
			// stateStr = "待确认";
			// } else if (state == 2) {
			// stateStr = "申购失败";
			// } else if (state == 3) {
			// stateStr = "持仓中";
			// } else if (state == 4 || state == 5) {
			// stateStr = "结算中";
			// } else if (state == 6) {
			// stateStr = "已结算";
			// }
			// data.add(stateStr);
			// data.add(String.valueOf(trade.getNominalAmount()));
			// data.add(String.valueOf(trade.getRightMoney()));
			// data.add(trade.getBuyingTime() != null ?
			// sdf.format(trade.getBuyingTime()) : "");
			// data.add(String.valueOf(trade.getBuyingPrice() != null ?
			// trade.getBuyingPrice() : ""));
			// data.add(trade.getSellingTime() != null ?
			// sdf.format(trade.getSellingTime()) : "");
			// data.add(String.valueOf(trade.getSellingPrice() != null ?
			// trade.getSellingPrice() : ""));
			// data.add(String.valueOf(trade.getLastPrice() != null ?
			// trade.getLastPrice() : ""));
			// data.add(String.valueOf(trade.getProfit() != null ?
			// trade.getProfit() : ""));
			// data.add(trade.getOrgCode() + "/" + trade.getOrgName());
			// result.add(data);
		}
		return result;
	}

	private List<String> columnDescList() {
		List<String> result = new ArrayList<>();
		result.add("客户姓名");
		result.add("交易账户");
		result.add("订单编号");
		result.add("股票代码/名称");
		result.add("名义本金");
		result.add("行权周期");
		result.add("平台权利金");
		result.add("机构权利金");
		result.add("申购时间");
		result.add("买入时间");
		result.add("买入价格");
		result.add("卖出时间");
		result.add("卖出价格");
		result.add("当前价格");
		result.add("盈利（浮动）");
		result.add("订单状态");
		result.add("是否测试");
		result.add("是否标记");
		return result;
	}

}
