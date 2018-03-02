package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.entity.Staff;
import com.waben.stock.datalayer.manage.repository.RoleDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findById(Long id) {
        Role role = roleDao.retrieve(id);
        if (role == null) {
            throw new ServiceException(ExceptionConstant.ROLE_NOT_FOUND_EXCEPTION);
        }
        return role;
    }

    public Page<Role> pagesByQuery(final RoleQuery roleQuery) {
        Pageable pageable = new PageRequest(roleQuery.getPage(), roleQuery.getSize());
        Page<Role> pages = roleDao.page(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList();
                if (!StringUtils.isEmpty(roleQuery.getName())) {
                    Predicate nameQuery = criteriaBuilder.equal(root.get("name").as(String.class), roleQuery
                            .getName());
                    predicatesList.add(nameQuery);
                }
                if (!StringUtils.isEmpty(roleQuery.getCode())) {
                    Predicate codeQuery = criteriaBuilder.equal(root.get("code").as(String.class), roleQuery
                            .getCode());
                    predicatesList.add(codeQuery);
                }
                criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }

    public Role fetchById(Long id) {
        return roleDao.retrieve(id);
    }

    public Role revision(Role role) {
        return roleDao.update(role);
    }

    public void delete(Long id) {
        roleDao.delete(id);
    }

    public Role save(Role role) {
        return roleDao.create(role);
    }

    public List<Role> findRoles() {
        return roleDao.list();
    }
}
