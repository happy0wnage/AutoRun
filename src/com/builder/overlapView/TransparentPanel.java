package com.builder.overlapView;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

class TransparentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	{
		setOpaque(false);
	}

	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		Rectangle r = g.getClipBounds();
		g.fillRect(r.x, r.y, r.width, r.height);
		super.paintComponent(g);
	}
}