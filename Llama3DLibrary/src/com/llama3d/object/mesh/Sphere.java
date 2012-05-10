package com.llama3d.object.mesh;

import com.llama3d.object.mesh.geometry.GeometrySphere;

public class Sphere extends Mesh {

    // ===================================================================
    // Constructors
    // ===================================================================

    public Sphere() {
        super(new GeometrySphere());
    }

    public Sphere(int rings, int segments) {
        super(new GeometrySphere(rings, segments));
    }

}
