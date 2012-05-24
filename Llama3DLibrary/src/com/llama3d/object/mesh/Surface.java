package com.llama3d.object.mesh;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;
import android.util.Log;

import com.llama3d.main.buffer.FloatBufferFactory;
import com.llama3d.object.material.Material;
import com.llama3d.object.material.MaterialCache;
import com.llama3d.shader.ShaderCache;

public class Surface {

	// ===================================================================
	// Fields
	// ===================================================================

	public Material material;

	private FloatBuffer vertexBuffer;
	public List<Vertex> vertices = new ArrayList<Vertex>();
	private List<Triangle> triangles = new ArrayList<Triangle>();

	private int[] buffer = new int[1];

	// ===================================================================
	// Constructors
	// ===================================================================

	public Surface() {
		this.material = MaterialCache.defaultMaterial;
	}

	// ===================================================================
	// Methods
	// ===================================================================

	/**
	 * Adds triangle to given surface.
	 * 
	 * @param triangle
	 */
	public void addTriangle(Triangle triangle) {
		this.triangles.add(triangle);
	}

	/**
	 * Adds triangle to given surface. ( Remember CC or CCW addition for visible
	 * or invisible tris.)
	 * 
	 * @param vertex1
	 *            - first vertex.
	 * @param vertex2
	 *            - second vertex.
	 * @param vertex3
	 *            - third vertex.
	 */
	public void addTriangle(Vertex vertex1, Vertex vertex2, Vertex vertex3) {
		this.triangles.add(new Triangle(vertex1, vertex2, vertex3));
	}

	/**
	 * Adds quad to given surface. ( Remember CC or CCW addition for visible or
	 * invisible quads.)
	 * 
	 * @param vertex1
	 *            - first vertex.
	 * @param vertex2
	 *            - second vertex.
	 * @param vertex3
	 *            - third vertex.
	 * @param vertex4
	 *            - fourth vertex.
	 */
	public void addQuad(Vertex vertex1, Vertex vertex2, Vertex vertex3, Vertex vertex4) {
		this.triangles.add(new Triangle(vertex1, vertex2, vertex4));
		this.triangles.add(new Triangle(vertex2, vertex3, vertex4));
	}

	/**
	 * Adds material to given surface.
	 * 
	 * @param material
	 *            - see also {@link Material}
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	public void buffer() {
		long timeStamp = System.nanoTime();
		this.vertexBuffer = FloatBufferFactory.create(this.triangles.size() * 33);
		for (Triangle triangle : this.triangles) {
			for (int v = 0; v < 3; v++) {
				// ======== Put Data ========
				for (int d = 0; d < 11; d++) {
					this.vertexBuffer.put((float) triangle.vertex[v].data[d]);
				}
			}
		}
		timeStamp = System.nanoTime() - timeStamp;
		Log.i("Surface", "surface buffered in " + (timeStamp / 1000000l) + " ms.");
	}

	public void setBuffer() {
		this.vertexBuffer.position(0);
		GLES20.glGenBuffers(1, this.buffer, 0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.buffer[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, this.vertexBuffer.capacity() * 4, this.vertexBuffer, GLES20.GL_DYNAMIC_DRAW);
		Log.i("Surface", "setbuffer. [unit:" + this.buffer[0] + "][" + this.vertexBuffer.capacity() * 4 + "bytes]");
	}

	public void render() {
		this.material.passUniforms();
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.buffer[0]);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeVertex, 3, GLES20.GL_FLOAT, false, 44, 0);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeNormal, 3, GLES20.GL_FLOAT, false, 44, 12);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeColor, 3, GLES20.GL_FLOAT, false, 44, 24);
		GLES20.glVertexAttribPointer(ShaderCache.activeShader.attributeUV, 2, GLES20.GL_FLOAT, false, 44, 36);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, this.triangles.size() * 3);
	}

}
