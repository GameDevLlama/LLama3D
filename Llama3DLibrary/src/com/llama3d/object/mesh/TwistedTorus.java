package com.llama3d.object.mesh;

import com.llama3d.object.mesh.geometry.GeometryTwistedTorus;

public class TwistedTorus extends Mesh {

	// ===================================================================
	// Constructors
	// ===================================================================

	public TwistedTorus() {
		super(new GeometryTwistedTorus());
	}

	public TwistedTorus(float innerRadius, float outerRadius) {
		super(new GeometryTwistedTorus(innerRadius, outerRadius));
	}	

	public TwistedTorus(float innerRadius, float outerRadius, int rings, int segments, int twists) {
		super(new GeometryTwistedTorus(innerRadius, outerRadius, rings, segments, twists));
	}
}
