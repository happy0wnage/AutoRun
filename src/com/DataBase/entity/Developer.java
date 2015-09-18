package com.DataBase.entity;

public class Developer extends Entity{
	
	private String companyName;
	
	private int fonundationYear;
	
	private String country;
	
	private int numberOfEmployess;
	
	private String inpustry;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getFonundationYear() {
		return fonundationYear;
	}

	public void setFonundationYear(int fonundationYear) {
		this.fonundationYear = fonundationYear;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getNumberOfEmployess() {
		return numberOfEmployess;
	}

	public void setNumberOfEmployess(int numberOfEmployess) {
		this.numberOfEmployess = numberOfEmployess;
	}

	public String getInpustry() {
		return inpustry;
	}

	public void setInpustry(String inpustry) {
		this.inpustry = inpustry;
	}

	@Override
	public String toString() {
		return "Developer [companyName=" + companyName + ", fonundationYear="
				+ fonundationYear + ", country=" + country
				+ ", numberOfEmployess=" + numberOfEmployess + ", inpustry="
				+ inpustry + "]";
	}
	
	
}
