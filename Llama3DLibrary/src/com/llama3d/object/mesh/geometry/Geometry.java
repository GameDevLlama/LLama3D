package com.llama3d.object.mesh.geometry;

import java.util.ArrayList;
import java.util.List;

import com.llama3d.object.mesh.Surface;

public class Geometry {

	// ===================================================================
	// Public Fields
	// ===================================================================

	public List<Surface> surfaces = new ArrayList<Surface>();
	public boolean buffered = false;

	// ===================================================================
	// Public Methods
	// ===================================================================

	public void bufferGeometry() {
		if (this.buffered == false) {
			for (Surface surface : this.surfaces) {
				surface.buffer();
				surface.setBuffer();
				this.buffered = true;
			}
		}
	}
}
