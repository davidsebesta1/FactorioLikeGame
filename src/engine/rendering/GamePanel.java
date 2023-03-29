package engine.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import engine.Game;
import engine.input.InputManager;
import engine.sprites.Sprite;
import math.Vector2;

public class GamePanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = -5792040577297371507L;

	public GamePanel(Vector2 size) {
		this.setSize((int) size.getX(), (int) size.getY());
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// G2D and rendering hits
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Zoom scale
		double zoomScale = Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale();

		// Get the width and height of the panel
		int width = getWidth();
		int height = getHeight();

		// Translate the graphics context to the center of the screen
		g2d.translate(width / 2, height / 2);

		// Scale the graphics context
		g2d.scale(zoomScale, zoomScale);

		// Translate the graphics context back
		g2d.translate(-width / 2, -height / 2);

		// Rendering
		for (Sprite sprite : SpriteManager.getSprites()) {
			if (sprite.getImage() != null && sprite.isVisible()) {
				g2d.drawImage(sprite.getImage(),
						(int) (sprite.getLocation().getX()
								- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getX()),
						(int) (sprite.getLocation().getY()
								- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getY()),
						null);
			}
		}

		// Cleaning up stuff
		g2d.dispose();
		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/* Unused */ }

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("pressed");
		Vector2 location = new Vector2(e.getLocationOnScreen().getX(), e.getLocationOnScreen().getY());
		InputManager.fireMousePressed(location);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector2 location = new Vector2(
				e.getLocationOnScreen().getX() + Game.getInstance().getCurrentWorld().getPlayer().getLocation().getX(),
				e.getLocationOnScreen().getY() + Game.getInstance().getCurrentWorld().getPlayer().getLocation().getY());
		InputManager.fireMouseReleased(location);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		/* Unused */
	}

	@Override
	public void mouseExited(MouseEvent e) {
		/* Unused */
	}
}
