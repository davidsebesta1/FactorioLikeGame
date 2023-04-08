package engine.rendering.textures;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TextureLibrary {
	
	private HashMap<String, Texture> library;
	
	private static TextureLibrary instance;

	public TextureLibrary() {
		library = new HashMap<>();
		instance = this;
	}
	
	public boolean addTexture(String textureName, Texture texture) {
		if(library.containsKey(textureName)) return false;
		
		library.put(textureName, texture);
		return true;
	}
	
	public boolean addTextureWithVariation(String textureName , Texture texture) {
		if(library.containsKey(textureName)) library.remove(textureName);
		
		library.put(textureName + "0", texture);
		library.put(textureName + "1", Texture.rotateImage(texture, Math.PI / 2));
		library.put(textureName + "2", Texture.rotateImage(texture, Math.PI));
		library.put(textureName + "3", Texture.rotateImage(texture, (Math.PI * 3) / 2));
		return true;
		
	}
	

	public static TextureLibrary getInstance() {
		return instance;
	}
	
	public Texture retrieveTexture(String textureName) {
		if(library.containsKey(textureName)) return library.get(textureName);
		return null;
	}
	
	public HashMap<String, Texture> getLibrary() {
		return library;
	}

	public void loadAllTextures(String folderPath) {
		File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {
            	if(file.getName().substring(0, file.getName().length() - 4).charAt(file.getName().length() - 5) == '!') {
            		addTextureWithVariation(file.getName().substring(0, file.getName().length() - 5), Texture.createTexture(file));
            	} else {
            		addTexture(file.getName().substring(0, file.getName().length() - 4), Texture.createTexture(file));
            	}
            }
        }
	}

}
