package com.waben.stock.applayer.tactics.controller.futures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.futures.FuturesContractBusiness;
import com.waben.stock.applayer.tactics.dto.futures.FuturesContractQuotationDto;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.enums.FuturesProductType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期货合约 Controller
 * 
 * @author sunl
 *
 */
@RestController
@RequestMapping("/futuresContract")
@Api(description = "期货合约")
public class FuturesContractController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractBusiness futuresContractBusiness;

	// @GetMapping("/pagesContract")
	// @ApiOperation(value = "获取期货合约列表")
	// public Response<PageInfo<FuturesContractQuotationDto>> pagesContract(int
	// page, int size) throws Throwable {
	// FuturesContractQuery query = new FuturesContractQuery();
	// query.setPage(page);
	// query.setSize(size);
	// query.setContractId(0L);
	// PageInfo<FuturesContractDto> contractPage =
	// futuresContractBusiness.pagesContract(query);
	// List<FuturesContractQuotationDto> quotationList = futuresContractBusiness
	// .pagesQuotations(contractPage.getContent());
	// Collections.sort(quotationList, new ContractComparator());
	// return new Response<>(new PageInfo<>(quotationList,
	// contractPage.getTotalPages(), contractPage.getLast(),
	// contractPage.getTotalElements(), contractPage.getSize(),
	// contractPage.getNumber(),
	// contractPage.getFrist()));
	// }

	@GetMapping("/pagesContract")
	@ApiOperation(value = "获取期货合约列表")
	public Response<List<List<FuturesContractQuotationDto>>> pagesContract() throws Throwable {
		FuturesContractQuery query = new FuturesContractQuery();
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		PageInfo<FuturesContractDto> contractPage = futuresContractBusiness.pagesContract(query);
		List<FuturesContractQuotationDto> quotationList = futuresContractBusiness
				.pagesQuotations(contractPage.getContent());
		// 对结果根据合约类别分组
		Map<FuturesProductType, List<FuturesContractQuotationDto>> productTypeMap = new HashMap<>();
		for (FuturesContractQuotationDto quotation : quotationList) {
			FuturesProductType type = quotation.getProductType();
			if (productTypeMap.containsKey(type)) {
				productTypeMap.get(type).add(quotation);
			} else {
				List<FuturesContractQuotationDto> value = new ArrayList<>();
				value.add(quotation);
				productTypeMap.put(type, value);
			}
		}
		// 对分组进行排序
		List<List<FuturesContractQuotationDto>> result = new ArrayList<>(productTypeMap.values());
		Collections.sort(result, new Comparator<List<FuturesContractQuotationDto>>() {
			@Override
			public int compare(List<FuturesContractQuotationDto> o1, List<FuturesContractQuotationDto> o2) {
				return o1.get(0).getProductType().getSort() - o2.get(0).getProductType().getSort();
			}
		});
		return new Response<>(result);
	}

}
