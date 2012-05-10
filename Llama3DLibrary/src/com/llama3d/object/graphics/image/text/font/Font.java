package com.llama3d.object.graphics.image.text.font;

import com.llama3d.object.graphics.image.ImageTexture;
import com.llama3d.scene.SceneCache;

public class Font {

	public FontSettings settings;
	public ImageTexture imagetexture;

	public Font(String imagefontPath, String settingsPath) {
		this.settings = new FontSettings(settingsPath);
		this.imagetexture = ImageTexture.load(imagefontPath, false);
	}

	public void set() {
		SceneCache.activeScene.font = this;
	}
}
