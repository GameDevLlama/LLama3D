package com.llama3d.object.mesh.geometry;

import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.Vertex;

public class GeometryCone extends Geometry {
	public GeometryCone() {
		this(16);
	}

	public GeometryCone(int segments) {
		Surface surface = new Surface();
		this.surfaces.add(surface);
		Vertex v[] = new Vertex[6];
		float step = 360.0f / segments;
		for (int i = 0; i < segments; i++) {
			// ======== Add Vertices ========
			v[0] = new Vertex();
			v[1] = new Vertex();
			v[2] = new Vertex();
			v[3] = new Vertex();
			v[4] = new Vertex();
			v[5] = new Vertex();
			// ======== Set Positions ========
			v[0].position(0, +1, 0);
			v[1].position((float) Math.cos(Math.toRadians(step * i)), -1, (float) Math.sin(Math.toRadians(step * i)));
			v[2].position((float) Math.cos(Math.toRadians(step * (i + 1))), -1, (float) Math.sin(Math.toRadians(step * (i + 1))));
			v[3].position((float) Math.cos(Math.toRadians(step * i)), -1, (float) Math.sin(Math.toRadians(step * i)));
			v[4].position((float) Math.cos(Math.toRadians(step * (i + 1))), -1, (float) Math.sin(Math.toRadians(step * (i + 1))));
			v[5].position(0, -1, 0);
			// ======== Set Normals ========
			v[0].normal(2f * (float) Math.cos(Math.toRadians(step * (i + 0.5f))), 1f, 2f * (float) Math.sin(Math.toRadians(step * (i + 0.5f))));
			v[1].normal(2f * (float) Math.cos(Math.toRadians(step * i)), 0f, 2f * (float) Math.sin(Math.toRadians(step * i)));
			v[2].normal(2f * (float) Math.cos(Math.toRadians(step * (i + 1f))), 0f, 2f * (float) Math.sin(Math.toRadians(step * (i + 1f))));
			v[3].normal(0, -1, 0);
			v[4].normal(0, -1, 0);
			v[5].normal(0, -1, 0);
			// ======== Set Texturecoordinates ========
			v[0].uvset((float) (0.5) / (float) segments, 1);
			v[1].uvset((float) (i) / (float) segments, 0.5f);
			v[2].uvset((float) (i + 1) / (float) segments, 0.5f);
			v[3].uvset((float) (i) / (float) segments, 0.5f);
			v[4].uvset((float) (i + 1) / (float) segments, 0.5f);
			v[5].uvset((float) (0.5) / (float) segments, 0);
			// ======== Add Triangles ========
			surface.addTriangle(v[0], v[2], v[1]);
			surface.addTriangle(v[3], v[4], v[5]);
		}
	}
}
