package com.waben.stock.datalayer.futures.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.service.FuturesContractService;
import com.waben.stock.datalayer.futures.service.FuturesContractTermService;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
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
	
	@Autowired
	private FuturesOrderService orderService;
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Response<FuturesTermAdminDto> addContractTerm(@RequestBody FuturesTermAdminDto dto) {
		//dto转entity
		FuturesContractTerm term = CopyBeanUtils.copyBeanProperties(FuturesContractTerm.class, dto, false);
		term.setContract(contractService.findByContractId(dto.getContractId()));
		term.setContractNo(Integer.valueOf(dto.getContractNo()));
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
		if(termResult.getExpirationDate()!=null){
			result.setExpirationDate(sdf.format(termResult.getExpirationDate()));
		}
		if(termResult.getFirstNoticeDate()!=null){
			result.setFirstNoticeDate(sdf.format(termResult.getFirstNoticeDate()));
		}
		if(termResult.getFirstPositonDate()!=null){
			result.setFirstPositonDate(sdf.format(termResult.getFirstPositonDate()));
		}
		if(termResult.getLastTradingDate()!=null){
			result.setLastTradingDate(sdf.format(termResult.getLastTradingDate()));
		}
		if(termResult.getForceUnwindDate()!=null){
			result.setForceUnwindDate(sdf.format(termResult.getForceUnwindDate()));
		}
		result.setContractNo(termResult.getContractNo().toString());
		return new Response<>(result);
	}

	@Override
	public Response<FuturesTermAdminDto> modifyContractTerm(@RequestBody FuturesTermAdminDto dto) {
		if(!dto.isCurrent()){
			List<Long> contractTermId = new ArrayList<Long>();
			contractTermId.add(dto.getId());
			List<FuturesOrder> list = orderService.findByContractTermId(contractTermId);
			if(list.size()>0){
				Response<FuturesTermAdminDto> res = new Response<FuturesTermAdminDto>();
				res.setMessage("该合约正在被订单使用");
				res.setCode("200");
				res.setResult(null);
				return res;
			}
		}
		
		//获取品种
		FuturesContract contract = contractService.findByContractId(dto.getContractId());
		//dto转entity
		FuturesContractTerm term = CopyBeanUtils.copyBeanProperties(FuturesContractTerm.class, dto, false);
		term.setContract(contract);
		term.setContractNo(Integer.valueOf(dto.getContractNo()));
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
		if(termResult.getExpirationDate()!=null){
			result.setExpirationDate(sdf.format(termResult.getExpirationDate()));
		}
		if(termResult.getFirstNoticeDate()!=null){
			result.setFirstNoticeDate(sdf.format(termResult.getFirstNoticeDate()));
		}
		if(termResult.getFirstPositonDate()!=null){
			result.setFirstPositonDate(sdf.format(termResult.getFirstPositonDate()));
		}
		if(termResult.getLastTradingDate()!=null){
			result.setLastTradingDate(sdf.format(termResult.getLastTradingDate()));
		}
		if(termResult.getForceUnwindDate()!=null){
			result.setForceUnwindDate(sdf.format(termResult.getForceUnwindDate()));
		}
		result.setContractNo(termResult.getContractNo().toString());
		return new Response<>(result);
	}

	@Override
	public String deleteContractTerm(@PathVariable Long id) {
		List<Long> contractTermId = new ArrayList<Long>();
		contractTermId.add(id);
		if(orderService.findByContractTermId(contractTermId).size()>0){
			return "改合约正在被订单使用，无法删除";
		}
		termService.deleteContractTerm(id);
		return "删除成功";
	}

	@Override
	public Response<PageInfo<FuturesTermAdminDto>> pagesTremAdmin(FuturesTermAdminQuery query) {
		Page<FuturesContractTerm> page = termService.pagesTermAdmin(query);
		PageInfo<FuturesTermAdminDto> pages = PageToPageInfo.pageToPageInfo(page, FuturesTermAdminDto.class);
		for(int i=0;i<pages.getContent().size();i++){
			if(page.getContent().get(i).getContractNo()!=null){
				pages.getContent().get(i).setContractNo(page.getContent().get(i).getContractNo().toString());
			}
			if(page.getContent().get(i).getContract()!=null){
				pages.getContent().get(i).setContractId(page.getContent().get(i).getId());
				if(page.getContent().get(i).getContract().getSymbol()!=null){
					pages.getContent().get(i).setSymbol(page.getContent().get(i).getContract().getSymbol());
				}
				if(page.getContent().get(i).getContract().getName()!=null){
					pages.getContent().get(i).setName(page.getContent().get(i).getContract().getName());
				}
				if(page.getContent().get(i).getContract().getExchange()!=null){
					if(page.getContent().get(i).getContract().getExchange().getCode()!=null){
						pages.getContent().get(i).setExchangcode(page.getContent().get(i).getContract().getExchange().getCode());
					}
					if(page.getContent().get(i).getContract().getExchange().getName()!=null){
						pages.getContent().get(i).setExchangename(page.getContent().get(i).getContract().getExchange().getName());
					}
				}
			}
			
			if(page.getContent().get(i).getExpirationDate()!=null){
				pages.getContent().get(i).setExpirationDate(sdf.format(page.getContent().get(i).getExpirationDate()));
			}
			if(page.getContent().get(i).getFirstNoticeDate()!=null){
				pages.getContent().get(i).setFirstNoticeDate(sdf.format(page.getContent().get(i).getFirstNoticeDate()));
			}
			if(page.getContent().get(i).getFirstPositonDate()!=null){
				pages.getContent().get(i).setFirstPositonDate(sdf.format(page.getContent().get(i).getFirstPositonDate()));
			}
			if(page.getContent().get(i).getLastTradingDate()!=null){
				pages.getContent().get(i).setLastTradingDate(sdf.format(page.getContent().get(i).getLastTradingDate()));
			}
			if(page.getContent().get(i).getForceUnwindDate()!=null){
				pages.getContent().get(i).setForceUnwindDate(sdf.format(page.getContent().get(i).getForceUnwindDate()));
			}
		}
		return new Response<>(pages);
	}

}
