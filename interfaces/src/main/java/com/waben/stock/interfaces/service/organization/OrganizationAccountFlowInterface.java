package com.waben.stock.interfaces.service.organization;

import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.waben.stock.interfaces.pojo.Response;

public interface OrganizationAccountFlowInterface {

    /**
     * 获取结算流水列表
     *
     * @param query
     *            查询条件
     * @return 结算流水
     */
    @RequestMapping(value = "/pages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<PageInfo<OrganizationAccountFlowDto>> pages(@RequestBody OrganizationAccountFlowQuery query);
}
