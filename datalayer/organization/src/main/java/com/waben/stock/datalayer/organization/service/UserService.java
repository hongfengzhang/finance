package com.waben.stock.datalayer.organization.service;

import java.util.List;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.entity.User;
import com.waben.stock.datalayer.organization.repository.UserDao;

/**
 * 机构管理用户 Service
 * 
 * @author luomengan
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User getUserInfo(Long id) {
		return userDao.retrieve(id);
	}

	@Transactional
	public User addUser(User user) {
		return userDao.create(user);
	}

	@Transactional
	public User modifyUser(User user) {
		return userDao.update(user);
	}

	@Transactional
	public void deleteUser(Long id) {
		userDao.delete(id);
	}
	
	@Transactional
	public void deleteUsers(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					userDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<User> users(int page, int limit) {
		return userDao.page(page, limit);
	}
	
	public List<User> list() {
		return userDao.list();
	}


	public User findByUserName(String userName) {
		User result = userDao.retrieveByUserName(userName);
		if (result == null) {
			throw new DataNotFoundException(ExceptionConstant.ORGANIZATION_USER_NOT_FOUND);
		}
		return result;
	}
}
