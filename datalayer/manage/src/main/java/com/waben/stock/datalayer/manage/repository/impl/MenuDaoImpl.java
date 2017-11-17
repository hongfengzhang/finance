package com.waben.stock.datalayer.manage.repository.impl;

import com.waben.stock.datalayer.manage.entity.Menu;
import com.waben.stock.datalayer.manage.repository.MenuDao;
import com.waben.stock.datalayer.manage.repository.impl.jpa.MenuRepository;
import com.waben.stock.datalayer.manage.repository.impl.jpa.RoleRepository;
import com.waben.stock.interfaces.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/17.
 * @desc
 */
@Repository
public class MenuDaoImpl implements MenuDao {

    @Autowired
    private MenuRepository repository;

    @Override
    public List<Menu> retrieveByRole(Long role) {
        return repository.findAllByRolesOrderById(role);
    }

    @Override
    public List<Menu> retrieveByStaff(Long staff) {
        return repository.findAllByStaff(staff);
    }

    @Override
    public Menu create(Menu menu) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Menu update(Menu menu) {
        return null;
    }

    @Override
    public Menu retrieve(Long id) {
        return null;
    }

    @Override
    public Page<Menu> page(int page, int limit) {
        return null;
    }

    @Override
    public List<Menu> list() {
        return null;
    }
}
