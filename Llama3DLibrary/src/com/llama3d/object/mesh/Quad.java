package com.llama3d.object.mesh;

import com.llama3d.object.mesh.geometry.GeometryQuad;

public class Quad extends Mesh {

    // ===================================================================
    // Constructors
    // ===================================================================

    public Quad() {
        super(new GeometryQuad());
    }

}
