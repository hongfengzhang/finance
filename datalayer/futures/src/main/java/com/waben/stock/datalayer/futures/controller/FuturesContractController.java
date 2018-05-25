package com.waben.stock.datalayer.futures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.service.FuturesContractService;

@RestController
@RequestMapping("/futurescontract")
public class FuturesContractController {

	@Autowired
	private FuturesContractService futuresContractService;

//	private Response<PageInfo<FuturesContract>> pageList() {
//
//		PageInfo<FuturesContract> page = futuresContractService.pagesByQuery(query);
//
//		return new Response<>(page);
//	}

}
