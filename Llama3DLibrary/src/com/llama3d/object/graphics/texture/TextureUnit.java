package com.llama3d.object.graphics.texture;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.llama3d.main.assets.AssetFactory;

public class TextureUnit {

	public String path;
	public boolean antiAlias;
	private boolean libraryIntern;
	private boolean forcedLoading;

	public int unit;
	public int imageWidth;
	public int imageHeight;
	public int textureSize;

	protected TextureUnit(String imagePath, boolean antiAlias, boolean libraryIntern) {
		this.path = imagePath;
		this.antiAlias = antiAlias;
		this.libraryIntern = libraryIntern;
		this.load(forcedLoading);
	}

	public void reload() {
		Log.i("TextureUnit", "reloading texture. [" + this.path + "]");
		this.load(false);
	}

	private void load(boolean forcedLoading) {
		// ======== Generate GL Textureunits ========
		this.genGLTextures();
		// ======== Open FileStream ========
		InputStream fileStream = AssetFactory.loadAssetAsStream(this.path, this.libraryIntern);
		Bitmap rawBitmap = BitmapFactory.decodeStream(fileStream);
		// ======== Get Image Attributes ========
		this.imageWidth = rawBitmap.getWidth();
		this.imageHeight = rawBitmap.getHeight();
		this.textureSize = getPow2(this.imageWidth, this.imageHeight);

		Bitmap textureBitmap = null;
		if (this.textureSize == this.imageWidth && this.textureSize == this.imageHeight) {
			textureBitmap = rawBitmap;
		} else {
			int pixels[] = new int[this.imageWidth * this.imageHeight];
			rawBitmap.getPixels(pixels, 0, this.imageWidth, 0, 0, this.imageWidth, this.imageHeight);
			textureBitmap = Bitmap.createBitmap(this.textureSize, this.textureSize, Bitmap.Config.ARGB_8888);
			textureBitmap.eraseColor(0);
			textureBitmap.setPixels(pixels, 0, this.imageWidth, 0, 0, this.imageWidth, this.imageHeight);
		}
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, textureBitmap, 0);

		Log.i("TextureUnit", "texture loaded. [unit:" + this.unit + "]");

		textureBitmap.recycle();
	}

	private void genGLTextures() {
		int[] textureID = new int[1];
		GLES20.glGenTextures(1, textureID, 0);
		this.unit = textureID[0];
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, this.unit);

		if (this.antiAlias) {
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		} else {
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		}

		// GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
		// GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_TEXTURE_WRAP_S);
		// GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
		// GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_TEXTURE_WRAP_T);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_BINDING_2D, GLES20.GL_ACTIVE_TEXTURE);
	}

	private int getPow2(int width, int height) {
		int i;
		for (i = 1; i < width || i < height; i *= 2) {
		}
		return i;
	}
}
