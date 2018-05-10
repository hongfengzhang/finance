package com.waben.stock.applayer.promotion.business;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.manage.RoleReference;
import com.waben.stock.applayer.promotion.security.SecurityUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;

@Service
public class RoleBusiness {
    @Autowired
    @Qualifier("roleReference")
    private RoleReference roleReference;

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
        roleQuery.setOrganization(SecurityUtil.getUserDetails().getOrgId());
        roleQuery.setType(4);
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


    public RoleDto save(String name, List<Long> permissionIds) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(name);
        roleDto.setOrganization(SecurityUtil.getUserDetails().getOrgId());
        roleDto.setCreateTime(new Date());
        roleDto.setType(4);
        roleDto.setPermissionDtos(permissionDtos(permissionIds));

        Response<RoleDto> response = roleReference.add(roleDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public RoleDto revision(Long id, String name, List<Long> permissionIds) {
        RoleDto roleDto = roleReference.role(id).getResult();
        roleDto.setPermissionDtos(permissionDtos(permissionIds));
        roleDto.setName(name);
        Response<RoleDto> response = roleReference.add(roleDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Set<PermissionDto> permissionDtos(List<Long> permissionIds) {
        Set<PermissionDto> permissionDtos = new HashSet();
        for(Long permissionId : permissionIds) {
            PermissionDto permissionDto = new PermissionDto();
            permissionDto.setId(permissionId);
            permissionDtos.add(permissionDto);
        }
        return permissionDtos;
    }

    public void remove(Long id) {
        roleReference.delete(id);
    }

    public RoleDto save(RoleDto roleDto) {
        Response<RoleDto> response = roleReference.add(roleDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
