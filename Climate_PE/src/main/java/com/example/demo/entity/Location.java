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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_ID")
	private int id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@JsonIgnore
	@OneToMany(mappedBy = "workingPlace")
	private List<InternalAccount> account;

	public Location() {
		// TODO Auto-generated constructor stub
	}

	public Location(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		// 0: create, 1: block
		this.commandFlag = 0;
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

	public List<InternalAccount> getAccount() {
		return account;
	}

	public void setAccount(List<InternalAccount> account) {
		this.account = account;
	}

}
