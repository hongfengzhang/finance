package com.waben.stock.applayer.promotion.business;

import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.manage.RoleReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;

import javax.management.relation.Role;
import java.util.List;

@Service
public class RoleBusiness {
    @Autowired
    @Qualifier("roleReference")
    private RoleReference roleReference;

    public RoleDto save(RoleDto requestDto) {
        Response<RoleDto> response = roleReference.add(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public RoleDto saveRoleMenu(Long id, Long[] menuIds) {
        Response<RoleDto> response = roleReference.addRoleMenu(id,menuIds);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public RoleDto saveRolePermission(Long id, Long[] permissionIds) {
        Response<RoleDto> response = roleReference.addRolePermission(id,permissionIds);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<RoleDto> pages(RoleQuery roleQuery) {
        Response<PageInfo<RoleDto>> response = roleReference.pages(roleQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public RoleDto findById(Long id) {
        Response<RoleDto> response = roleReference.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public RoleDto findByOrganization(OrganizationDto organizationDto) {
        Response<RoleDto> response = roleReference.fetchByOrganizationAdmin(organizationDto.getId());
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public RoleDto bindAdminRoleWithPermissionAndMenu(Long id) {
        Response<RoleDto> response = roleReference.bindAdminRoleWithPermissionAndMenu(id, 4L);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<RoleDto> findByOrganization(Long id) {
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setOrganization(id);
        Response<PageInfo<RoleDto>> response = roleReference.pages(roleQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult().getContent();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
