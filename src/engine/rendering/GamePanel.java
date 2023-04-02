package engine.rendering;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import engine.Game;
import engine.input.InputManager;
import engine.physics.BoundingBox;
import engine.physics.PhysicsManager;
import engine.sprites.PhysicsSprite;
import engine.sprites.Sprite;
import engine.sprites.SpriteManager;
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

		// Draw FPS
		g2d.drawString("FPS: " + Game.getInstance().getFramesPerSecond(), 10, 15);

		// Zoom scale
		double zoomScale = Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale();

		// Get the width and height of the panel
		int width = (int) Game.getInstance().getWindow().getSize().getX();

		int height = (int) Game.getInstance().getWindow().getSize().getY();

		// Translate the graphics context to the center of the screen
		g2d.translate(width / 2, height / 2);

		// Scale the graphics context
		g2d.scale(zoomScale, zoomScale);

		// Translate the graphics context back
		g2d.translate(-width / 2, -height / 2);

		// Rendering
		for (Sprite sprite : SpriteManager.getSprites()) {
			if (sprite.getTexture().getImage() != null && sprite.isVisible()) {
				if (sprite.getTexture().isOpaque()) {
					g2d.drawImage(sprite.getTexture().getImage(),
							(int) (sprite.getLocation().getX() - Game.getInstance().getCurrentWorld().getPlayer()
									.getCamera().getLocation().getX()),
							(int) (sprite.getLocation().getY() - Game.getInstance().getCurrentWorld().getPlayer()
									.getCamera().getLocation().getY()),
							null);
				} else {

					// Set the transparency level
					float alpha = sprite.getTexture().getAlpha();
					AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
					g2d.setComposite(alphaComposite);

					// Render your image
					g2d.drawImage(sprite.getTexture().getImage(),
							(int) (sprite.getLocation().getX() - Game.getInstance().getCurrentWorld().getPlayer()
									.getCamera().getLocation().getX()),
							(int) (sprite.getLocation().getY() - Game.getInstance().getCurrentWorld().getPlayer()
									.getCamera().getLocation().getY()),
							null);

					g2d.setComposite(AlphaComposite.SrcOver);
				}
			}
			
			//Debug render collision box
			for(PhysicsSprite sprite2 : PhysicsManager.getPhysicsSprites()) {
				if(sprite2.getCollisionBox() != null) {
					BoundingBox box = sprite2.getCollisionBox();
					g2d.setColor(Color.red);
					Vector2 location = Camera.worldToScreenCoordinates(new Vector2((int) box.getX(),(int) box.getY()));
					g2d.drawRect((int) location.getX(),(int) location.getY(),(int) box.getSize().getWidth(),(int) box.getSize().getHeight());
				}
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
		Vector2 location = new Vector2(e.getLocationOnScreen().getX(), e.getLocationOnScreen().getY());
		InputManager.fireMousePressed(location);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector2 location = new Vector2(e.getLocationOnScreen().getX(), e.getLocationOnScreen().getY());
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
