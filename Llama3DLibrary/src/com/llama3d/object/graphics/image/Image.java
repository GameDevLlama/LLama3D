package com.llama3d.object.graphics.image;

import com.llama3d.main.graphics.Origin;
import com.llama3d.scene.SceneCache;

public class Image extends ImageBase {

	// ===================================================================
	// Public Static Final Fields
	// ===================================================================

	public static final int STRETCH = 0x00000000;
	public static final int REPEAT = 0x00000001;

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	// ===================================================================
	// Protected Static Fields
	// ===================================================================

	protected static boolean antiAliasing = true;

	// ===================================================================
	// Private Fields
	// ===================================================================

	// ===================================================================
	// Constructors
	// ===================================================================

	public static Image load(String imagePath) {
		return Image.load(imagePath, Origin.center, false);
	}

	public static Image load(String imagePath, boolean libraryIntern) {
		return Image.load(imagePath, Origin.center, libraryIntern);
	}

	public static Image load(String imagePath, int origin, boolean libraryIntern) {
		Image image = new Image();
		image.imagetexture = ImageTexture.load(imagePath, libraryIntern);
		image.offsetX = 0;
		image.offsetY = 0;
		image.width = image.imagetexture.imageWidth;
		image.height = image.imagetexture.imageHeight;
		image.setQuad(origin);
		image.setVBOBuffer();
		return image;
	}

	private Image() {
		SceneCache.activeScene.images.add(this);
	}

	private Image(Image sourceImage, float x, float y, float width, float height, int origin) {
		this.imagetexture = sourceImage.imagetexture;
		this.masked = sourceImage.masked;
		this.maskColor = sourceImage.maskColor;

		this.offsetX = x;
		this.offsetY = y;
		this.width = width;
		this.height = height;
		this.origin = origin;

		this.setQuad(this.offsetX, this.offsetY, this.width, this.height, this.origin);
		this.setVBOBuffer();
		SceneCache.activeScene.images.add(this);
	}

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static Image grab(Image sourceImage, float x, float y, float width, float height) {
		return new Image(sourceImage, x, y, width, height, Origin.center);
	}

	public static Image grab(Image sourceImage, float x, float y, float width, float height, int origin) {
		return new Image(sourceImage, x, y, width, height, origin);
	}

	public static void setAntiAliasing(boolean enable) {
		antiAliasing = enable;
	}

}
