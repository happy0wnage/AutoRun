package com.DataBase.entity;

public class Type extends Entity{

	private String typeName;
	
	private String speciality;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	@Override
	public String toString() {
		return "Type [typeName=" + typeName + ", speciality=" + speciality
				+ "]";
	}
	
}
