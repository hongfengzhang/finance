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
import com.waben.stock.datalayer.futures.service.FuturesContractService;
import com.waben.stock.datalayer.futures.service.FuturesContractTermService;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTermAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTermAdminQuery;
import com.waben.stock.interfaces.service.futures.FutureContractTermInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/contractTerm")
@Api(description = "期货合约接口列表")
public class FuturesContractTermController implements FutureContractTermInterface {

	@Autowired
	private FuturesContractTermService termService;
	
	@Autowired
	private FuturesContractService contractService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Response<FuturesTermAdminDto> addContractTerm(@RequestBody FuturesTermAdminDto dto) {
		//获取品种
		FuturesContract contract = contractService.findByContractId(dto.getContractId());
		//dto转entity
		FuturesContractTerm term = CopyBeanUtils.copyBeanProperties(FuturesContractTerm.class, dto, false);
		term.setContract(contract);
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
			if(dto.getForceUnwindDate()!=null && !"".equals(dto.getForceUnwindDate())){
				term.setForceUnwindDate(sdf.parse(dto.getForceUnwindDate()));
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
		result.setForceUnwindDate(sdf.format(termResult.getForceUnwindDate()));
		return new Response<>(result);
	}

	@Override
	public Response<FuturesTermAdminDto> modifyContractTerm(@RequestBody FuturesTermAdminDto dto) {
		//获取品种
		FuturesContract contract = contractService.findByContractId(dto.getContractId());
		//dto转entity
		FuturesContractTerm term = CopyBeanUtils.copyBeanProperties(FuturesContractTerm.class, dto, false);
		term.setContract(contract);
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
			if(dto.getForceUnwindDate()!=null && !"".equals(dto.getForceUnwindDate())){
				term.setForceUnwindDate(sdf.parse(dto.getForceUnwindDate()));
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
		result.setForceUnwindDate(sdf.format(termResult.getForceUnwindDate()));
		return new Response<>(result);
	}

	@Override
	public void deleteContractTerm(@PathVariable Long id) {
		termService.deleteContractTerm(id);
	}

	@Override
	public Response<PageInfo<FuturesTermAdminDto>> pagesTremAdmin(FuturesTermAdminQuery query) {
		Page<FuturesContractTerm> page = termService.pagesTermAdmin(query);
		PageInfo<FuturesTermAdminDto> pages = PageToPageInfo.pageToPageInfo(page, FuturesTermAdminDto.class);
		for(int i=0;i<pages.getContent().size();i++){
			pages.getContent().get(i).setExchangcode(page.getContent().get(i).getContract().getExchange().getCode());
			pages.getContent().get(i).setExchangename(page.getContent().get(i).getContract().getExchange().getName());
			pages.getContent().get(i).setSymbol(page.getContent().get(i).getContract().getSymbol());
			pages.getContent().get(i).setName(page.getContent().get(i).getContract().getName());
			pages.getContent().get(i).setExpirationDate(sdf.format(page.getContent().get(i).getExpirationDate()));
			pages.getContent().get(i).setFirstNoticeDate(sdf.format(page.getContent().get(i).getFirstNoticeDate()));
			pages.getContent().get(i).setFirstPositonDate(sdf.format(page.getContent().get(i).getFirstPositonDate()));
			pages.getContent().get(i).setLastTradingDate(sdf.format(page.getContent().get(i).getLastTradingDate()));
			pages.getContent().get(i).setForceUnwindDate(sdf.format(page.getContent().get(i).getForceUnwindDate()));
		}
		return new Response<>(pages);
	}

}
