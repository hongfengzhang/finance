package com.waben.stock.interfaces.service.publisher;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;

public interface BindCardInterface {

	@RequestMapping(value = "/bindBankCard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<BindCardDto> bindBankCard(@RequestBody BindCardDto bindCardDto);

	@RequestMapping(value = "/publisherBankCardList", method = RequestMethod.GET)
	Response<List<BindCardDto>> publisherBankCardList(@RequestParam(name = "serialCode") String serialCode);

}
