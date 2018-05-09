package com.waben.stock.interfaces.pojo.query.organization;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="OrganizationAccountVo",description="代理商资产查询对象")
public class OrganizationAccountQuery extends PageAndSortQuery {
    //机构代码
    @ApiModelProperty(value = "代理商代码")
    private String code;
    //机构名称
    @ApiModelProperty(value = "代理商名称")
    private String name;
    //机构ID
    @ApiModelProperty(value = "代理商id")
    private Long  id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
