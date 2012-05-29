package com.llama3d.object.graphics.image;

import android.opengl.Matrix;
import android.util.Log;

import com.llama3d.main.display.Screen;

public class ImageCache {

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static float[] imageMatrix = new float[16];
	protected static int lastUsedImageBuffer = -1;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		int w = Screen.width;
		int h = Screen.height;
		Matrix.orthoM(ImageCache.imageMatrix, 0, -w / 2, +w / 2, -h / 2, +h / 2, 0f, 2f);
		Log.i("ImageCache", "set up imagematrix. [" + w + "x" + h + "]");
	}

	public static void reset() {
		// ======== Reset Temporary RenderStep Data ========
		ImageCache.lastUsedImageBuffer = -1;
	}
}
