package com.llama3d.object.mesh;

public class Triangle {

    // ===================================================================
    // Fields
    // ===================================================================

    public Vertex vertex[] = new Vertex[3];

    // ===================================================================
    // Constructors
    // ===================================================================

    public Triangle(Vertex vertex1, Vertex vertex2, Vertex vertex3) {
        // ======== Add Vertices ========
        this.vertex[0] = vertex1;
        this.vertex[1] = vertex2;
        this.vertex[2] = vertex3;
    }
}
