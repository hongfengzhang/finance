package com.waben.stock.datalayer.futures.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.entity.enumconverter.FuturesProductTypeConverter;
import com.waben.stock.datalayer.futures.service.FuturesContractService;
import com.waben.stock.datalayer.futures.service.FuturesContractTermService;
import com.waben.stock.datalayer.futures.service.FuturesCurrencyRateService;
import com.waben.stock.datalayer.futures.service.FuturesExchangeService;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.interfaces.dto.admin.futures.FuturesContractAdminDto;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTermAdminDto;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesContractAdminQuery;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;
import com.waben.stock.interfaces.service.futures.FuturesContractInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/contract")
@Api(description = "期货品种接口列表")
public class FuturesContractController implements FuturesContractInterface {

	@Autowired
	private FuturesContractService futuresContractService;

	@Autowired
	private FuturesContractTermService futuresContractTermService;

	@Autowired
	private FuturesExchangeService exchangeService;

	@Autowired
	private FuturesCurrencyRateService rateService;
	
	@Autowired
	private FuturesOrderService orderService;

	@Autowired
	private FuturesCurrencyRateService futuresCurrencyRateService;

	private SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat fullSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Response<PageInfo<FuturesContractDto>> pagesContract(@RequestBody FuturesContractQuery contractQuery) {
		Page<FuturesContract> page = futuresContractService.pagesContract(contractQuery);
		PageInfo<FuturesContractDto> result = PageToPageInfo.pageToPageInfo(page, FuturesContractDto.class);
		List<FuturesContractDto> contractDtoList = result.getContent();
		for (FuturesContractDto futuresContractDto : contractDtoList) {
			for (FuturesContract futuresContract : page.getContent()) {
				if (futuresContractDto.getId() == futuresContract.getId()) {
					// 获取汇率信息
					FuturesCurrencyRate rate = futuresCurrencyRateService.findByCurrency(futuresContract.getCurrency());
					futuresContractDto.setExchangeEnable(futuresContract.getExchange().getEnable());
					futuresContractDto.setTimeZoneGap(futuresContract.getExchange().getTimeZoneGap());
//					futuresContractDto.setRate(rate.getRate() == null ? new BigDecimal(0) : rate.getRate());
//					futuresContractDto.setCurrencyName(futuresContract.getCurrencyRate().getCurrencyName());
				}
			}
			// 判断交易所 和 合约是否可用
			if (!futuresContractDto.getExchangeEnable() || !futuresContractDto.getEnable()) {
				futuresContractDto.setState(3);
				futuresContractDto.setCurrentTradeTimeDesc("交易异常");
				break;
			}

			List<FuturesContractTerm> termList = futuresContractTermService
					.findByListContractId(futuresContractDto.getId());

			if (termList == null || termList.size() == 0) {
				futuresContractDto.setState(1);
				futuresContractDto.setCurrentTradeTimeDesc("全天交易");
				break;
			}
			// 获取交易所对应的交易期限数据
			FuturesContractTerm term = termList.get(0);

			Calendar cal = Calendar.getInstance();
			// 计算时差后的时间
			cal.add(Calendar.HOUR_OF_DAY, -futuresContractDto.getTimeZoneGap());
			// 获取该时间是星期几
			int dayForweek = cal.get(Calendar.DAY_OF_WEEK);
			// 时差后的时间
			Date exchangeTime = cal.getTime();
			Boolean state = false;
			// 交易所合约交易时间
			String str = "";
			if (dayForweek == 1) {
				futuresContractDto.setCurrentTradeTimeDesc(term.getSunTradeTimeDesc());
				str = term.getSunTradeTime();
			}
			if (dayForweek == 2) {
				futuresContractDto.setCurrentTradeTimeDesc(term.getMonTradeTimeDesc());
				str = term.getMonTradeTime();
			}
			if (dayForweek == 3) {
				futuresContractDto.setCurrentTradeTimeDesc(term.getTueTradeTimeDesc());
				str = term.getTueTradeTime();
			}
			if (dayForweek == 4) {
				futuresContractDto.setCurrentTradeTimeDesc(term.getWedTradeTimeDesc());
				str = term.getWedTradeTime();
			}
			if (dayForweek == 5) {
				futuresContractDto.setCurrentTradeTimeDesc(term.getThuTradeTimeDesc());
				str = term.getThuTradeTime();
			}
			if (dayForweek == 6) {
				futuresContractDto.setCurrentTradeTimeDesc(term.getFriTradeTimeDesc());
				str = term.getFriTradeTime();
			}
			if (dayForweek == 7) {
				futuresContractDto.setCurrentTradeTimeDesc(term.getSatTradeTimeDesc());
				str = term.getSatTradeTimeDesc();
			}

			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				String st = strs[i].toString();
				String[] sts = st.trim().split("-");
				String dayStr = daySdf.format(exchangeTime);
				String fullStr = fullSdf.format(exchangeTime);
				if (fullStr.compareTo(dayStr + " " + sts[0].trim()) >= 0
						&& fullStr.compareTo(dayStr + " " + sts[1].trim()) < 0) {
					state = true;
					break;
				}
			}
			if (state) {
				futuresContractDto.setState(1);
			} else {
				futuresContractDto.setState(2);
			}
		}

		return new Response<>(result);
	}

	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param nowTime
	 *            当前时间
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Response<FuturesContractAdminDto> addContract(@RequestBody FuturesContractAdminDto contractDto) {
		FuturesContract fcontract = CopyBeanUtils.copyBeanProperties(FuturesContract.class, contractDto, false);

		fcontract.setExchange(exchangeService.findById(contractDto.getExchangeId()));
		fcontract.setCurrency(contractDto.getCurrency());
		if(contractDto.getProductType()!=null && !"".equals(contractDto.getProductType())){
			FuturesProductTypeConverter converter = new FuturesProductTypeConverter();
			fcontract.setProductType(converter.convertToEntityAttribute(Integer.valueOf(contractDto.getProductType())));
		}
		FuturesContract result = futuresContractService.saveExchange(fcontract);
		FuturesContractAdminDto resultDto = CopyBeanUtils.copyBeanProperties(result, new FuturesContractAdminDto(),
				false);
		
		if(result.getExchange()!=null){
			resultDto.setExchangcode(result.getExchange().getCode());
			resultDto.setExchangename(result.getExchange().getName());
			resultDto.setExchangeId(result.getExchange().getId());
			resultDto.setExchangeType(result.getExchange().getExchangeType());
		}
		if(result.getProductType()!=null){
			resultDto.setProductType(result.getProductType().getValue());
		}
		if(result.getCurrency()!=null&&!"".equals(result.getCurrency())){
			FuturesCurrencyRate rate = rateService.queryByName(result.getCurrency());;
			if(rate!=null){
				resultDto.setRate(rate.getRate());
			}
		}
		
		return new Response<>(resultDto);
	}

	@Override
	public Response<FuturesContractAdminDto> modifyContract(@RequestBody FuturesContractAdminDto contractDto) {
		if(!contractDto.getEnable()){
			List<Long> contractId = new ArrayList<Long>();
			contractId.add(contractDto.getId());
			List<FuturesOrder> list = orderService.findByContractId(contractId);
			if(list.size()>0){
				Response<FuturesContractAdminDto> res = new Response<FuturesContractAdminDto>();
				res.setCode("200");
				res.setMessage("该合约正在被订单使用");
				res.setResult(null);
				return res;
			}
		}
		
		FuturesContract fcontract = CopyBeanUtils.copyBeanProperties(FuturesContract.class, contractDto, false);

		fcontract.setExchange(exchangeService.findById(contractDto.getExchangeId()));
		fcontract.setCurrency(contractDto.getCurrency());
		
		if(contractDto.getProductType()!=null && !"".equals(contractDto.getProductType())){
			FuturesProductTypeConverter converter = new FuturesProductTypeConverter();
			fcontract.setProductType(converter.convertToEntityAttribute(Integer.valueOf(contractDto.getProductType())));
		}
		FuturesContract result = futuresContractService.modifyExchange(fcontract);
		FuturesContractAdminDto resultDto = CopyBeanUtils.copyBeanProperties(result, new FuturesContractAdminDto(),
				false);
		
		if(result.getExchange()!=null){
			resultDto.setExchangcode(result.getExchange().getCode());
			resultDto.setExchangename(result.getExchange().getName());
			resultDto.setExchangeId(result.getExchange().getId());
			resultDto.setExchangeType(result.getExchange().getExchangeType());
		}
		if(result.getProductType()!=null){
			resultDto.setProductType(result.getProductType().getValue());
		}
		if(result.getCurrency()!=null&&!"".equals(result.getCurrency())){
			FuturesCurrencyRate rate = rateService.queryByName(result.getCurrency());;
			if(rate!=null){
				resultDto.setRate(rate.getRate());
			}
		}
		return new Response<>(resultDto);
	}

	@Override
	public String deleteContract(@PathVariable Long id) {
		String message = futuresContractService.deleteExchange(id);
		return message;
	}

	@Override
	public Response<PageInfo<FuturesContractAdminDto>> pagesContractAdmin(
			@RequestBody FuturesContractAdminQuery query) {
		Page<FuturesContract> page = futuresContractService.pagesContractAdmin(query);
		PageInfo<FuturesContractAdminDto> result = PageToPageInfo.pageToPageInfo(page, FuturesContractAdminDto.class);
		for(int i=0;i<result.getContent().size();i++){
			if(page.getContent().get(i).getExchange()!=null){
				result.getContent().get(i).setExchangcode(page.getContent().get(i).getExchange().getCode());
				result.getContent().get(i).setExchangename(page.getContent().get(i).getExchange().getName());
				result.getContent().get(i).setExchangeId(page.getContent().get(i).getExchange().getId());
				result.getContent().get(i).setExchangeType(page.getContent().get(i).getExchange().getExchangeType());
			}
			if(page.getContent().get(i).getProductType()!=null){
				result.getContent().get(i).setProductType(page.getContent().get(i).getProductType().getValue());
			}
			
			if(page.getContent().get(i).getCurrency()!=null&&!"".equals(page.getContent().get(i).getCurrency())){
				FuturesCurrencyRate rate = rateService.queryByName(page.getContent().get(i).getCurrency());
				if(rate!=null){
					result.getContent().get(i).setRate(rate.getRate());
				}
			}
			List<FuturesContractTerm> list = futuresContractService.findByListContractId(page.getContent().get(i).getId());
			List<FuturesTermAdminDto> resultList = new ArrayList<FuturesTermAdminDto>();
			for (FuturesContractTerm term : list) {
				FuturesTermAdminDto dto = CopyBeanUtils.copyBeanProperties(term, new FuturesTermAdminDto(), false);
				resultList.add(dto);
			}
			result.getContent().get(i).setFuturesTermAdminDto(resultList);
		}
		return new Response<>(result);
	}

	@Override
	public Response<FuturesContractDto> findByContractId(@PathVariable Long contractId) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(FuturesContractDto.class,
				futuresContractService.findByContractId(contractId), false));
	}

}