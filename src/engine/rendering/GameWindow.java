package engine.rendering;

import javax.swing.JFrame;

import math.Vector2;

public class GameWindow {
	private static GameWindow instance;
	
	private JFrame frame;
	private GamePanel panel;
	
	private Vector2 size;
	
	private boolean isFullscreen;
	
	private GameWindow(Vector2 size, boolean isFullscreen) {
		this.size = size;
		this.isFullscreen = isFullscreen;
		
		this.frame = new JFrame();
		this.frame.setSize((int) size.getX(),(int) size.getY());
		if(isFullscreen) {
			this.frame.setUndecorated(true);
			this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		this.frame.setResizable(false);
		this.panel = new GamePanel(size);
		
		this.frame.setVisible(true);
		this.frame.add(panel);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void repaint() {
		frame.repaint();
	}
	
	public static GameWindow getInstance() {
		if(instance != null) return instance;
		return null;
	}
	
	public static GameWindow initiateInstance(Vector2 size, boolean isFullscreen) {
		if(instance == null) instance = new GameWindow(size, isFullscreen);
		return instance;
	}
	


}
