package com.waben.stock.applayer.admin.controller.futures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.futures.FuturesContractTermBusiness;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTermAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTermAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/futuresContractTerm")
@Api(description="期货合约期限")
public class FuturesContractTermControllerr {

	@Autowired
	private FuturesContractTermBusiness business;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增合约期限")
    public Response<FuturesTermAdminDto> save(FuturesTermAdminDto query){
		FuturesTermAdminDto result = business.save(query);
        return new Response<>(result);
    }
	
	@PutMapping("/modify")
    @ApiOperation(value = "修改合约期限")
    public Response<FuturesTermAdminDto> modify(FuturesTermAdminDto dto){
		FuturesTermAdminDto result = business.modify(dto);
        return new Response<>(result);
    }
	
	@DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除合约期限")
    public Response<String> delete(@PathVariable("id") Long id){
		String mesage = business.deleteContract(id);
        return new Response<>(mesage);
    }
	
	@GetMapping("/pagesTerm")
	@ApiOperation(value = "查询合约期限")
	public Response<PageInfo<FuturesTermAdminDto>> pagesTermAdmin(FuturesTermAdminQuery query){
		return new Response<>(business.pagesTermAdmin(query));
	}
}
