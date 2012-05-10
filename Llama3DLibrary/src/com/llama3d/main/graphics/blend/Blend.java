package com.llama3d.main.graphics.blend;

import android.opengl.GLES20;

/**
 * 
 * @author Chrise
 * @version 1.0
 * 
 */
public class Blend {

	// ===================================================================
	// Final Static Fields
	// ===================================================================

	public final static int SOLID = 0x00000101;
	public final static int ALPHA = 0x00000102;
	public final static int LIGHT = 0x00000103;
	public final static int DARK = 0x00000104;
	public final static int INVERT = 0x00000105;
	public final static int ALPHA_MASK = 0x00000106;
	public final static int INVERT_MASK = 0x00000107;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void setBlend(int blendMode) {
		switch (blendMode) {
		// ======== Normal Material Blending ========
		case Blend.SOLID:
			GLES20.glDisable(GLES20.GL_BLEND);
			GLES20.glDisable(GLES20.GL_ALPHA);
			break;
		// ======== Normal Material Blending With Alpha Channel ========
		case Blend.ALPHA:
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glEnable(GLES20.GL_ALPHA);
			GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			break;
		// ======== Additional Material Blending ========
		case Blend.LIGHT:
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glEnable(GLES20.GL_ALPHA);
			GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);
			break;
		// ======== Subtractional Material Blending ========
		case Blend.DARK:
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glEnable(GLES20.GL_ALPHA);
			GLES20.glBlendFunc(GLES20.GL_DST_COLOR, GLES20.GL_SRC_COLOR);
			break;
		// ======== Inversion Material Blending ========
		case Blend.INVERT:
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glEnable(GLES20.GL_ALPHA);
			GLES20.glBlendFunc(GLES20.GL_ONE_MINUS_DST_COLOR, GLES20.GL_ONE_MINUS_SRC_COLOR);
			// ======== Alphamask Material Blending ========
		case Blend.ALPHA_MASK:
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glEnable(GLES20.GL_ALPHA);
			GLES20.glBlendFunc(GLES20.GL_DST_COLOR, GLES20.GL_ZERO);
			// ======== Inverted Alphamask Material Blending ========
		case Blend.INVERT_MASK:
			GLES20.glEnable(GLES20.GL_BLEND);
			GLES20.glEnable(GLES20.GL_ALPHA);
			GLES20.glBlendFunc(GLES20.GL_ONE_MINUS_DST_COLOR, GLES20.GL_ZERO);

		}
	}

}
