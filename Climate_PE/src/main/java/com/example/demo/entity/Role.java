package com.example.demo.entity;

import java.io.Serializable;
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
@Table(name = "role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private int id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "COMMAND_FLAG")
	private int commandFlag;

	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private List<InternalAccount> inaccounts;

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

	public int getCommandFlag() {
		return commandFlag;
	}

	public List<InternalAccount> getInaccounts() {
		return inaccounts;
	}

	public void setInaccounts(List<InternalAccount> inaccounts) {
		this.inaccounts = inaccounts;
	}

	public void setCommandFlag(int commandFlag) {
		this.commandFlag = commandFlag;
	}

	public Role() {
		// constructor mặc định không có tham số
	}

	public Role(String name) {
		super();
		this.name = name;
		// 0: create, 1: delete
		this.commandFlag = 0;
	}

}
