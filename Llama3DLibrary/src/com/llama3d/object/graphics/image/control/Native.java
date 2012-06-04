package com.llama3d.object.graphics.image.control;

import android.opengl.GLES20;

import com.llama3d.main.display.DisplayCache;
import com.llama3d.main.graphics.blend.Blend;
import com.llama3d.opengl.OpenGL;
import com.llama3d.shader.ShaderCache;

public class Native {

	// ===================================================================
	// Private Static Native Methods
	// ===================================================================

	private static volatile float[] renderColor = new float[] { 1, 1, 1, 1 };
	private static volatile int[] renderColorInt = new int[] { 255, 255, 255 };
	private static volatile float[] renderScale = new float[] { 1, 1 };
	private static volatile float renderRotation = 0;

	// ===================================================================
	// Public Static Native Methods
	// ===================================================================

	public static void setDefault() {
		setAlpha(1);
		setRotation(0);
		setScale(1, 1);
		setBlend(Blend.ALPHA);
		setColor(255, 255, 255);
	}

	/**
	 * sets the alpha value for drawn natives.
	 * 
	 * @param alpha
	 */
	public static void setAlpha(float alpha) {
		if (alpha != renderColor[3]) {
			renderColor[3] = alpha;
			GLES20.glUniform1f(ShaderCache.activeShader.matAlpha, renderColor[3]);
		}
	}

	/**
	 * sets the blending for drawn natives.
	 * 
	 * @param blendMode
	 *            sets blending for drawn natives.
	 * @see Blend
	 */
	public static void setBlend(int blendMode) {
		Blend.setBlend(blendMode);
	}

	/**
	 * sets the color for drawn natives.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void setColor(int red, int green, int blue) {
		if (red != renderColorInt[0] || green != renderColorInt[1] || blue != renderColorInt[2]) {
			renderColorInt[0] = red;
			renderColorInt[1] = green;
			renderColorInt[2] = blue;
			renderColor[0] = (float) red / 255f;
			renderColor[1] = (float) green / 255f;
			renderColor[2] = (float) blue / 255f;
			GLES20.glUniform3f(ShaderCache.activeShader.matDiffuse, renderColor[0], renderColor[1], renderColor[2]);
		}
	}

	/**
	 * sets the rotation (in degrees) for drawn natives.
	 * 
	 * @param angle
	 */
	public static void setRotation(float angle) {
		if (angle != renderRotation) {
			GLES20.glUniform1f(ShaderCache.activeShader.angle, (float) Math.toRadians(angle));
			renderRotation = angle;
		}
	}

	/**
	 * sets the scale for drawn natives.
	 * 
	 * @param scaleX
	 * @param scaleY
	 */
	public static void setScale(float scaleX, float scaleY) {
		if (scaleX != Native.renderScale[0] && scaleY != Native.renderScale[1]) {
			Native.renderScale[0] = scaleX;
			Native.renderScale[1] = scaleY;
			GLES20.glUniform2f(ShaderCache.activeShader.scale, Native.renderScale[0], Native.renderScale[1]);
		}
	}

	/**
	 * clears the whole screen with a specified color.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void clearScreen(int red, int green, int blue) {
		if (OpenGL.autoDither) {
			OpenGL.pushDither();
			GLES20.glDisable(GLES20.GL_DITHER);
		}

		GLES20.glViewport(0, 0, DisplayCache.w, DisplayCache.h);
		GLES20.glClearColor((float) red / 255f, (float) green / 255f, (float) blue / 255f, 1);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		if (OpenGL.autoDither) {
			OpenGL.popDither();
		}
	}

	public static synchronized void passUniforms() {
		GLES20.glUniform2f(ShaderCache.activeShader.scale, Native.renderScale[0], Native.renderScale[1]);
		GLES20.glUniform1f(ShaderCache.activeShader.angle, Native.renderRotation);
		GLES20.glUniform3f(ShaderCache.activeShader.matDiffuse, renderColor[0], renderColor[1], renderColor[2]);
		GLES20.glUniform1f(ShaderCache.activeShader.matAlpha, renderColor[3]);
	}

}
