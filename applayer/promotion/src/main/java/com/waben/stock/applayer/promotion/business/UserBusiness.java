package com.waben.stock.applayer.promotion.business;

import com.waben.stock.applayer.promotion.util.SecurityAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.organization.UserReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.UserQuery;

@Service
public class UserBusiness {

    @Autowired
    @Qualifier("userReference")
    private UserReference userReference;
    @Autowired
    private RoleBusiness roleBusiness;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto fetchByUserName(String userName) {
        Response<UserDto> response = userReference.fetchByUserName(userName);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }


    public UserDto save(UserDto userDto) {
        //获取用户所属机构的管理员角色并绑定给当前用户
        Response<UserDto> userDtoResponse = userReference.fetchByUserName(userDto.getUsername());
        if(userDtoResponse==null) {
            OrganizationDto organizationDto = new OrganizationDto();
            organizationDto.setId(userDto.getOrgId());
            userDto.setOrg(organizationDto);
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            Response<UserDto> response = userReference.addition(userDto);
            String code = response.getCode();
            if ("200".equals(code)) {
                return response.getResult();
            } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
                throw new NetflixCircuitException(code);
            }
            throw new ServiceException(response.getCode());
        }else {
            throw new ServiceException(ExceptionConstant.ORGANIZATION_USER_EXIST);
        }
    }

    public UserDto saveUserRole(Long id, Long roleId) {
        Response<UserDto> response = userReference.bindRole(id,roleId);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<UserDto> pages(UserQuery userQuery) {
//        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
//        userQuery.setOrganization(userDto.getOrg().getId());
        userQuery.setOrganization(1L);
        Response<PageInfo<UserDto>> response = userReference.pages(userQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
    
    public void modifyPassword(Long userId, String oldPassword, String password) {
    	Response<Void> response = userReference.modifyPassword(userId, oldPassword, password);
        String code = response.getCode();
        if ("200".equals(code)) {
            return;
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public UserDto revisionState(Long id) {
        Response<UserDto> response = userReference.modifyState(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
