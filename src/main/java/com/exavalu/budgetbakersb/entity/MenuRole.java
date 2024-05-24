package com.exavalu.budgetbakersb.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MenuRole {

	@EmbeddedId
	private MenuRoleId id;

	@ManyToOne
	@JoinColumn(name = "menuId", insertable = false, updatable = false)
	private Menu menu;

	@ManyToOne
	@JoinColumn(name = "roleId", insertable = false, updatable = false)
	private Role role;

	public MenuRoleId getId() {
		return id;
	}

	public void setId(MenuRoleId id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
