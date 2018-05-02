package com.waben.stock.applayer.admin.controller.publisher;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.publisher.CapitalFlowBusiness;
import com.waben.stock.applayer.admin.util.PoiUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.publisher.CapitalFlowAdminDto;
import com.waben.stock.interfaces.enums.PaymentType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.CapitalFlowAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 资金流水 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/capitalFlow")
@Api(description = "资金流水")
public class CapitalFlowController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CapitalFlowBusiness business;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@GetMapping("/pages")
	@ApiOperation(value = "查询资金流水")
	public Response<PageInfo<CapitalFlowAdminDto>> pages(CapitalFlowAdminQuery query) {
		return new Response<>(business.adminPagesByQuery(query));
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ApiOperation(value = "导出流水信息")
	public void export(CapitalFlowAdminQuery query, HttpServletResponse svrResponse) {
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		PageInfo<CapitalFlowAdminDto> result = business.adminPagesByQuery(query);
		File file = null;
		FileInputStream is = null;
//		List<String> columnDescList = null;
		try {
			String fileName = "capitalflow_" + String.valueOf(System.currentTimeMillis());
			file = File.createTempFile(fileName, ".xls");
//			if ((result.getContent().get(0).getType().getIndex()).equals("1")) {
//				columnDescList = rechargeColumnDescList();
//			} else if ((result.getContent().get(0).getType().getIndex()).equals("2")) {
//				columnDescList = withdrawalsColumnDescList();
//			} else {
			List<String> columnDescList = columnDescList();
//			}
			List<List<String>> dataList = dataList(result.getContent());
			PoiUtil.writeDataToExcel("流水数据", file, columnDescList, dataList);

			is = new FileInputStream(file);
			svrResponse.setContentType("application/vnd.ms-excel");
			svrResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
			IOUtils.copy(is, svrResponse.getOutputStream());
			svrResponse.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION, "导出流水数据到excel异常：" + e.getMessage());
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

	private List<List<String>> dataList(List<CapitalFlowAdminDto> content) {
		List<List<String>> result = new ArrayList<>();
		for (CapitalFlowAdminDto trade : content) {
			List<String> data = new ArrayList<>();
			/*if ((trade.getType().getIndex()).equals("1")) {
				data.add(String.valueOf(trade.getId() == null ? "" : trade.getId()));
				data.add(trade.getPublisherName() == null ? "" : trade.getPublisherName());
				data.add(trade.getPublisherPhone() == null ? "" : trade.getPublisherPhone());
				data.add(trade.getOccurrenceTime() == null ? "" : sdf.format(trade.getOccurrenceTime()));
				data.add(String.valueOf(trade.getAmount() == null ? "" : trade.getAmount()));
				data.add(String.valueOf(trade.getAvailableBalance() != null ? trade.getAvailableBalance() : ""));
				data.add(String.valueOf(PaymentType.getByIndex(trade.getPaymentType().toString()).getType()));
			} else if ((trade.getType().getIndex()).equals("2")) {
				data.add(String.valueOf(trade.getId() == null ? "" : trade.getId()));
				data.add(trade.getPublisherName() == null ? "" : trade.getPublisherName());
				data.add(trade.getPublisherPhone() == null ? "" : trade.getPublisherPhone());
				data.add(trade.getOccurrenceTime() == null ? "" : sdf.format(trade.getOccurrenceTime()));
				data.add(String.valueOf(trade.getAmount() == null ? "" : trade.getAmount()));
				data.add(String.valueOf(trade.getAvailableBalance() != null ? trade.getAvailableBalance() : ""));
				
				data.add(trade.getBankCard() == null ? "" : trade.getBankCard());
				data.add(trade.getBankName() == null ? "" : trade.getBankName());
			} else {*/
				data.add(String.valueOf(trade.getId() == null ? "" : trade.getId()));
				data.add(trade.getPublisherName() == null ? "" : trade.getPublisherName());
				data.add(trade.getPublisherPhone() == null ? "" : trade.getPublisherPhone());
				data.add(trade.getFlowNo() == null ? "" : trade.getFlowNo());
				data.add(trade.getOccurrenceTime() == null ? "" : sdf.format(trade.getOccurrenceTime()));
				data.add(String.valueOf(trade.getType().getType() == null ? "" : trade.getType().getType()));
				data.add(String.valueOf(trade.getAmount() == null ? "" : trade.getAmount()));
				data.add(String.valueOf(trade.getAvailableBalance() != null ? trade.getAvailableBalance() : ""));
				data.add(trade.getsStockCode() != null ? trade.getsStockCode() : "");
				data.add(trade.getsStockName() != null ? trade.getsStockName() : "");
				Integer payType = trade.getPaymentType();
				String recharge = "";
				if (payType != null) {
					recharge = PaymentType.getByIndex(String.valueOf(payType)).getType();
				}
				data.add(recharge);
				data.add(trade.getBankCard() == null ? "" : trade.getBankCard());
				data.add(trade.getBankName() == null ? "" : trade.getBankName());
//			}
			result.add(data);
		}
		return result;
	}

	private List<String> columnDescList() {
		List<String> result = new ArrayList<>();
		result.add("订单ID");
		result.add("客户姓名");
		result.add("交易帐号");
		result.add("交易编号");
		result.add("交易时间");
		result.add("业务类型");
		result.add("交易金额");
		result.add("账户余额");
		result.add("股票代码");
		result.add("股票名称");
		result.add("充值方式");
		result.add("银行卡号");
		result.add("银行名称");
		return result;
	}

	/*// 充值
	private List<String> rechargeColumnDescList() {
		List<String> result = new ArrayList<>();
		result.add("序号");
		result.add("客户姓名");
		result.add("客户帐号");
		result.add("充值时间");
		result.add("充值金额");
		result.add("账户余额");
		result.add("充值方式");
		return result;
	}

	// 提现
	private List<String> withdrawalsColumnDescList() {
		List<String> result = new ArrayList<>();
		result.add("序号");
		result.add("客户姓名");
		result.add("客户帐号");
		result.add("提现时间");
		result.add("提现金额");
		result.add("账户余额");
		result.add("银行卡号");
		result.add("银行名称");
		return result;
	}*/

}
