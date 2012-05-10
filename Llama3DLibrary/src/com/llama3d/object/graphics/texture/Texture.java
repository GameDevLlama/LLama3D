package com.llama3d.object.graphics.texture;

import com.llama3d.scene.SceneCache;

public class Texture extends TextureUnit {

	// ===================================================================
	// Constructors
	// ===================================================================

	private Texture(String texturePath, boolean libraryIntern) {
		super(texturePath, true, libraryIntern);
		if (SceneCache.activeScene != null) {
			SceneCache.activeScene.textures.add(this);
		}
	}

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static Texture load(String texturePath) {
		return new Texture(texturePath, false);
	}

	public static Texture load(String texturePath, boolean libraryIntern) {
		return new Texture(texturePath, libraryIntern);
	}

}
