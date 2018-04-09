package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Menu;
import com.waben.stock.datalayer.manage.entity.Permission;
import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.repository.RoleDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
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
import java.util.*;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MenuService menuService;

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
                if(!StringUtils.isEmpty(roleQuery.getId())) {
                    Predicate idQuery = criteriaBuilder.equal(root.get("id").as(Long.class), roleQuery
                            .getId());
                    predicatesList.add(idQuery);
                }
                if(!StringUtils.isEmpty(roleQuery.getOrganization())) {
                    Predicate organizationQuery = criteriaBuilder.equal(root.get("organization").as(Long.class), roleQuery
                            .getOrganization());
                    predicatesList.add(organizationQuery);
                }
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
        Role retrieve = roleDao.retrieve(role.getId());
        retrieve.setCode(role.getCode());
        retrieve.setName(role.getName());
        retrieve.setDescription(role.getDescription());
        return roleDao.update(retrieve);
    }

    public void delete(Long id) {
        roleDao.delete(id);
    }

    public Role save(Role role) {
        Role result = roleDao.create(role);
        if (result != null) {
            if (result.getCode().equals("ADMIN")) {
                //添加渠道管理员
                if (result.getDescription().equals("渠道管理员")) {
                    bindRoleWithPermissionAndMenu(result.getId(), 4L);
                }
            }
        }
        return result;
    }

    public List<Role> findRoles() {
        return roleDao.list();
    }

    public Role saveRoleMenu(Long id, Long[] menuIds) {
        Role role = roleDao.retrieve(id);
        if (role == null) {
            throw new ServiceException(ExceptionConstant.ROLE_NOT_FOUND_EXCEPTION);
        }
        Set<Menu> menus = role.getMenus();
        for(Long menuId : menuIds) {
            Menu menu = new Menu();
            menu.setId(menuId);
            menus.add(menu);
        }
        role.setMenus(menus);
        return roleDao.update(role);
    }

    public Role saveRolePermission(Long id, Long[] permissionIds) {
        Role role = roleDao.retrieve(id);
        if (role == null) {
            throw new ServiceException(ExceptionConstant.ROLE_NOT_FOUND_EXCEPTION);
        }
        Set<Permission> permissions = role.getPermissions();
        for(Long permissionId : permissionIds) {
            Permission permission = new Permission();
            permission.setId(permissionId);
            permissions.add(permission);
        }
        role.setPermissions(permissions);
        return roleDao.update(role);
    }

    public Role findByOrganizationAdmin(Long organization) {
        Role role = roleDao.retrieveRoleAdminByOrganization(organization);
        if (role == null) {
            throw new ServiceException(ExceptionConstant.ROLE_NOT_FOUND_EXCEPTION);
        }
        return role;
    }

    public Role bindRoleWithPermissionAndMenu(Long id,Long variety) {
        Role role = findById(id);
        List<Permission> permissions = permissionService.findPermissionsByVariety(variety);
        List<Menu> menus = menuService.findMenusByVariety(variety);
        role.setMenus(new HashSet(menus));
        role.setPermissions(new HashSet(permissions));
        Role result = roleDao.update(role);
        return result;
    }
}
