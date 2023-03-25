package engine.rendering;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import engine.Sprite;
import math.Vector2;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -5792040577297371507L;

	public GamePanel(Vector2 size) {
		this.setSize((int) size.getX(), (int) size.getY());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// G2D and rendering hits
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		for(Sprite sprite : SpriteManager.getSprites()) {
			g.drawImage(sprite.getImage(),(int) sprite.getLocation().getX(),(int) sprite.getLocation().getY(), null);
		}
	}
}
