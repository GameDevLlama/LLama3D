package com.llama3d.object.mesh.geometry;

import com.llama3d.math.vector.Vector2;
import com.llama3d.math.vector.Vector3;
import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.Vertex;

public class GeometryCube extends Geometry {

    public GeometryCube() {

        Surface surface = new Surface();
        this.surfaces.add(surface);

        Vertex vertex[] = new Vertex[4];
        Vector3 position[] = new Vector3[8];
        Vector3 normal[] = new Vector3[6];
        Vector2 uvset[] = new Vector2[4];
        // add vertices
        position[0] = new Vector3(-1, +1, -1);
        position[1] = new Vector3(+1, +1, -1);
        position[2] = new Vector3(-1, -1, -1);
        position[3] = new Vector3(+1, -1, -1);
        position[4] = new Vector3(-1, +1, +1);
        position[5] = new Vector3(+1, +1, +1);
        position[6] = new Vector3(-1, -1, +1);
        position[7] = new Vector3(+1, -1, +1);
        // add normals
        normal[0] = new Vector3(+0, +0, -1);
        normal[1] = new Vector3(+0, +0, +1);
        normal[2] = new Vector3(-1, +0, +0);
        normal[3] = new Vector3(+1, +0, +0);
        normal[4] = new Vector3(+0, +1, +0);
        normal[5] = new Vector3(+0, -1, +0);
        // add uv-sets
        uvset[0] = new Vector2(1, 0);
        uvset[1] = new Vector2(0, 0);
        uvset[2] = new Vector2(1, 1);
        uvset[3] = new Vector2(0, 1);
        // triangles
        // front
        vertex[0] = new Vertex(position[0], normal[0], uvset[0]);
        vertex[1] = new Vertex(position[1], normal[0], uvset[1]);
        vertex[2] = new Vertex(position[2], normal[0], uvset[2]);
        vertex[3] = new Vertex(position[3], normal[0], uvset[3]);
        surface.addTriangle(vertex[1], vertex[2], vertex[0]);
        surface.addTriangle(vertex[1], vertex[3], vertex[2]);
        // back
        vertex[0] = new Vertex(position[5], normal[1], uvset[0]);
        vertex[1] = new Vertex(position[4], normal[1], uvset[1]);
        vertex[2] = new Vertex(position[7], normal[1], uvset[2]);
        vertex[3] = new Vertex(position[6], normal[1], uvset[3]);
        surface.addTriangle(vertex[1], vertex[2], vertex[0]);
        surface.addTriangle(vertex[1], vertex[3], vertex[2]);
        // left
        vertex[0] = new Vertex(position[1], normal[3], uvset[0]);
        vertex[1] = new Vertex(position[5], normal[3], uvset[1]);
        vertex[2] = new Vertex(position[3], normal[3], uvset[2]);
        vertex[3] = new Vertex(position[7], normal[3], uvset[3]);
        surface.addTriangle(vertex[1], vertex[2], vertex[0]);
        surface.addTriangle(vertex[1], vertex[3], vertex[2]);
        // right
        vertex[0] = new Vertex(position[4], normal[2], uvset[0]);
        vertex[1] = new Vertex(position[0], normal[2], uvset[1]);
        vertex[2] = new Vertex(position[6], normal[2], uvset[2]);
        vertex[3] = new Vertex(position[2], normal[2], uvset[3]);
        surface.addTriangle(vertex[1], vertex[2], vertex[0]);
        surface.addTriangle(vertex[1], vertex[3], vertex[2]);
        // up
        vertex[0] = new Vertex(position[4], normal[4], uvset[0]);
        vertex[1] = new Vertex(position[5], normal[4], uvset[1]);
        vertex[2] = new Vertex(position[0], normal[4], uvset[2]);
        vertex[3] = new Vertex(position[1], normal[4], uvset[3]);
        surface.addTriangle(vertex[1], vertex[2], vertex[0]);
        surface.addTriangle(vertex[1], vertex[3], vertex[2]);
        // down
        vertex[0] = new Vertex(position[6], normal[5], uvset[0]);
        vertex[1] = new Vertex(position[7], normal[5], uvset[1]);
        vertex[2] = new Vertex(position[2], normal[5], uvset[2]);
        vertex[3] = new Vertex(position[3], normal[5], uvset[3]);
        surface.addTriangle(vertex[1], vertex[0], vertex[2]);
        surface.addTriangle(vertex[1], vertex[2], vertex[3]);
    }
}
