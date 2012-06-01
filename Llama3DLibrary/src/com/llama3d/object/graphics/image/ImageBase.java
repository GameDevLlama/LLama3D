package com.llama3d.object.graphics.image;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.llama3d.main.buffer.FloatBufferFactory;
import com.llama3d.main.graphics.Origin;
import com.llama3d.math.MathEx;
import com.llama3d.math.color.iColorRGB;
import com.llama3d.object.ObjectImage;
import com.llama3d.object.graphics.texture.TextureCache;
import com.llama3d.shader.ShaderCache;

public class ImageBase extends ObjectImage {

	// ===================================================================
	// Fields
	// ===================================================================

	private static ImageVBO imageVBO;
	private static boolean pixelCorrection = true;

	private int buffer = 0;
	private int bufferOffset = 0;

	protected boolean masked = false;
	public ImageTexture imagetexture;

	protected FloatBuffer vertexBuffer;
	protected int vertices;

	protected iColorRGB maskColor = new iColorRGB();// native-color

	protected float offsetX;
	protected float offsetY;
	protected float width;
	protected float height;
	protected int origin;

	protected float u1, v1;
	protected float u2, v2;

	// ===================================================================
	// Constructors
	// ===================================================================

	public ImageBase() {
	}

	// ===================================================================
	// Public Methods
	// ===================================================================

	public void draw() {
		this.draw(0, 0);
	}

	public void draw(int posX, int posY) {
		// ======== Passing Vertex And UV Attributes ========
		if (ImageCache.lastUsedImageBuffer != buffer) {
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffer);
			GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeVertex, 2, GLES20.GL_FLOAT, false, 16, 0);
			GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeUV, 2, GLES20.GL_FLOAT, false, 16, 8);
			ImageCache.lastUsedImageBuffer = buffer;
		}
		// ======== Image Or ImagePart ========
		this.render(+posX + (int) Origin.positionX, -posY + (int) Origin.positionY, this.bufferOffset * 6);
	}

	/**
	 * Draws given image stretched to the bounds.
	 * 
	 * @param startX
	 *            - Startposition in pixels.
	 * @param startY
	 *            - Startposition in pixels.
	 * @param width
	 *            - Rectwidth in pixels.
	 * @param height
	 *            - Rectheight in pixels.
	 */
	public void drawRect(int startX, int startY, int width, int height) {
		// ======== Resetting Bufferposition ========
		ImageBaseCache.resetBuffer();
		// ======== Put Vertex In Buffer ========
		ImageBaseCache.putVertex(startX, -startY, this.u1, this.v1);
		ImageBaseCache.putVertex(startX, -startY - height, this.u1, this.v2);
		ImageBaseCache.putVertex(startX + width, -startY, this.u2, this.v1);
		ImageBaseCache.putVertex(startX + width, -startY, this.u2, this.v1);
		ImageBaseCache.putVertex(startX, -startY - height, this.u1, this.v2);
		ImageBaseCache.putVertex(startX + width, -startY - height, this.u2, this.v2);
		// ======== Resetting Bufferposition ========
		ImageBaseCache.resetBuffer();
		// ======== Passing Vertex And UV Attributes ========
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeVertex, 2, GLES20.GL_FLOAT, false, 8, ImageBaseCache.vertices);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeUV, 2, GLES20.GL_FLOAT, false, 8, ImageBaseCache.uvs);
		ImageCache.lastUsedImageBuffer = -1;
		// ======== Render ImageRect ========
		this.render(Origin.positionX, Origin.positionY, 0);
	}

	public void drawQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		// ======== Resetting Bufferposition ========
		ImageBaseCache.resetBuffer();
		// ======== Put Vertex In Buffer ========
		ImageBaseCache.putVertex(x1, -y1, this.u1, this.v1);
		ImageBaseCache.putVertex(x3, -y3, this.u1, this.v2);
		ImageBaseCache.putVertex(x2, -y2, this.u2, this.v1);
		ImageBaseCache.putVertex(x2, -y2, this.u2, this.v1);
		ImageBaseCache.putVertex(x3, -y3, this.u1, this.v2);
		ImageBaseCache.putVertex(x4, -y4, this.u2, this.v2);
		// ======== Resetting Bufferposition ========
		ImageBaseCache.resetBuffer();
		// ======== Passing Vertex And UV Attributes ========
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeVertex, 2, GLES20.GL_FLOAT, false, 8, ImageBaseCache.vertices);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeUV, 2, GLES20.GL_FLOAT, false, 8, ImageBaseCache.uvs);
		ImageCache.lastUsedImageBuffer = -1;
		// ======== Render ImageQuad ========
		this.render(Origin.positionX, Origin.positionY, 0);
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		// ======== LineWidth = ImageHeight ========
		this.drawLine(x1, y1, x2, y2, this.height);
	}

	public void drawLine(int x1, int y1, int x2, int y2, float lineWidth) {
		// ======== If There's A Line ========
		if (x1 != x2 || y1 != y2) {
			// ======== Resetting Bufferposition ========
			ImageBaseCache.resetBuffer();
			// ======== Calculate LineData ========
			float length = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
			float crossVectorX = ((float) (y1 - y2) / length);
			float crossVectorY = ((float) (x2 - x1) / length);
			lineWidth *= 0.5f;
			// ======== Put Vertex In Buffer ========
			ImageBaseCache.putVertex(x1 + crossVectorX * lineWidth, -y1 + crossVectorY * lineWidth, this.u1, this.v1);
			ImageBaseCache.putVertex(x1 - crossVectorX * lineWidth, -y1 - crossVectorY * lineWidth, this.u1, this.v2);
			ImageBaseCache.putVertex(x2 + crossVectorX * lineWidth, -y2 + crossVectorY * lineWidth, this.u2, this.v1);
			ImageBaseCache.putVertex(x2 + crossVectorX * lineWidth, -y2 + crossVectorY * lineWidth, this.u2, this.v1);
			ImageBaseCache.putVertex(x1 - crossVectorX * lineWidth, -y1 - crossVectorY * lineWidth, this.u1, this.v2);
			ImageBaseCache.putVertex(x2 - crossVectorX * lineWidth, -y2 - crossVectorY * lineWidth, this.u2, this.v2);
			// ======== Resetting Bufferposition ========
			ImageBaseCache.resetBuffer();
			// ======== Passing Vertex And UV Attributes ========
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
			GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeVertex, 2, GLES20.GL_FLOAT, false, 8, ImageBaseCache.vertices);
			GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeUV, 2, GLES20.GL_FLOAT, false, 8, ImageBaseCache.uvs);
			ImageCache.lastUsedImageBuffer = -1;
			// ======== Render ImageLine ========
			this.render(Origin.positionX, Origin.positionY, 0);
		}
	}

	public void mask(int r, int g, int b) {
		this.maskColor.r = MathEx.clamp(r, 0, 255);
		this.maskColor.g = MathEx.clamp(g, 0, 255);
		this.maskColor.b = MathEx.clamp(b, 0, 255);
		this.masked = true;
	}

	public void mask(boolean masked) {
		this.masked = masked;
	}

	public void free() {
		int[] units = new int[1];
		int[] buffer = new int[] { this.buffer };
		GLES20.glDeleteTextures(1, units, 0);
		GLES20.glDeleteBuffers(1, buffer, 0);
	}

	public void render(float posX, float posY, int offset) {
		// ======== Pass Uniforms To Shader ========
		this.passUniforms();
		// ======== Passing Image Position ========
		GLES20.glUniform2f(ShaderCache.activeShader.position, posX, posY);
		// ======== Draw Arrays With Image Vertices ========
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, offset, 6);

	}

	private void passUniforms() {
		// ======== Pass Masking Color ========
		if (this.masked) {
			GLES20.glUniform1i(ShaderCache.activeShader.masked, 1);
			GLES20.glUniform3i(ShaderCache.activeShader.maskColor, this.maskColor.r, this.maskColor.g, this.maskColor.b);
		} else {
			GLES20.glUniform1i(ShaderCache.activeShader.masked, 0);
		}
		// ======== Set Sampler Texture2D ========
		if (TextureCache.lastTextureUnit != this.imagetexture.unit) {
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, this.imagetexture.unit);
			GLES20.glUniform1i(ShaderCache.activeShader.texture, 0);
			TextureCache.lastTextureUnit = this.imagetexture.unit;
		}
	}

	public static void pixelCorrection(boolean correctionEnable) {
		// ======== Images Or Grabs Are Corrected For Images ========
		ImageBase.pixelCorrection = correctionEnable;
	}

	// ===================================================================
	// Protected Methods
	// ===================================================================

	public void setVBOBuffer() {
		this.vertexBuffer.position(0);

		imageVBO = ImageVBOFactory.getFreeVBOSpace();
		this.buffer = imageVBO.vBuffer;
		this.bufferOffset = imageVBO.reserveSpace();

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.buffer);
		GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, this.bufferOffset * 96, 96, this.vertexBuffer);
		this.vertices = 6;
	}

	protected void setQuad(int origin) {
		this.setQuad(0, 0, this.imagetexture.imageWidth, this.imagetexture.imageHeight, origin);
	}

	protected void setQuad(float x, float y, float width, float height, int origin) {
		// ======== Create Vertexbuffer ========
		this.vertexBuffer = FloatBufferFactory.create(24);
		// ======== Vertexpositions ========
		float quadHalfWidth = (float) width / 2;
		float quadHalfHeight = (float) height / 2;
		float quadHalfOffsetX = 0;
		float quadHalfOffsetY = 0;
		float pixelCorrectionX = 0f;
		float pixelCorrectionY = 0f;
		// ======== Origin ========
		switch (origin) {
		case Origin.upperLeft:
			quadHalfOffsetX = quadHalfWidth;
			quadHalfOffsetY = quadHalfHeight;
			break;
		case Origin.center:
			break;
		}
		// ======== Pixelcorrection ========
		if (ImageBase.pixelCorrection == true) {
			if (width % 2 == 1) {
				pixelCorrectionX = .5f;
			}
			if (height % 2 == 1) {
				pixelCorrectionY = .5f;
			}
		}
		// ======== UV-Coordinates ========
		this.u1 = +x / (float) imagetexture.textureSize;
		this.v1 = +y / (float) imagetexture.textureSize;
		this.u2 = +(x + width) / (float) imagetexture.textureSize;
		this.v2 = +(y + height) / (float) imagetexture.textureSize;
		// ======== Put Vertex In Buffer ========
		this.putVertex(-quadHalfWidth + quadHalfOffsetX - pixelCorrectionX, +quadHalfHeight - quadHalfOffsetY + pixelCorrectionY, this.u1, this.v1);
		this.putVertex(-quadHalfWidth + quadHalfOffsetX - pixelCorrectionX, -quadHalfHeight - quadHalfOffsetY + pixelCorrectionY, this.u1, this.v2);
		this.putVertex(+quadHalfWidth + quadHalfOffsetX - pixelCorrectionX, +quadHalfHeight - quadHalfOffsetY + pixelCorrectionY, this.u2, this.v1);
		this.putVertex(+quadHalfWidth + quadHalfOffsetX - pixelCorrectionX, +quadHalfHeight - quadHalfOffsetY + pixelCorrectionY, this.u2, this.v1);
		this.putVertex(-quadHalfWidth + quadHalfOffsetX - pixelCorrectionX, -quadHalfHeight - quadHalfOffsetY + pixelCorrectionY, this.u1, this.v2);
		this.putVertex(+quadHalfWidth + quadHalfOffsetX - pixelCorrectionX, -quadHalfHeight - quadHalfOffsetY + pixelCorrectionY, this.u2, this.v2);
		// ======== Buffer System ========
		// 1---3 /4
		// |--/ /-|
		// |-/ /--|
		// 2/ 5---6
	}

	private void putVertex(float x, float y, float u, float v) {
		this.vertexBuffer.put(x);
		this.vertexBuffer.put(y);
		this.vertexBuffer.put(u);
		this.vertexBuffer.put(v);
	}
}
