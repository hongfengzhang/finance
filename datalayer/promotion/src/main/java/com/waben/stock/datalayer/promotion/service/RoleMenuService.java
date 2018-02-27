package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.RoleMenu;
import com.waben.stock.datalayer.promotion.repository.RoleMenuDao;

/**
 * 角色菜单 Service
 * 
 * @author luomengan
 *
 */
@Service
public class RoleMenuService {

	@Autowired
	private RoleMenuDao roleMenuDao;

	public RoleMenu getRoleMenuInfo(Long id) {
		return roleMenuDao.retrieve(id);
	}

	@Transactional
	public RoleMenu addRoleMenu(RoleMenu roleMenu) {
		return roleMenuDao.create(roleMenu);
	}

	@Transactional
	public RoleMenu modifyRoleMenu(RoleMenu roleMenu) {
		return roleMenuDao.update(roleMenu);
	}

	@Transactional
	public void deleteRoleMenu(Long id) {
		roleMenuDao.delete(id);
	}
	
	@Transactional
	public void deleteRoleMenus(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					roleMenuDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<RoleMenu> roleMenus(int page, int limit) {
		return roleMenuDao.page(page, limit);
	}
	
	public List<RoleMenu> list() {
		return roleMenuDao.list();
	}

}
