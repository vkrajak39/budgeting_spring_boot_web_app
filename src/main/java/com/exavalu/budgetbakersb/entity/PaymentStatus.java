package com.exavalu.budgetbakersb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PaymentStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@Enumerated(value = EnumType.STRING)
	private PaymentStatusEnum name;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PaymentStatusEnum getName() {
		return name;
	}

	public void setName(PaymentStatusEnum name) {
		this.name = name;
	}

	@OneToMany(mappedBy="paymentStatus")
	private List<Record> records = new ArrayList<>();
}
