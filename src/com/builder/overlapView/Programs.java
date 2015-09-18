package com.builder.overlapView;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class Programs {

	public String value;
	public Icon icon;

	public Programs(String value, String path) {
		this.value = value;
		this.icon = FileSystemView.getFileSystemView().getSystemIcon(new File(path));
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getValue() {
		return value;
	}

	public Icon getIcon() {
		return icon;
	}

	public String toString() {
		return value;
	}

	public String rename(String value) {
		return this.value = value;
	}

}
