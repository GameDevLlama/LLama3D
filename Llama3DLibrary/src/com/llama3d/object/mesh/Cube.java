package com.llama3d.object.mesh;

import com.llama3d.object.mesh.geometry.GeometryCube;

public class Cube extends Mesh {

    // ===================================================================
    // Constructors
    // ===================================================================

    public Cube() {
        super(new GeometryCube());
    }

}
