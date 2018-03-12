package com.waben.stock.interfaces.pojo.query.organization;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class OrganizationAccountFlowQuery extends PageAndSortQuery {

    private Long  strategyTypeId;
    private String flowNo;
    private Long orgId;

    public Long getStrategyTypeId() {
        return strategyTypeId;
    }

    public void setStrategyTypeId(Long strategyTypeId) {
        this.strategyTypeId = strategyTypeId;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
