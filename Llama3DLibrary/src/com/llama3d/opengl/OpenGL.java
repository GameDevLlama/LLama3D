package com.llama3d.opengl;

import android.opengl.GLES20;

import com.llama3d.main.display.DisplayCache;
import com.llama3d.main.graphics.blend.Blend;
import com.llama3d.object.graphics.image.ImageCache;
import com.llama3d.object.graphics.image.control.Native;
import com.llama3d.object.graphics.texture.TextureCache;
import com.llama3d.object.material.MaterialCache;
import com.llama3d.shader.ShaderCache;

public class OpenGL {

	// ===================================================================
	// Public Static Final Fields
	// ===================================================================

	public static final int renderModels = 0x00001101;
	public static final int renderImages = 0x00001102;
	public static final int renderInterface = 0x00001103;

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	private static int temporaryRenderMode = 0;
	private static int currentRenderMode = 0;

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void renderMode(int renderMode) {
		switch (renderMode) {
		case 0:
			resetAll();
			break;
		case OpenGL.renderModels:
			// ======== Set Rendermode For Models =======
			if (OpenGL.currentRenderMode != OpenGL.renderModels && ShaderCache.modelShader != null) {
				// ======== Use Shader For Models =======
				ShaderCache.modelShader.use();
				Blend.setBlend(Blend.SOLID);
				GLES20.glEnable(GLES20.GL_DEPTH_TEST);
				GLES20.glDepthFunc(GLES20.GL_LESS);
				// ======== Use VertexAttributes =======
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeVertex);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeNormal);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeColor);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeUV);
				OpenGL.currentRenderMode = OpenGL.renderModels;
				reset();
			}
			break;
		case OpenGL.renderImages:
			// ======== Set Rendermode For Images =======
			if (OpenGL.currentRenderMode != OpenGL.renderImages && ShaderCache.imageShader != null) {
				// ======== Use Shader For Images =======
				ShaderCache.imageShader.use();
				Blend.setBlend(Blend.ALPHA);
				GLES20.glDisable(GLES20.GL_DEPTH_TEST);
				GLES20.glDepthFunc(GLES20.GL_ALWAYS);
				// ======== Use VertexAttributes =======
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeVertex);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeNormal);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeColor);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeUV);

				Native.passUniforms();
				GLES20.glUniformMatrix4fv(ShaderCache.activeShader.projectionMatrix, 1, false, ImageCache.imageMatrix, 0);

				OpenGL.currentRenderMode = OpenGL.renderImages;
				reset();
			}
			break;
		case OpenGL.renderInterface:
			// ======== Set Rendermode For Interfaces =======
			if (OpenGL.currentRenderMode != OpenGL.renderInterface && ShaderCache.guiShader != null) {
				// ======== Use Shader For Interfaces =======
				ShaderCache.guiShader.use();
				Blend.setBlend(Blend.SOLID);
				GLES20.glDisable(GLES20.GL_DEPTH_TEST);
				GLES20.glDepthFunc(GLES20.GL_LESS);
				GLES20.glClearDepthf(1.0f);
				GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
				// ======== Use VertexAttributes =======
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeVertex);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeNormal);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeColor);
				GLES20.glEnableVertexAttribArray(ShaderCache.activeShader.attributeUV);

				OpenGL.currentRenderMode = OpenGL.renderInterface;
				reset();
			}
			break;
		}
	}

	public static void popRenderMode() {
		OpenGL.renderMode(OpenGL.temporaryRenderMode);
	}

	public static void pushRenderMode() {
		OpenGL.temporaryRenderMode = OpenGL.currentRenderMode;
	}

	public static void setViewPort() {
		//GLES20.glDisable(GLES20.GL_DITHER);
		GLES20.glViewport(0, 0, DisplayCache.w, DisplayCache.h);
		// Log.i("OpenGL", "set viewport. [" + DisplayCache.w + "x" +
		// DisplayCache.h + "]");
	}

	public static void clearViewPort() {
		GLES20.glClearColor(0, 0, 0, 0);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
	}

	public static void openGLGetGraphicsCardInfo() {
		int[] uniformParameters = new int[10];
		GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_UNIFORM_VECTORS, uniformParameters, 0);
		GLES20.glGetIntegerv(GLES20.GL_MAX_FRAGMENT_UNIFORM_VECTORS, uniformParameters, 1);
		GLES20.glGetIntegerv(GLES20.GL_MAX_VARYING_VECTORS, uniformParameters, 2);
		GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, uniformParameters, 3);
		GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_ATTRIBS, uniformParameters, 4);
		GLES20.glGetIntegerv(GLES20.GL_MAX_VIEWPORT_DIMS, uniformParameters, 5);
		GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS, uniformParameters, 6);
		GLES20.glGetIntegerv(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, uniformParameters, 7);

		for (int i = 0; i < 8; i++) {
			// LogCache.out(this, BaseActivity.GL_VALUES[i] + ": " +
			// uniformParameters[i], 1);
		}
	}

	// ===================================================================
	// Private Static Methods
	// ===================================================================

	private static void reset() {
		MaterialCache.currentMaterial = null;
		ImageCache.lastUsedImageBuffer = 0;
		TextureCache.lastTextureUnit = 0;
	}

	private static void resetAll() {
		OpenGL.temporaryRenderMode = 0;
		OpenGL.currentRenderMode = 0;
		OpenGL.reset();
	}
}
