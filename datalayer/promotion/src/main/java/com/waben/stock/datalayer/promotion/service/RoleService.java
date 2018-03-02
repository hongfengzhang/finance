package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.Role;
import com.waben.stock.datalayer.promotion.repository.RoleDao;

/**
 * 角色 Service
 * 
 * @author luomengan
 *
 */
@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	public Role getRoleInfo(Long id) {
		return roleDao.retrieve(id);
	}

	@Transactional
	public Role addRole(Role role) {
		return roleDao.create(role);
	}

	@Transactional
	public Role modifyRole(Role role) {
		return roleDao.update(role);
	}

	@Transactional
	public void deleteRole(Long id) {
		roleDao.delete(id);
	}
	
	@Transactional
	public void deleteRoles(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					roleDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<Role> roles(int page, int limit) {
		return roleDao.page(page, limit);
	}
	
	public List<Role> list() {
		return roleDao.list();
	}

}
