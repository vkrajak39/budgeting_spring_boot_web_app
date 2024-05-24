package com.exavalu.budgetbakersb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
@Entity
public class RecordType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int recordTypeId;
	
	public int getRecordTypeId() {
		return recordTypeId;
	}

	public void setRecordTypeId(int recordTypeId) {
		this.recordTypeId = recordTypeId;
	}

	public RecordTypeEnum getRecordTypeName() {
		return recordTypeName;
	}

	public void setRecordTypeName(RecordTypeEnum recordTypeName) {
		this.recordTypeName = recordTypeName;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Enumerated(value = EnumType.STRING)
	private RecordTypeEnum recordTypeName;
	
	@OneToMany(mappedBy = "recordType")
    private List<Record> records = new ArrayList<>();
	
	@OneToMany(mappedBy = "recordType")
	private List<Category> categories = new ArrayList<>();
	
}
