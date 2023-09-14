package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "symptom")
public class Symptom {

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SYMPTOM_ID")
	private int id;

	@Column(name = "SYMPTOM_NAME")
	private String name;

	@Column(name = "SYMPTOM_DESCRIPTION")
	private String description;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@ManyToOne
	@JoinColumn(name = "SPEC_ID")
	private Specialty specialty;

	public Symptom() {
		// TODO Auto-generated constructor stub
	}

	public Symptom(String name, String description, Specialty specialty) {
		super();
		this.name = name;
		this.description = description;

		// 0: create, 1: block
		this.commandFlag = 0;
		this.specialty = specialty;
	}

	public int getId() {
		return id;
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

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

}
