package com.waben.stock.datalayer.manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.manage.service.CircularsService;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.service.CircularsInterface;

/**
 * 通告 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/circulars")
public class CircularsController implements CircularsInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CircularsService circularsService;

	@Override
	public List<CircularsDto> getEnabledCircularsList() {
		return circularsService.getEnabledCircularsList();
	}
}
