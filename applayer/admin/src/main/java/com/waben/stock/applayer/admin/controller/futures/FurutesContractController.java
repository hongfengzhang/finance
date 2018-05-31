package com.waben.stock.applayer.admin.controller.futures;

import static org.mockito.Matchers.booleanThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.futures.FuturesContractBusiness;
import com.waben.stock.applayer.admin.util.PoiUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.futures.FuturesContractAdminDto;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTermAdminDto;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionAdminDto;
import com.waben.stock.interfaces.dto.futures.FuturesContractTermDto;
import com.waben.stock.interfaces.dto.futures.FuturesExchangeDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesContractAdminQuery;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesExchangeAdminQuery;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
	
	@Autowired
	private FuturesContractBusiness business;
	@GetMapping("/pagesContractAdmin")
	@ApiOperation(value = "查询品种")
	public Response<PageInfo<FuturesContractAdminDto>> pageContracntAdmin(FuturesContractAdminQuery query){
		return new Response<>(business.pagesContractAdmin(query));
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增期货市场")
    public Response<FuturesExchangeDto> save(FuturesExchangeDto query){
		FuturesExchangeDto result = business.save(query);
        return new Response<>(result);
    }
	
	@GetMapping("/pagesExchange")
    @ApiOperation(value = "查询期货市场")
	public Response<PageInfo<FuturesExchangeDto>> pagesExchange(FuturesExchangeAdminQuery query){
		PageInfo<FuturesExchangeDto> response = business.pagesExchange(query);
		return new Response<>(response);
	}
	
	@PutMapping("/modify")
    @ApiOperation(value = "修改期货市场")
    public Response<FuturesExchangeDto> modify(FuturesExchangeDto exchangeDto){
		FuturesExchangeDto result = business.modify(exchangeDto);
        return new Response<>(result);
    }
	
	@DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除期货市场")
    public Response<Integer> delete(@PathVariable("id") Long id){
        business.delete(id);
        return new Response<>(1);
    }
	
	@RequestMapping(value = "/futuresContract/save", method = RequestMethod.POST)
	@ApiOperation(value = "添加品种")
	public Response<FuturesContractAdminDto> savec(FuturesContractAdminDto query){
		FuturesContractAdminDto result = business.save(query);
		return new Response<>(result);
	}
	
	@PutMapping("/futuresContract/modify")
	@ApiOperation(value = "修改品种")
	public Response<FuturesContractAdminDto> modifyc(FuturesContractAdminDto query){
		FuturesContractAdminDto result = business.modify(query);
		return new Response<>(result);
	}
	
	@DeleteMapping("/futuresContract/delete/{id}")
    @ApiOperation(value = "删除品种")
    public Response<Integer> deleteContract(@PathVariable("id") Long id){
        business.deleteContract(id);
        return new Response<>(1);
    }
	
	@GetMapping("/exportContract")
	@ApiOperation(value = "导出品种数据")
	public void exportContract(FuturesContractAdminQuery query, HttpServletResponse svrResponse){
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		PageInfo<FuturesContractAdminDto> response = business.pagesContractAdmin(query);
		File file = null;
		FileInputStream is = null;
		
		List<String> columnDescList = null;
		try {
			String fileName = "contract_" + String.valueOf(System.currentTimeMillis());
			file = File.createTempFile(fileName, ".xls");
			if (query.getQueryType() == 0) {
				columnDescList = contractList();
			}
			
			List<List<String>> dataList = dataList(response.getContent(), query.getQueryType());
			PoiUtil.writeDataToExcel("期权品种数据", file, columnDescList, dataList);

			is = new FileInputStream(file);
			svrResponse.setContentType("application/vnd.ms-excel");
			svrResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
			IOUtils.copy(is, svrResponse.getOutputStream());
			svrResponse.getOutputStream().flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION, "导出期货品种数据到excel异常：" + e.getMessage());
		}finally {
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
	
	private List<List<String>> dataList(List<FuturesContractAdminDto> content, Integer type) {
		List<List<String>> result = new ArrayList<>();
		for (FuturesContractAdminDto dto : content) {
			Boolean enable = dto.getEnable();
			String enables = "";
			if(enable != null && enable){
				enables = "启用";
			}else{
				enables = "未启用";
			}
			
			String currencyRate = "";
			if(!dto.getRate().equals(0)){
				currencyRate = "1人民币="+dto.getRate()+dto.getCurrency();
			}
			String exchangeType = "";
			if(dto.getExchangeType() == 1){
				exchangeType = "外盘";
			}else {
				exchangeType = "内盘";
			}
			
			String unwindPoint = "";
			if(dto.getUnwindPointType()==1&&dto.getUnwindServiceFee()!=null){
				unwindPoint = dto.getUnwindServiceFee()+"%";
			}else{
				unwindPoint = dto.getUnwindServiceFee().toString();
			}
			
			List<String> data = new ArrayList<>();
			if(type == 0){
				String exchangCode = dto.getExchangcode() == null ? "" : dto.getExchangcode();
				String exchangName = dto.getExchangename() == null ? "" : dto.getExchangename();
				data.add(exchangCode+"/"+exchangName);
				data.add(dto.getExchangeType() == null ? "" : exchangeType);
				data.add(dto.getSymbol() == null ? "" : dto.getSymbol());
				data.add(dto.getProductType() == null ? "" : dto.getProductType());
				data.add(dto.getCurrency() == null ? "" : dto.getCurrency());
				data.add(dto.getRate() == null ? "" : currencyRate);
				data.add(dto.getMultiplier() == null ? "" : dto.getMultiplier().toString());
				data.add(dto.getMinWave() == null ? "" : dto.getMinWave().toString());
				data.add(dto.getPerWaveMoney() == null ? "" : dto.getPerWaveMoney().toString());
				data.add(dto.getPerContractValue() == null ? "" : dto.getPerContractValue().toString());
				data.add(dto.getPerUnitReserveFund() == null ? "" : dto.getPerUnitReserveFund().toString());
				data.add(dto.getPerUnitUnwindPoint() == null ? "" : unwindPoint);
				data.add(dto.getOpenwindServiceFee() == null ? "" : dto.getOpenwindServiceFee().toString());
				data.add(dto.getUnwindServiceFee() == null ? "" : dto.getUnwindServiceFee().toString());
				data.add(dto.getOvernightPerUnitDeferredFee() == null ? "" : dto.getOvernightPerUnitDeferredFee().toString());
				data.add(dto.getOvernightPerUnitReserveFund() == null ? "" : dto.getOvernightPerUnitReserveFund().toString());
				data.add(dto.getUserTotalLimit() == null ? "" : dto.getUserTotalLimit().toString());
				data.add(dto.getPerOrderLimit() == null ? "" : dto.getPerOrderLimit().toString());
				data.add(dto.getOvernightTime() == null ? "" : dto.getOvernightTime());
				data.add("");
			}
			result.add(data);
		}
		return result;
	}
	
	
	private List<String> contractList() {
		List<String> result = new ArrayList<>();
		result.add("交易所类别/名称");
		result.add("境外标记");
		result.add("合约代码");
		result.add("产品名称");
		result.add("产品分类");
		result.add("货币单位");
		result.add("汇率");
		result.add("交易单位");
		result.add("最小波动点数");
		result.add("波动盈亏");
		result.add("1手合约价值");
		result.add("1手保证金");
		result.add("1手强平点");
		result.add("1手开仓手续费");
		result.add("1手平仓手续费");
		result.add("1手递延费");
		result.add("1手隔夜保证金");
		result.add("单品持仓总额");
		result.add("单品持仓限额");
		result.add("隔夜时间");
		result.add("风控状态");
		return result;
	}
	
}
