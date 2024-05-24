package com.exavalu.budgetbakersb.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class MenuRoleId implements Serializable {

    private Long menuId;
    private Long roleId;

    // Getters and setters
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}