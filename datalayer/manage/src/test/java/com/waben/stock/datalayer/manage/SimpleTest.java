package com.waben.stock.datalayer.manage;

import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.entity.Staff;
import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public class SimpleTest {

    @Test
    public void testBeanCopy() {
        Staff staff = new Staff();
        staff.setUserName("yuyidi");
        staff.setPassword("1234566");
        staff.setCreateTime(new Date());
        Role role = new Role();
        role.setCode("admin");
        role.setName("管理员");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        staff.setRoles(roles);
//        StaffDto staffDto = staff.copy();
//        System.out.println(JacksonUtil.encode(staffDto));
    }
}
