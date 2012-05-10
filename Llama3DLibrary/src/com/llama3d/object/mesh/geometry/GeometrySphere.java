package com.llama3d.object.mesh.geometry;

import android.util.Log;

import com.llama3d.math.vector.Vector2;
import com.llama3d.math.vector.Vector3;
import com.llama3d.object.mesh.Surface;
import com.llama3d.object.mesh.Vertex;

public class GeometrySphere extends Geometry {

    // ===================================================================
    // Constructors
    // ===================================================================

    public GeometrySphere() {
        this(16, 16);
    }

    public GeometrySphere(int rings, int segments) {
        // ======== Preparing Systemdata ========
        long milliseconds = System.currentTimeMillis();
        // ======== Preparing Calculationdata ========
        Vertex vertex[] = new Vertex[4];
        float temporaryPosition[] = new float[8];
        float step[] = { 360.0f / segments, 180.0f / rings };
        // ======== Preparing Geometrydata ========
        Surface surface = new Surface();
        this.surfaces.add(surface);
        Vector3[] position = new Vector3[4];
        Vector3[] normal = new Vector3[4];
        Vector2[] uvset = new Vector2[4];
        // ======== Calculating Geometrydata ========
        for (float ring = 0; ring <= 180.0f; ring += step[1]) {
            for (float segment = 0; segment < 360.0; segment += step[0]) {
                // ======== Calculating Radius1 & Radius2 ========
                temporaryPosition[0] = (float) Math.sin(Math.toRadians(ring));
                temporaryPosition[1] = (float) Math.sin(Math.toRadians(ring + step[1]));
                // ======== Calculating Width X1X2 ========
                temporaryPosition[2] = (float) Math.cos(Math.toRadians(segment));
                temporaryPosition[3] = (float) Math.cos(Math.toRadians(segment + step[0]));
                // ======== Calculating Depth Z1Z2 ========
                temporaryPosition[4] = (float) Math.sin(Math.toRadians(segment));
                temporaryPosition[5] = (float) Math.sin(Math.toRadians(segment + step[0]));
                // ======== Calculating Height Y1Y2 ========
                temporaryPosition[6] = (float) Math.cos(Math.toRadians(ring));
                temporaryPosition[7] = (float) Math.cos(Math.toRadians(ring + step[1]));
                // ======== Calculating Vertexposition ========
                position[0] = new Vector3(temporaryPosition[3] * temporaryPosition[0], temporaryPosition[6], temporaryPosition[5] * temporaryPosition[0]);
                position[1] = new Vector3(temporaryPosition[2] * temporaryPosition[1], temporaryPosition[7], temporaryPosition[4] * temporaryPosition[1]);
                position[2] = new Vector3(temporaryPosition[3] * temporaryPosition[1], temporaryPosition[7], temporaryPosition[5] * temporaryPosition[1]);
                position[3] = new Vector3(temporaryPosition[2] * temporaryPosition[0], temporaryPosition[6], temporaryPosition[4] * temporaryPosition[0]);
                // ======== Calculating Vertexnormal ========
                normal[0] = new Vector3(temporaryPosition[3] * temporaryPosition[0], temporaryPosition[6], temporaryPosition[5] * temporaryPosition[0]);
                normal[1] = new Vector3(temporaryPosition[2] * temporaryPosition[1], temporaryPosition[7], temporaryPosition[4] * temporaryPosition[1]);
                normal[2] = new Vector3(temporaryPosition[3] * temporaryPosition[1], temporaryPosition[7], temporaryPosition[5] * temporaryPosition[1]);
                normal[3] = new Vector3(temporaryPosition[2] * temporaryPosition[0], temporaryPosition[6], temporaryPosition[4] * temporaryPosition[0]);
                // ======== Calculating UVset ========
                uvset[0] = new Vector2(1.0f - (segment + step[0]) / 360.0f, 1.0f - (temporaryPosition[6] + 1.0f) / 2.0f);
                uvset[1] = new Vector2(1.0f - segment / 360.0f, 1.0f - (temporaryPosition[7] + 1.0f) / 2.0f);
                uvset[2] = new Vector2(1.0f - (segment + step[0]) / 360.0f, 1.0f - (temporaryPosition[7] + 1.0f) / 2.0f);
                uvset[3] = new Vector2(1.0f - segment / 360.0f, 1.0f - (temporaryPosition[6] + 1.0f) / 2.0f);
                // ======== Generating Vertices ========
                vertex[0] = new Vertex(position[0], normal[0], uvset[0]);
                vertex[1] = new Vertex(position[1], normal[1], uvset[1]);
                vertex[2] = new Vertex(position[2], normal[2], uvset[2]);
                vertex[3] = new Vertex(position[3], normal[3], uvset[3]);
                // ======== Adding Triangles To Surface ========
                surface.addTriangle(vertex[0], vertex[2], vertex[1]);
                surface.addTriangle(vertex[3], vertex[0], vertex[1]);
            }
        }
        // ======== Resulting Systemdata ========
        long tookMilliseconds = System.currentTimeMillis() - milliseconds;
        Log.d("Geometry", "Time for generating: " + tookMilliseconds + " milliseconds. [SphereGeometry]");
    }
}
