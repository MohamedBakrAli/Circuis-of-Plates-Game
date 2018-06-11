package view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class Background extends JPanel {
	Image circus = null;
	Dimension y;

	public Background(Image x) {
		// TODO Auto-generated constructor stub
		this.circus = x;
	}

	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(circus, 0, 0, 1360, 750, null);
	}

}
