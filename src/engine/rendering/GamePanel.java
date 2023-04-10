package engine.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Game;
import engine.input.InputManager;
import engine.rendering.optimalization.ChunkManager;
import engine.sprites.Background;
import engine.sprites.entities.Player;
import math.Vector2;

public class GamePanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = -5792040577297371507L;

	private BufferedImage buffer;
	
	private int fps = 0;

	public GamePanel(Vector2 size) {
		this.setSize((int) size.getX(), (int) size.getY());
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addMouseListener(this);

		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		buffer = gfxConfig.createCompatibleImage((int) size.getX(), (int) size.getY(), Transparency.OPAQUE);
		
		 Timer fpsTimer = new Timer(1000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.out.println("FPS: " + fps);
	                Game.getInstance().setFramesPerSecond(fps);
	                fps = 0;
	            }
	        });
	        fpsTimer.start();
	}

	private void render() {
		// Render to off-screen buffer
		Graphics g = buffer.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		g2d.clearRect(0, 0, getWidth(), getHeight());

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

		// Render graphics here

		Background background = Game.getInstance().getCurrentWorld().getBackground();
		Player player = Game.getInstance().getCurrentWorld().getPlayer();

		g2d.drawImage(background.getTexture().getImage(),
				(int) (background.getLocation().getX()
						- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getX()),
				(int) (background.getLocation().getY()
						- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getY()),
				null);

		ChunkManager manager = Game.getInstance().getCurrentWorld().getChunkManager();

		for (int i = 0; i < manager.getChunkMap().length; i++) {
			for (int j = 0; j < manager.getChunkMap()[i].length; j++) {
				if (manager.getActiveChunks().contains(manager.getChunkMap()[i][j])) {
					g2d.drawImage(manager.getChunkMap()[i][j].getBuffer(),
							(i * 256 - (int) Game.getInstance().getCurrentWorld().getPlayer().getCamera()
									.getLocation().getX()),
							(j * 256 - (int) Game.getInstance().getCurrentWorld().getPlayer().getCamera()
									.getLocation().getY()),
							null);
				}
			}
		}

		g2d.drawImage(player.getTexture().getImage(),
				(int) (player.getLocation().getX()
						- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getX()),
				(int) (player.getLocation().getY()
						- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getY()),
				null);

		g2d.dispose();
		g.dispose();
	
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// G2D and rendering hits
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		render();

		g2d.drawImage(buffer, 0, 0, null);
		g2d.setColor(Color.red);
		g2d.drawString("FPS: " + Game.getInstance().getFramesPerSecond(), 10, 15);

//		for (Chunk chunk : Game.getInstance().getCurrentWorld().getChunkManager().getActiveChunks()) {
//			for (Sprite sprite : chunk.getSprites()) {
//				if (sprite.getTexture().getImage() != null && sprite.isVisible()) {
//					if (sprite.getTexture().isOpaque()) {
//						g2d.drawImage(sprite.getTexture().getImage(),
//								(int) Math.floor(sprite.getLocation().getX() - Game.getInstance().getCurrentWorld()
//										.getPlayer().getCamera().getLocation().getX()),
//								(int) Math.floor(sprite.getLocation().getY() - Game.getInstance().getCurrentWorld()
//										.getPlayer().getCamera().getLocation().getY()),
//								null);
//					} else {
//
//						// Set the transparency level
//						float alpha = sprite.getTexture().getAlpha();
//						AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
//						g2d.setComposite(alphaComposite);
//
//						// Render your image
//						g2d.drawImage(sprite.getTexture().getImage(),
//								(int) (sprite.getLocation().getX() - Game.getInstance().getCurrentWorld().getPlayer()
//										.getCamera().getLocation().getX()),
//								(int) (sprite.getLocation().getY() - Game.getInstance().getCurrentWorld().getPlayer()
//										.getCamera().getLocation().getY()),
//								null);
//
//						g2d.setComposite(AlphaComposite.SrcOver);
//					}
//				}
//			}

//		 Debug render collision box
//			for(PhysicsSprite sprite2 : PhysicsManager.getPhysicsSprites()) {
//				if(sprite2.getCollisionBox() != null) {
//					BoundingBox box = sprite2.getCollisionBox();
//					g2d.setColor(Color.red);
//					Vector2 location = MathUtilities.worldToScreenCoordinates(new Vector2((int) box.getX(),(int) box.getY()));
//					g2d.drawRect((int) location.getX(),(int) location.getY(),(int) box.getSize().getWidth(),(int) box.getSize().getHeight());
//				}
//			}

		// Cleaning up stuff
		g2d.dispose();
		g.dispose();
		Toolkit.getDefaultToolkit().sync();
		
		fps++;

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
