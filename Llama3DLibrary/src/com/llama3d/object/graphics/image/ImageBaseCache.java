package com.llama3d.object.graphics.image;

import java.nio.FloatBuffer;

import com.llama3d.main.buffer.FloatBufferFactory;

public class ImageBaseCache {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	private static final int triangles = 2;

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	// ===================================================================
	// Protected Static Fields
	// ===================================================================

	protected static FloatBuffer vertices = FloatBufferFactory.create(triangles * 6);
	protected static FloatBuffer uvs = FloatBufferFactory.create(triangles * 6);

	// ===================================================================
	// Public Static Methods
	// ===================================================================

	public static void init() {
		// ImageBaseCache.setBuffer();
	}

	// ===================================================================
	// Protected Static Methods
	// ===================================================================

	protected static void putVertex(float x, float y, float u, float v) {
		ImageBaseCache.vertices.put(x);
		ImageBaseCache.vertices.put(y);
		ImageBaseCache.uvs.put(u);
		ImageBaseCache.uvs.put(v);
	}

	protected static void resetBuffer() {
		ImageBaseCache.vertices.position(0);
		ImageBaseCache.uvs.position(0);
	}

}
