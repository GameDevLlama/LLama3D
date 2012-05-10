package com.llama3d.object.mesh;

import com.llama3d.math.vector.Vector2;
import com.llama3d.math.vector.Vector3;

public class Vertex {

	// ===================================================================
	// Fields
	// ===================================================================

	public float[] data = new float[11];

	// ===================================================================
	// Constructors
	// ===================================================================

	public Vertex() {

	}

	public Vertex(Vector3 position, Vector3 normal, Vector2 uvset) {
		this.data[0] = position.x;
		this.data[1] = position.y;
		this.data[2] = position.z;
		this.data[3] = normal.x;
		this.data[4] = normal.y;
		this.data[5] = normal.z;
		// this.data[6] = r;
		// this.data[7] = g;
		// this.data[8] = b;
		this.data[9] = uvset.x;
		this.data[10] = uvset.y;
	}

	public Vertex(float x, float y, float z) {
		this.data[0] = x;
		this.data[1] = y;
		this.data[2] = z;
	}

	public Vertex(float x, float y, float z, float nx, float ny, float nz, float r, float g, float b, float u, float v) {
		this.data[0] = x;
		this.data[1] = y;
		this.data[2] = z;
		this.data[3] = nx;
		this.data[4] = ny;
		this.data[5] = nz;
		this.data[6] = r;
		this.data[7] = g;
		this.data[8] = b;
		this.data[9] = u;
		this.data[10] = v;
	}

	public void position(float x, float y, float z) {
		this.data[0] = x;
		this.data[1] = y;
		this.data[2] = z;
	}

	public void normal(float nx, float ny, float nz) {
		this.data[3] = nx;
		this.data[4] = ny;
		this.data[5] = nz;
	}

	public void uvset(float u, float v) {
		this.data[9] = u;
		this.data[10] = v;
	}
}
