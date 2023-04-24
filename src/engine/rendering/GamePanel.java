package engine.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import engine.Game;
import engine.input.InputManager;
import engine.rendering.optimalization.Chunk;
import engine.rendering.optimalization.ChunkManager;
import engine.sprites.Background;
import engine.sprites.entities.player.Player;
import math.MathUtilities;
import math.Vector2;

public class GamePanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = -5792040577297371507L;

	private transient BufferedImage buffer;

	private transient int fps = 0;

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
				//	                System.out.println("FPS: " + fps);
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

		//		g2d.clearRect(0, 0, getWidth(), getHeight());

		// Render graphics here
		Background background = Game.getInstance().getCurrentWorld().getBackground();
		Player player = Game.getInstance().getCurrentWorld().getPlayer();
		ChunkManager manager = Game.getInstance().getCurrentWorld().getChunkManager();

		g2d.drawImage(background.getTexture().getImage(), (int) (background.getLocation().getX()
				- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getX()), (int) (background.getLocation().getY()
						- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getY()), null);
		

		// Zoom scale
		double zoomScale = player.getCamera().getCameraZoomScale();

		// Get the width and height of the panel
		int width = (int) Game.getInstance().getWindow().getSize().getX();

		int height = (int) Game.getInstance().getWindow().getSize().getY();

		// Translate the graphics context to the center of the screen
		g2d.translate(width / 2, height / 2);

		// Scale the graphics context
		g2d.scale(zoomScale, zoomScale);

		// Translate the graphics context back
		g2d.translate(-width / 2, -height / 2);

		// Render active chunks
		int chunkSize = (int) manager.getChunkSize();
		Vector2 loc = player.getCamera().getLocation();
		for (int i = 0; i < manager.getChunkMap().length; i++) {
			for (int j = 0; j < manager.getChunkMap()[i].length; j++) {
				if (manager.getActiveChunks().contains(manager.getChunkMap()[i][j])) {
					g2d.drawImage(manager.getChunkMap()[i][j].getBuffer(), (i * chunkSize
							- (int) loc.getX()), (j * chunkSize - (int) loc.getY()), null);
				}
			}
		}

		// START DEBUG STUFF DRAW

		// END DEBUG STUFF

		// Draw player sprite
		g2d.drawImage(player.getTexture().getImage(), (int) (player.getLocation().getX()
				- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getX()), (int) (player.getLocation().getY()
						- Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().getY()), null);

		// Release resources
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
		
		//Draw inventory
		Game.getInstance().getCurrentWorld().getPlayer().getConstructManager().paint(g2d);

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
		
		if(SwingUtilities.isLeftMouseButton(e)){
			InputManager.setRunLMBPressed(true);
			InputManager.setPressedPosition(location);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			InputManager.setRunRMBPressed(true);
			InputManager.setPressedPosition(location);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector2 location = new Vector2(e.getLocationOnScreen().getX(), e.getLocationOnScreen().getY());
		
		if(SwingUtilities.isLeftMouseButton(e)){
			InputManager.setRunLMBReleased(true);
			InputManager.setReleasedPosition(location);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			InputManager.setRunRMBReleased(true);
			InputManager.setReleasedPosition(location);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		/* Unused */
	}

	@Override
	public void mouseExited(MouseEvent e) {
		/* Unused */
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

}
