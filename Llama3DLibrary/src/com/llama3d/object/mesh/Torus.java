package com.llama3d.object.mesh;

import com.llama3d.object.mesh.geometry.GeometryTorus;

public class Torus extends Mesh {

    // ===================================================================
    // Constructors
    // ===================================================================

    public Torus() {
        super(new GeometryTorus());
    }

    public Torus(float innerRadius, float outerRadius) {
        super(new GeometryTorus(innerRadius, outerRadius));
    }

    public Torus(float innerRadius, float outerRadius, int rings, int segments) {
        super(new GeometryTorus(innerRadius, outerRadius, rings, segments));
    }
}
