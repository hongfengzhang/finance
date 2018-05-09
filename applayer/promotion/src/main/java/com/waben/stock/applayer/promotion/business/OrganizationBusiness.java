package com.waben.stock.applayer.promotion.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.organization.OrganizationReference;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.TreeNode;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.OrganizationForm;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;

/**
 * 机构 Business
 *
 * @author luomengan
 */
@Service
public class OrganizationBusiness {

    @Autowired
    @Qualifier("organizationReference")
    private OrganizationReference reference;
    @Autowired
    private RoleBusiness roleBusiness;

    public OrganizationDto addition(OrganizationForm orgForm) {
        Response<OrganizationDto> response = reference.addition(orgForm);
        //创建机构后为机构默认创建一个管理员角色
        if ("200".equals(response.getCode())) {
            RoleDto orgAdminRole = new RoleDto();
            orgAdminRole.setCode("ADMIN");
            orgAdminRole.setName("管理员");
            orgAdminRole.setDescription("渠道管理员");
            orgAdminRole.setOrganization(response.getResult().getId());
            RoleDto role = roleBusiness.save(orgAdminRole);
            if (role != null) {
                return response.getResult();
            }
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<OrganizationDto> adminPage(OrganizationQuery query) {
        Response<PageInfo<OrganizationDto>> response = reference.adminPage(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<OrganizationDto> pages(OrganizationQuery query) {
        Response<PageInfo<OrganizationDto>> response = reference.pages(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public List<TreeNode> adminTree(Long orgId) {
        return reference.adminTree(orgId);
    }

    public List<OrganizationDto> listByParentId(Long parentId) {
        Response<List<OrganizationDto>> response = reference.listByParentId(parentId);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public OrganizationDetailDto detail(Long orgId) {
        Response<OrganizationDetailDto> response = reference.detail(orgId);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public OrganizationDto modifyName(Long id, String name) {
        Response<OrganizationDto> response = reference.modifyName(id, name);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }


}
