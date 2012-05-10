package com.llama3d.object.mesh.geometry;

import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.Vertex;

public class GeometryQuad extends Geometry {
	public GeometryQuad() {
		Surface surface = new Surface();
		Vertex v[] = new Vertex[4];
		// ======== Add Vertices ========
		v[0] = new Vertex();
		v[1] = new Vertex();
		v[2] = new Vertex();
		v[3] = new Vertex();
		// ======== Set Positions ========
		v[0].position(-1f, +1f, 0f);
		v[1].position(+1f, +1f, 0f);
		v[2].position(-1f, -1f, 0f);
		v[3].position(+1f, -1f, 0f);
		// ======== Set Normals ========
		v[0].normal(+0, +0, -1);
		v[1].normal(+0, +0, -1);
		v[2].normal(+0, +0, -1);
		v[3].normal(+0, +0, -1);
		// ======== Set Texturecoordinates ========
		v[0].uvset(0, 0);
		v[1].uvset(1, 0);
		v[2].uvset(0, 1);
		v[3].uvset(1, 1);
		// ======== Add Vertices ========
		surface.vertices.add(v[0]);
		surface.vertices.add(v[1]);
		surface.vertices.add(v[2]);
		surface.vertices.add(v[3]);
		// ======== Add Triangles ========
		surface.addTriangle(surface.vertices.get(2), surface.vertices.get(1), surface.vertices.get(0));
		surface.addTriangle(surface.vertices.get(2), surface.vertices.get(3), surface.vertices.get(1));
		this.surfaces.add(surface);
	}
}
