package engine.rendering;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
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
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import engine.Game;
import engine.input.InputManager;
import engine.rendering.optimalization.Chunk;
import engine.rendering.optimalization.ChunkManager;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.Background;
import engine.sprites.entities.player.Player;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import math.Vector2;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -5792040577297371507L;

	private transient BufferedImage buffer;

	private Canvas canvas;

	private transient int fps = 0;

	private transient BufferStrategy bs;
	private transient Graphics2D g;

	private BufferedImage ghostImage;
	private Vector2 ghostImageLocation;

	public GamePanel(Vector2 size) {
		this.setSize((int) size.getX(), (int) size.getY());
		this.setBackground(Color.black);
		this.setFocusable(true);

		this.canvas = new Canvas();
		this.canvas.setSize(new Dimension((int) size.getX(), (int) size.getY()));
		this.canvas.setLocation(0, 0);
		this.canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				/* Unused */ }

			@Override
			public void mousePressed(MouseEvent e) {
				Vector2 location = new Vector2(e.getLocationOnScreen().getX(), e.getLocationOnScreen().getY());

				if (SwingUtilities.isLeftMouseButton(e)) {
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

				if (SwingUtilities.isLeftMouseButton(e)) {
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

		});
		this.add(canvas);

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

	public void init() {
		this.canvas.createBufferStrategy(2);
		this.bs = canvas.getBufferStrategy();
		this.g = (Graphics2D) bs.getDrawGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	private void render() {
		// Render to off-screen buffer
		Graphics graphics = buffer.getGraphics();
		Graphics2D g2d = (Graphics2D) graphics;

		// Render graphics here
		Background background = Game.getInstance().getCurrentWorld().getBackground();
		Player player = Game.getInstance().getCurrentWorld().getPlayer();
		ChunkManager manager = Game.getInstance().getCurrentWorld().getChunkManager();

		g2d.drawImage(background.getTexture().getImage(), (int) (background.getLocation().getX()
				- player.getCamera().getLocation().getX()), (int) (background.getLocation().getY()
						- player.getCamera().getLocation().getY()), null);

		// Zoom scale
		double zoomScale = player.getCamera().getCameraZoomScale();

		// Get the width and height of the panel
		int width = (int) Game.getInstance().getWindow().getSize().getX();
		int height = (int) Game.getInstance().getWindow().getSize().getY();

		// Translate the graphics context to the center of the screen
		g2d.translate(width * 0.5, height * 0.5);

		// Scale the graphics context
		g2d.scale(zoomScale, zoomScale);

		// Translate the graphics context back
		g2d.translate(-width * 0.5, -height * 0.5);

		// Render active chunks
		int chunkSize = (int) manager.getChunkSize();
		Vector2 loc = player.getCamera().getLocation();
		int numChunksX = manager.getChunkMap().length;
		int numChunksY = manager.getChunkMap()[0].length;
		for (int i = 0; i < numChunksX; i++) {
			for (int j = 0; j < numChunksY; j++) {
				Chunk chunk = manager.getChunkMap()[i][j];
				if (manager.getActiveChunks().contains(chunk)) {
					int x = (int) (i * chunkSize - loc.getX());
					int y = (int) (j * chunkSize - loc.getY());
					g2d.drawImage(chunk.getBuffer(), x, y, null);
				}
			}
		}

		// START DEBUG STUFF DRAW
		//		for(PhysicsSprite sprite : PhysicsManager.getPhysicsSprites()) {
		//			
		//			BoundingBox rect = sprite.getCollisionBox();
		//			
		//			g2d.setColor(Color.red);
		//			if(rect != null) g2d.drawRect((int) rect.getX(),(int) rect.getY(),(int) rect.getWidth(),(int) rect.getHeight());
		//		}

		// END DEBUG STUFF

		// Draw player sprite
		g2d.drawImage(player.getTexture().getImage(), (int) (player.getLocation().getX()
				- player.getCamera().getLocation().getX()), (int) (player.getLocation().getY()
						- player.getCamera().getLocation().getY()), null);

		//Draw selected struct ghost
		if (ghostImage != null) {
			g2d.drawImage(ghostImage, (int) (ghostImageLocation.getX()), (int) (ghostImageLocation.getY() - 4), null);
		}

		// Release resources
		g2d.dispose();
		graphics.dispose();
	}

	public void updateImage() {
		render();
		Player player = Game.getInstance().getPlayer();
		if(player.canShowBuildMenu()) {
			player.getConstructManager().paint((Graphics2D) buffer.getGraphics());
		}
		
		if(player.canShowMainMenu()) {
			player.getMenuManager().draw((Graphics2D) buffer.getGraphics());
		}
		
		

		g.drawImage(buffer, 0, 0, canvas.getWidth(), canvas.getHeight(), null);

		g.setColor(Color.red);
		g.drawString("FPS: " + Game.getInstance().getFramesPerSecond(), 10, 15);
		bs.show();

		Toolkit.getDefaultToolkit().sync();

		fps++;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setGhostBuffer(BufferedImage ghostImage, Vector2 location) {
		this.ghostImage = ghostImage;
		this.ghostImageLocation = location;
	}

	public void setGhostBuffer(BufferedImage ghostImage, Vector2 location, ConveyorBeltDirection dir) {
		this.ghostImage = ghostImage;
		this.ghostImageLocation = location;
		Graphics2D bufferGraphics = (Graphics2D) ghostImage.getGraphics();

		switch (dir) {
		case DOWN:
			bufferGraphics.drawImage(TextureLibrary.retrieveTexture("arrowIcon1").getImage(), 0, 0, null);
			break;
		case LEFT:
			bufferGraphics.drawImage(TextureLibrary.retrieveTexture("arrowIcon2").getImage(), 0, 0, null);
			break;
		case RIGHT:
			bufferGraphics.drawImage(TextureLibrary.retrieveTexture("arrowIcon0").getImage(), 0, 0, null);
			break;
		case UP:
			bufferGraphics.drawImage(TextureLibrary.retrieveTexture("arrowIcon3").getImage(), 0, 0, null);
			break;
		}
		
		bufferGraphics.dispose();
	}
}
