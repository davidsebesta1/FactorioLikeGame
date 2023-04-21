package main;

import engine.Game;

public class Main {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true"); // turn off if game crashes on startup
		System.setProperty("sun.java2d.ddoffscreen", "true");
		
		Game.initializeInstance();
	}

}
