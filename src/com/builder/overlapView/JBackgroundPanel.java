package com.builder.overlapView;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class JBackgroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage img;

	public JBackgroundPanel(BufferedImage img) {
		this.img = img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}
