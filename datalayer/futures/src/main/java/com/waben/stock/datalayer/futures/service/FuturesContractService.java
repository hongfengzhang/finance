package com.waben.stock.datalayer.futures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.service.futures.FuturesContractInterface;

/**
 * 期货合约 service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesContractService {

	@Autowired
	private FuturesContractInterface futuresContractInterface;
}
