package com.llama3d.object.light;

import android.opengl.GLES20;

import com.llama3d.shader.ShaderCache;

public class Ambient {
	public static float[] lightColor = new float[3];

	public static void setColor(int r, int g, int b) {
		lightColor[0] = r / 255f;
		lightColor[1] = g / 255f;
		lightColor[2] = b / 255f;
	}

	public static void passUniform() {
		GLES20.glUniform4f(ShaderCache.activeShader.ambientLight, lightColor[0], lightColor[1], lightColor[2], 0f);
	}
}
