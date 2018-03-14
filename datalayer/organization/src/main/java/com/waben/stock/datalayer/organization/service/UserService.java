package com.waben.stock.datalayer.organization.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.entity.OrganizationAccountFlow;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.OrganizationState;
import com.waben.stock.interfaces.exception.DataNotFoundException;
import com.waben.stock.interfaces.pojo.query.organization.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.entity.User;
import com.waben.stock.datalayer.organization.repository.UserDao;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

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

    public Page<User> pagesByQuery(final UserQuery query) {

		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<User> pages = userDao.page(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery,
										 CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if(!StringUtils.isEmpty(query.getId())) {
					Predicate idQuery = criteriaBuilder.equal(root.get("id").as(Long.class), query
							.getId());
					predicateList.add(idQuery);
				}
				if(!StringUtils.isEmpty(query.getOrganization())) {
//					Join<User, Organization> join = root.join("org", JoinType.LEFT);
//					predicateList.add(criteriaBuilder.equal(join.get("id").as(Long.class), query.getOrganization()));
					Organization organization = new Organization();
					organization.setId(query.getOrganization());
					Predicate organizationQuery = criteriaBuilder.equal(root.get("org").as(Organization.class), organization);
					predicateList.add(organizationQuery);
				}
				if(!StringUtils.isEmpty(query.getUserName())) {
					Predicate userNameQuery = criteriaBuilder.equal(root.get("username").as(String.class), query
							.getUserName());
					predicateList.add(userNameQuery);
				}
				if(!StringUtils.isEmpty(query.getNickName())) {
					Predicate nickNameQuery = criteriaBuilder.equal(root.get("nickname").as(String.class), query
							.getNickName());
					predicateList.add(nickNameQuery);
				}
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createTime").as(Date.class)));
				criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
    }

	public User bindRole(Long id, Long role) {
		User user = userDao.retrieve(id);
		user.setRole(role);
		return userDao.update(user);
	}
}