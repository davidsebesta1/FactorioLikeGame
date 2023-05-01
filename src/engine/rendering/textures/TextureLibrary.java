package engine.rendering.textures;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import engine.sprites.entities.player.UI.StructureButton;
import engine.sprites.entities.player.UI.StructureTypeButton;
import main.Log;

public class TextureLibrary {
	
	private static HashMap<String, Texture> library;
	
	private static TextureLibrary instance;

	public TextureLibrary() {
		library = new HashMap<>();
		instance = this;
	}
	
	public static boolean addTexture(String textureName, Texture texture) {
		if(library.containsKey(textureName)) return false;
		
		library.put(textureName, texture);
		
		Log.info("Loaded texture " + textureName);
		return true;
	}
	
	public static boolean addTextureWithVariation(String textureName , Texture texture) {
		if(library.containsKey(textureName)) library.remove(textureName);
		
		library.put(textureName + "0", texture);
		library.put(textureName + "1", Texture.rotateImage(texture, Math.PI / 2));
		library.put(textureName + "2", Texture.rotateImage(texture, Math.PI));
		library.put(textureName + "3", Texture.rotateImage(texture, (Math.PI * 3) / 2));
		
		Log.info("Loaded variation texture " + textureName);
		return true;
		
	}
	

	public static TextureLibrary getInstance() {
		return instance;
	}
	
	public static Texture retrieveTexture(String textureName) {
		for (Map.Entry<String, Texture> entry : library.entrySet()) {
			if(entry.getKey().equals(textureName)) return entry.getValue();
		}
		
		return library.get("unknownTexture");
	}
	
	public static HashMap<String, Texture> getLibrary() {
		return library;
	}

	public void loadAllTextures(String folderPath, TextureType type) {
		File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {
            	if(file.getName().substring(0, file.getName().length() - 4).charAt(file.getName().length() - 5) == '!') {
            		addTextureWithVariation(file.getName().substring(0, file.getName().length() - 5), Texture.createTexture(file, type));
            	} else {
            		addTexture(file.getName().substring(0, file.getName().length() - 4), Texture.createTexture(file, type));
            	}
            }
        }
	}

}
