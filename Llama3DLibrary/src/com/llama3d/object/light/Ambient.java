package com.llama3d.object.light;

import android.opengl.GLES20;

import com.llama3d.shader.ShaderCache;

public class Ambient {
	private static float[] lightColor = new float[3];
	private static boolean changed = true;

	public static int getR() {
		return (int) (Ambient.lightColor[0] * 255.0);
	}

	public static int getG() {
		return (int) (Ambient.lightColor[1] * 255.0);
	}

	public static int getB() {
		return (int) (Ambient.lightColor[2] * 255.0);
	}

	public static void setColor(int r, int g, int b) {
		lightColor[0] = r / 255f;
		lightColor[1] = g / 255f;
		lightColor[2] = b / 255f;
		Ambient.changed = true;
	}

	public static void reset() {
		Ambient.changed = true;
	}

	public static void passUniform() {
		if (changed) {
			GLES20.glUniform4f(ShaderCache.activeShader.ambientLight, lightColor[0], lightColor[1], lightColor[2], 0f);
			Ambient.changed = false;
		}
	}
}
