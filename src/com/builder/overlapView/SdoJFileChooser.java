package com.builder.overlapView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sun.swing.FilePane;

public class SdoJFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;

	public void updateUI() {
		LookAndFeel old = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable ex) {
			old = null;
		}

		super.updateUI();

		if (old != null) {
			FilePane filePane = findFilePane(this);
			filePane.setViewType(FilePane.VIEWTYPE_DETAILS);
			filePane.setViewType(FilePane.VIEWTYPE_LIST);

			Color background = UIManager.getColor("Label.background");
			setBackground(background);
			setOpaque(true);

			try {
				UIManager.setLookAndFeel(old);
			} catch (UnsupportedLookAndFeelException ignored) {
			}
		}
	}

	private static FilePane findFilePane(Container parent) {
		for (Component comp : parent.getComponents()) {
			if (FilePane.class.isInstance(comp)) {
				return (FilePane) comp;
			}
			if (comp instanceof Container) {
				Container cont = (Container) comp;
				if (cont.getComponentCount() > 0) {
					FilePane found = findFilePane(cont);
					if (found != null) {
						return found;
					}
				}
			}
		}

		return null;
	}
}