package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "specialty")
public class Specialty {

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SPEC_ID")
	private int id;

	@Column(name = "SPEC_NAME")
	private String name;

	@Column(name = "SPEC_DESCRIPTION")
	private String description;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@JsonIgnore
	@OneToMany(mappedBy = "specialty")
	private List<InternalAccount> internal;

	@JsonIgnore
	@OneToMany(mappedBy = "specialty")
	List<Symptom> symptoms;

//////////////////////////////

	public Specialty() {
		// TODO Auto-generated constructor stub
	}

	public Specialty(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		// 0: create, 1: block
		this.commandFlag = 0;
	}

	public List<InternalAccount> getInternal() {
		return internal;
	}

	public void setInternal(List<InternalAccount> internal) {
		this.internal = internal;
	}

	public int getId() {
		return id;
	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCommandFlag() {
		return commandFlag;
	}

	public void setCommandFlag(int commandFlag) {
		this.commandFlag = commandFlag;
	}

}
