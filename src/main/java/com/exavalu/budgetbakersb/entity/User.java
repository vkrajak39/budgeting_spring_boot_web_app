package com.exavalu.budgetbakersb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String verificationCode;
	
	// Set default value for userRoleId using @Column, and mark it as insertable = false, updatable = false
    @Column(name = "userRoleId", columnDefinition = "bigint default 2")
    private final Long userRoleId = 2L;

    @ManyToOne
    @JoinColumn(name = "userRoleId", referencedColumnName = "roleId",insertable = false, updatable = false)
    private Role role;	
    private final int status = 1;
    private boolean loggedIn;
    private String imagePath;
    
    public int getStatus() {
		return status;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public Long getUserRoleId() {
		return userRoleId;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Account> accounts = new ArrayList<>(); 
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	private List<Record> records = new ArrayList<>();
	
}