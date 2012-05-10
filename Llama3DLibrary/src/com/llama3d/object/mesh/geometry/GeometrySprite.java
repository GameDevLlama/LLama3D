package com.llama3d.object.mesh.geometry;

import com.llama3d.object.Object3D;

public class GeometrySprite extends Object3D {
	public Geometry geometry;
	public GeometrySprite() {
		this.geometry = new GeometryQuad();
	}
}
