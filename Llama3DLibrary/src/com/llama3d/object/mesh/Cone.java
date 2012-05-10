package com.llama3d.object.mesh;

import com.llama3d.object.mesh.geometry.GeometryCone;

public class Cone extends Mesh {

	// ===================================================================
	// Constructors
	// ===================================================================

	public Cone() {
		super(new GeometryCone());
	}

	public Cone(int segments) {
		super(new GeometryCone(segments));
	}

}
