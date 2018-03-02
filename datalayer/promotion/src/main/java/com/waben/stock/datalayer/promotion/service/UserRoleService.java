package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.UserRole;
import com.waben.stock.datalayer.promotion.repository.UserRoleDao;

/**
 * 用户角色 Service
 * 
 * @author luomengan
 *
 */
@Service
public class UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;

	public UserRole getUserRoleInfo(Long id) {
		return userRoleDao.retrieve(id);
	}

	@Transactional
	public UserRole addUserRole(UserRole userRole) {
		return userRoleDao.create(userRole);
	}

	@Transactional
	public UserRole modifyUserRole(UserRole userRole) {
		return userRoleDao.update(userRole);
	}

	@Transactional
	public void deleteUserRole(Long id) {
		userRoleDao.delete(id);
	}
	
	@Transactional
	public void deleteUserRoles(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					userRoleDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<UserRole> userRoles(int page, int limit) {
		return userRoleDao.page(page, limit);
	}
	
	public List<UserRole> list() {
		return userRoleDao.list();
	}

}
