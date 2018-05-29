package com.waben.stock.datalayer.futures.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.entity.FuturesExchange;
import com.waben.stock.datalayer.futures.service.FuturesContractService;
import com.waben.stock.datalayer.futures.service.FuturesContractTermService;
import com.waben.stock.datalayer.futures.service.FuturesExchangeService;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTermAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesExchangeAdminQuery;
import com.waben.stock.interfaces.service.futures.FutureContractTermInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/contractTerm")
@Api(description = "期货合约接口列表")
public class FuturesContractTermController implements FutureContractTermInterface {

	@Autowired
	private FuturesContractTermService termService;
	
	@Autowired
	private FuturesContractService contractService;
	
	@Autowired
	private FuturesExchangeService exchangeService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Response<FuturesTermAdminDto> addContractTerm(@RequestBody FuturesTermAdminDto dto) {
		//获取市场
		FuturesExchangeAdminQuery exchange = new FuturesExchangeAdminQuery();
		exchange.setCode(dto.getExchangcode());
		exchange.setName(dto.getExchangename());
		exchange.setPage(0);
		exchange.setSize(Integer.MAX_VALUE);
		Page<FuturesExchange> exchangePage = exchangeService.pagesExchange(exchange);
		//获取品种
		FuturesContract contract = new FuturesContract();
		contract.setSymbol(dto.getCode());
		contract.setName(dto.getName());
		contract.setExchange(exchangePage.getContent().get(0));
//		Page<FuturesContract> contractPage = contractService.pagesContractAdmin(contract, 0, Integer.MAX_VALUE);
		
		//dto转entity
		FuturesContractTerm term = CopyBeanUtils.copyBeanProperties(FuturesContractTerm.class, dto, false);
//		term.setContract(contractPage.getContent().get(0));
		try {
			if(dto.getExpirationDate()!=null && !"".equals(dto.getExpirationDate())){
				term.setExpirationDate(sdf.parse(dto.getExpirationDate()));
			}
			if(dto.getFirstNoticeDate()!=null && !"".equals(dto.getFirstNoticeDate())){
				term.setFirstNoticeDate(sdf.parse(dto.getFirstNoticeDate()));
			}
			if(dto.getFirstPositonDate()!=null && !"".equals(dto.getFirstPositonDate())){
				term.setFirstPositonDate(sdf.parse(dto.getFirstPositonDate()));
			}
			if(dto.getLastTradingDate()!=null && !"".equals(dto.getLastTradingDate())){
				term.setLastTradingDate(sdf.parse(dto.getLastTradingDate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		FuturesContractTerm termResult = termService.addContractTerm(term);
		FuturesTermAdminDto result = CopyBeanUtils.copyBeanProperties(termResult, new FuturesTermAdminDto(), false);
		result.setExpirationDate(sdf.format(termResult.getExpirationDate()));
		result.setFirstNoticeDate(sdf.format(termResult.getFirstNoticeDate()));
		result.setFirstPositonDate(sdf.format(termResult.getFirstPositonDate()));
		result.setLastTradingDate(sdf.format(termResult.getLastTradingDate()));
		return new Response<>(result);
	}

	@Override
	public Response<FuturesTermAdminDto> modifyContractTerm(@RequestBody FuturesTermAdminDto dto) {
		//获取市场
		FuturesExchangeAdminQuery exchange = new FuturesExchangeAdminQuery();
		exchange.setCode(dto.getExchangcode());
		exchange.setName(dto.getExchangename());
		exchange.setPage(0);
		exchange.setSize(Integer.MAX_VALUE);
		Page<FuturesExchange> exchangePage = exchangeService.pagesExchange(exchange);
		//获取品种
		FuturesContract contract = new FuturesContract();
		contract.setSymbol(dto.getCode());
		contract.setName(dto.getName());
		contract.setExchange(exchangePage.getContent().get(0));
//		Page<FuturesContract> contractPage = contractService.pagesContractAdmin(contract, 0, Integer.MAX_VALUE);
		
		//dto转entity
		FuturesContractTerm term = CopyBeanUtils.copyBeanProperties(FuturesContractTerm.class, dto, false);
//		term.setContract(contractPage.getContent().get(0));
		try {
			if(dto.getExpirationDate()!=null && !"".equals(dto.getExpirationDate())){
				term.setExpirationDate(sdf.parse(dto.getExpirationDate()));
			}
			if(dto.getFirstNoticeDate()!=null && !"".equals(dto.getFirstNoticeDate())){
				term.setFirstNoticeDate(sdf.parse(dto.getFirstNoticeDate()));
			}
			if(dto.getFirstPositonDate()!=null && !"".equals(dto.getFirstPositonDate())){
				term.setFirstPositonDate(sdf.parse(dto.getFirstPositonDate()));
			}
			if(dto.getLastTradingDate()!=null && !"".equals(dto.getLastTradingDate())){
				term.setLastTradingDate(sdf.parse(dto.getLastTradingDate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		FuturesContractTerm termResult = termService.modifyContractTerm(term);
		FuturesTermAdminDto result = CopyBeanUtils.copyBeanProperties(termResult, new FuturesTermAdminDto(), false);
		result.setExpirationDate(sdf.format(termResult.getExpirationDate()));
		result.setFirstNoticeDate(sdf.format(termResult.getFirstNoticeDate()));
		result.setFirstPositonDate(sdf.format(termResult.getFirstPositonDate()));
		result.setLastTradingDate(sdf.format(termResult.getLastTradingDate()));
		return new Response<>(result);
	}

	@Override
	public void deleteContractTerm(@PathVariable Long id) {
		termService.deleteContractTerm(id);
	}

}
