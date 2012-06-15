package com.llama3d.object.graphics.image;

import android.opengl.GLES20;
import android.util.Log;

public class ImageVBO {

	// ===================================================================
	// Private Static Final Fields
	// ===================================================================

	private static final int images = 1000;

	// ===================================================================
	// Private Fields
	// ===================================================================

	public int vBuffer;
	protected boolean[] reservedSpace = new boolean[images];

	// ===================================================================
	// Constructors
	// ===================================================================

	protected ImageVBO() {
		// ======== Create A New VBO ========
		int[] vBuffer = new int[1];
		GLES20.glGenBuffers(1, vBuffer, 0);
		this.vBuffer = vBuffer[0];
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.vBuffer);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, 96 * images, null, GLES20.GL_DYNAMIC_DRAW);
		Log.i("ImageVBO", "generated buffer. [unit:" + this.vBuffer + "]");
	}

	// ===================================================================
	// Private Methods
	// ===================================================================

	// ===================================================================
	// Protected Methods
	// ===================================================================

	protected boolean hasFreeSpace() {
		// ======== Check For Free VBO Space ========
		for (int i = 0; i < images; i++) {
			if (this.reservedSpace[i] == false) {
				// Log.i("ImageVBO", "free space. [" + i + "]");
				return true;
			}
		}
		return false;
	}

	// ===================================================================
	// Public Methods
	// ===================================================================

	public int reserveSpace() {
		// ======== Check For Free VBO Space ========
		for (int i = 0; i < images; i++) {
			if (this.reservedSpace[i] == false) {
				// ======== Set Space Reserved ========
				this.reservedSpace[i] = true;
				// Log.i("ImageVBO", "reserve. [" + i + "]");
				return i;
			}
		}
		return 0;
	}

}
