package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.Menu;
import com.waben.stock.datalayer.promotion.repository.MenuDao;

/**
 * 菜单 Service
 * 
 * @author luomengan
 *
 */
@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;

	public Menu getMenuInfo(Long id) {
		return menuDao.retrieve(id);
	}

	@Transactional
	public Menu addMenu(Menu menu) {
		return menuDao.create(menu);
	}

	@Transactional
	public Menu modifyMenu(Menu menu) {
		return menuDao.update(menu);
	}

	@Transactional
	public void deleteMenu(Long id) {
		menuDao.delete(id);
	}
	
	@Transactional
	public void deleteMenus(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					menuDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<Menu> menus(int page, int limit) {
		return menuDao.page(page, limit);
	}
	
	public List<Menu> list() {
		return menuDao.list();
	}

}
