package com.example.demo.DTO;

public class SymptomDTO {

	private String id;
	private String name;
	private String description;
	private String specid;
	private String commandFlag;

	public SymptomDTO() {
		// TODO Auto-generated constructor stub
	}

	public SymptomDTO(String name, String description, String specid) {
		super();
		this.name = name;
		this.description = description;
		this.specid = specid;
	}

	public SymptomDTO(String id, String name, String description, String specid) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.specid = specid;
	}

	public SymptomDTO(String id, String name, String description, String specid, String commandFlag) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.specid = specid;
		this.commandFlag = commandFlag;
	}

	public String getCommandFlag() {
		return commandFlag;
	}

	public void setCommandFlag(String commandFlag) {
		this.commandFlag = commandFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getSpecid() {
		return specid;
	}

	public void setSpecid(String specid) {
		this.specid = specid;
	}

}
