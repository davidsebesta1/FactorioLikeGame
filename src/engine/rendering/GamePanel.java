package engine.rendering;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import engine.Game;
import engine.sprites.Sprite;
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

		for (Sprite sprite : SpriteManager.getSprites()) {
			if (sprite.getImage() != null && sprite.isVisible()) {
				g.drawImage(sprite.getImage(),
						(int) (sprite.getLocation().getX()
								- Game.getInstance().getCurrentWorld().getPlayer().getLocation().getX()),
						(int) (sprite.getLocation().getY()
								- Game.getInstance().getCurrentWorld().getPlayer().getLocation().getY()),
						null);
			}
		}
	}
}
