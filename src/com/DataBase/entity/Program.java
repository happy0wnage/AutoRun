package com.DataBase.entity;

import java.io.File;
import java.sql.Date;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class Program {

	private int idProgrammingLanguage;
	
	private int idDeveloper;
	
	private int idType;
	
	private String name;
	
	private Date release;
	
	private String webSite;
	
	private String description;
	
	private String path;
	
	private Icon icon;
	
	public Icon getIcon() {
		return FileSystemView.getFileSystemView().getSystemIcon(new File(path));
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public int getIdProgrammingLanguage() {
		return idProgrammingLanguage;
	}

	public void setIdProgrammingLanguage(int idProgrammingLanguage) {
		this.idProgrammingLanguage = idProgrammingLanguage;
	}

	public int getIdDeveloper() {
		return idDeveloper;
	}

	public void setIdDeveloper(int idDeveloper) {
		this.idDeveloper = idDeveloper;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRelease() {
		return release;
	}

	public void setRelease(Date release) {
		this.release = release;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Program [idProgrammingLanguage=" + idProgrammingLanguage
				+ ", idDeveloper=" + idDeveloper + ", idType=" + idType
				+ ", name=" + name + ", release=" + release + ", webSite="
				+ webSite + ", description=" + description + ", path=" + path
				+ ", icon=" + icon + "]";
	}

}
