package com.waben.stock.datalayer.futures.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.service.FuturesContractService;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;
import com.waben.stock.interfaces.service.futures.FuturesContractInterface;
import com.waben.stock.interfaces.util.PageToPageInfo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/contract")
@Api(description = "期货合约接口列表")
public class FuturesContractController implements FuturesContractInterface {

	@Autowired
	private FuturesContractService futuresContractService;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

	@Override
	public Response<PageInfo<FuturesContractDto>> pagesContract(@RequestBody FuturesContractQuery contractQuery)
			throws Throwable {
		Page<FuturesContract> page = futuresContractService.pagesContract(contractQuery);
		PageInfo<FuturesContractDto> result = PageToPageInfo.pageToPageInfo(page, FuturesContractDto.class);
		List<FuturesContractDto> contractDtoList = result.getContent();
		for (FuturesContractDto futuresContractDto : contractDtoList) {
			for (FuturesContract futuresContract : page.getContent()) {
				if (futuresContractDto.getId() == futuresContract.getId()) {
					futuresContractDto.setChangeEnable(futuresContract.getExchange().getEnable());
					futuresContractDto.setTimeZoneGap(futuresContract.getExchange().getTimeZoneGap());
				}
			}
			// 判断交易所 和 合约是否可用
			if (!futuresContractDto.getChangeEnable() || !futuresContractDto.getEnable()) {
				futuresContractDto.setState(3);
				futuresContractDto.setDescribe("交易异常");
				break;
			}

			List<FuturesContractTerm> termList = futuresContractService
					.findByListContractId(futuresContractDto.getId());

			if (termList == null || termList.size() == 0) {
				futuresContractDto.setState(1);
				futuresContractDto.setDescribe("全天交易");
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
				futuresContractDto.setDescribe(term.getMonTradeTimeDesc());
				str = term.getMonTradeTime();
			}
			if (dayForweek == 2) {
				futuresContractDto.setDescribe(term.getTueTradeTimeDesc());
				str = term.getTueTradeTime();
			}
			if (dayForweek == 3) {
				futuresContractDto.setDescribe(term.getWedTradeTimeDesc());
				str = term.getWedTradeTime();
			}
			if (dayForweek == 4) {
				futuresContractDto.setDescribe(term.getThuTradeTimeDesc());
				str = term.getThuTradeTime();
			}
			if (dayForweek == 5) {
				futuresContractDto.setDescribe(term.getFriTradeTimeDesc());
				str = term.getFriTradeTime();
			}
			if (dayForweek == 6) {
				futuresContractDto.setDescribe(term.getSatTradeTimeDesc());
				str = term.getSatTradeTimeDesc();
			}
			if (dayForweek == 7) {
				futuresContractDto.setDescribe(term.getSunTradeTimeDesc());
				str = term.getSunTradeTime();
			}
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				String st = strs[i].toString();
				String[] sts = st.trim().split("-");
				Calendar beginc = Calendar.getInstance();
				cal.add(Calendar.HOUR_OF_DAY, -futuresContractDto.getTimeZoneGap());
				Date begin = beginc.getTime();
				begin.setHours(sdf.parse(sts[0].trim().toString()).getHours());
				begin.setMinutes(sdf.parse(sts[0].trim().toString()).getMinutes());
				Calendar endc = Calendar.getInstance();
				cal.add(Calendar.HOUR_OF_DAY, -futuresContractDto.getTimeZoneGap());
				Date end = endc.getTime();
				end.setHours(sdf.parse(sts[1].trim().toString()).getHours());
				end.setMinutes(sdf.parse(sts[1].trim().toString()).getMinutes());
				state = FuturesContractController.belongCalendar(exchangeTime, begin, end);
				if (state) {
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

}
