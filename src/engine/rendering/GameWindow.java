package engine.rendering;

import javax.swing.JFrame;

import math.Vector2;

public class GameWindow implements Runnable{
	private static GameWindow instance;
	
	private JFrame frame;
	private GamePanel panel;
	
	private Vector2 size;
	
	private boolean isRunning;
	
	private GameWindow(Vector2 size) {
		this.size = size;
		
		this.frame = new JFrame();
		this.frame.setSize((int) size.getX(),(int) size.getY());
		this.panel = new GamePanel(size);
		
		this.frame.setVisible(true);
		this.frame.add(panel);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		isRunning = true;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public static GameWindow getInstance() {
		if(instance != null) return instance;
		return null;
	}
	
	public static GameWindow initiateInstance(Vector2 size) {
		if(instance == null) instance = new GameWindow(size);
		return instance;
	}
	

	@Override
	public void run() {
		while(isRunning) {
			gameLoop();
		}
	}
	
	private void gameLoop() 
	{
		panel.repaint();
	}
}
