package com.waben.stock.datalayer.manage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.waben.stock.interfaces.dto.RoleDto;
import net.sf.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author Created by yuyidi on 2017/11/13.
 * @desc
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column
    private String code;
    @Column(length = 30)
    private String describtion;

    @JsonIgnore
    @ManyToMany(targetEntity = Staff.class, mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<Staff> staffs = new HashSet<>();

    @JsonIgnore
    @ManyToMany(targetEntity = Menu.class,fetch = FetchType.LAZY)
    @JoinTable(name = "role_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn
            (name = "menu_id")})
    private Set<Menu> menus = new HashSet<>();

    @JsonIgnore
    @ManyToMany(targetEntity = Permission.class,fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns =
            {@JoinColumn(name =
                    "permission_id")})
    private Set<Permission> permissions = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public Set<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Staff> staffs) {
        this.staffs = staffs;
    }

    public Set<Menu> getMenus() {
        return  menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RoleDto copy() {
        RoleDto roleDto = new RoleDto();
        BeanCopier beanCopier = BeanCopier.create(Role.class, RoleDto.class, false);
        beanCopier.copy(this,roleDto, null);
        return roleDto;
    }

    private static SortedSet<Menu> sortMenus(Collection<? extends Menu> menus) {
        SortedSet<Menu> sortedAuthorities = new TreeSet(new Role.MenuSortComparator());
        Iterator var2 = menus.iterator();

        while(var2.hasNext()) {
            Menu grantedAuthority = (Menu) var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class MenuSortComparator implements Comparator<Menu>, Serializable {
        private MenuSortComparator() {
        }
        public int compare(Menu g1, Menu g2) {
            if (g2.getId() == null) {
                return -1;
            } else {
                return g1.getId() == null ? 1 : g1.getId().compareTo(g2.getId());
            }
        }
    }
}
