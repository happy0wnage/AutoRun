package com.DataBase.entity;

public class ProgrammingLanguage extends Entity{

	private String name;
	
	private String Category;
	
	private String typeOfExecution;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getTypeOfExecution() {
		return typeOfExecution;
	}

	public void setTypeOfExecution(String typeOfExecution) {
		this.typeOfExecution = typeOfExecution;
	}

	@Override
	public String toString() {
		return "Developer [name=" + name + ", Category=" + Category
				+ ", typeOfExecution=" + typeOfExecution + "]";
	}
	
}
