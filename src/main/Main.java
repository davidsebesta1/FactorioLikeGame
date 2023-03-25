package main;

import java.io.File;

import engine.Sprite;
import engine.rendering.GameWindow;
import math.Vector2;

public class Main {

	public static void main(String[] args) {
		GameWindow window = GameWindow.initiateInstance(new Vector2(800, 800));

		Sprite sprite = Sprite.instantiateSprite(new File("ar.jpg"), new Vector2(500, 500), 0);

	}

}
